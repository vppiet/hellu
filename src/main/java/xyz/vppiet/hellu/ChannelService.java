package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.regex.Pattern;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
class ChannelService implements Service<ChannelMessageEvent> {
	@ToString.Include
	private static final String PREFIX = ".";

	protected static final String PROP_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	protected static final String PROP_COMMAND_REGISTRATION_EVENT = "commandRegistrationEvent";

	@Getter(AccessLevel.PUBLIC)
	@ToString.Include
	private final String name;

	@ToString.Include
	private final String description;

	private ChannelMessageEvent channelMessageEvent;
	private CommandRegistrationEvent commandRegistrationEvent;

	private final Pattern filter;
	private final Pattern exactFilter;
	private final CommandRegistry commandRegistry;
	protected final PropertyChangeSupport pcs;

	ChannelService(String name, String description) {
		this.name = name;
		this.description = description;
		this.filter = Pattern.compile("^\\" + PREFIX + name + "\\b.*?", Pattern.CASE_INSENSITIVE);
		this.exactFilter = Pattern.compile("^\\" + PREFIX + name + "$", Pattern.CASE_INSENSITIVE);
		this.commandRegistry = new CommandRegistry(this.name);
		this.pcs = new PropertyChangeSupport(this);
		this.channelMessageEvent = null;
		this.commandRegistrationEvent = null;
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (this.hasPropertyChangeListener(property, listener)) {
			log.error("Listener is already subscribed to the property");

			return;
		}

		this.pcs.addPropertyChangeListener(property, listener);
		log.info("New subscription from {} to property {} on {}", listener, property, this);

		if (listener instanceof ChannelCommand) {
			ChannelCommand command = (ChannelCommand) listener;

			String name = command.getName();
			String description = command.getDescription();
			String service = this.name;
			CommandRegistrationEvent event = new CommandRegistrationEvent(name, description, service);
			this.handleCommandRegistrationEvent(event);
		}
	}

	public boolean hasPropertyChangeListener(String property, PropertyChangeListener pcl) {
		PropertyChangeListener[] listeners = this.pcs.getPropertyChangeListeners(property);

		for (PropertyChangeListener listener : listeners) {
			if (listener.equals(pcl)) {
				return true;
			}
		}

		return false;
	}

	private void handleCommandRegistrationEvent(CommandRegistrationEvent event) {
		String name = event.getName();
		String description = event.getDescription();

		this.commandRegistry.addCommand(name, description);
		this.setCommandRegistrationEvent(event);
	}

	private void setCommandRegistrationEvent(CommandRegistrationEvent event) {
		this.pcs.firePropertyChange(PROP_COMMAND_REGISTRATION_EVENT, this.commandRegistrationEvent, event);
		this.commandRegistrationEvent = event;
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		if (propertyName.equals(PROP_CHANNEL_MESSAGE_EVENT)) {
			Object newValue = pce.getNewValue();

			if (!(newValue instanceof ChannelMessageEvent)) return;

			ChannelMessageEvent messageEvent = (ChannelMessageEvent) newValue;
			this.setChannelMessageEvent(messageEvent);
		}
	}

	protected void setChannelMessageEvent(ChannelMessageEvent event) {
		String msg = event.getMessage().strip();

		if (this.matchesExactFilter(msg)) {
			event.sendReply(this.getHelp());
			this.channelMessageEvent = event;

			return;
		}

		if (this.matchesFilter(msg)) {
			this.pcs.firePropertyChange(PROP_CHANNEL_MESSAGE_EVENT, this.channelMessageEvent, event);
			this.channelMessageEvent = event;
		}
	}

	private boolean matchesExactFilter(String msg) {
		return this.exactFilter.matcher(msg).matches();
	}

	private boolean matchesFilter(String msg) {
		return this.filter.matcher(msg).matches();
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Description: ");
		sb.append(this.getDescription());
		sb.append(" ");
		sb.append("Commands: ");
		sb.append(String.join(", ", this.commandRegistry.getCommands()));

		return sb.toString();
	}
}
