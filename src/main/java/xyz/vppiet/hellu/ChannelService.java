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
import java.util.stream.Collectors;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
class ChannelService implements Service<ChannelMessageEvent> {
	@ToString.Include
	private static final String PREFIX = ".";

	protected static final String PROP_IN_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	protected static final String PROP_OUT_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	protected static final String PROP_OUT_SERVICE_INFO = "serviceInfo";

	@Getter(AccessLevel.PUBLIC)
	@ToString.Include
	private final String name;

	@Getter(AccessLevel.PUBLIC)
	private final String description;

	private ChannelMessageEvent channelMessageEvent;
	private ServiceInfo serviceInfo;

	private final Pattern filter;
	private final Pattern exactFilter;

	protected final PropertyChangeSupport pcs;

	ChannelService(String name, String description) {
		this.name = name;
		this.description = description;
		this.filter = Pattern.compile("^\\" + PREFIX + name + "\\b.*?", Pattern.CASE_INSENSITIVE);
		this.exactFilter = Pattern.compile("^\\" + PREFIX + name + "$", Pattern.CASE_INSENSITIVE);
		this.pcs = new PropertyChangeSupport(this);

		this.channelMessageEvent = null;
		this.serviceInfo = new ServiceInfo(name, description);
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (this.hasPropertyChangeListener(property, listener)) {
			log.error("Listener is already subscribed to the property");

			return;
		}

		this.pcs.addPropertyChangeListener(property, listener);
		log.info("New subscription from {} to property {} on {}", listener, property, this);

		if (listener instanceof ChannelCommand) {
			Command<ChannelMessageEvent> command = (ChannelCommand) listener;
			this.handleCommandRegistration(command);
		}
	}

	private void handleCommandRegistration(Command<ChannelMessageEvent> command) {
		String name = command.getName();
		String description = command.getDescription();
		CommandInfo commandInfo = new CommandInfo(name, description);

		ServiceInfo newServiceInfo = this.serviceInfo.clone();
		newServiceInfo.update(commandInfo);
		this.setServiceInfo(newServiceInfo);
	}

	private void setServiceInfo(ServiceInfo serviceInfo) {
		this.pcs.firePropertyChange(PROP_OUT_SERVICE_INFO, this.serviceInfo, serviceInfo);
		this.serviceInfo = serviceInfo;
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		switch (propertyName) {
			case PROP_IN_CHANNEL_MESSAGE_EVENT:
				this.handleIncomingChannelMessageEvent(pce);
				break;
			default:
				log.warn("Property {} dismissed", propertyName);
		}
	}

	protected void handleIncomingChannelMessageEvent(PropertyChangeEvent pce) {
		Object newValue = pce.getNewValue();

		if (!(newValue instanceof ChannelMessageEvent)) return;

		ChannelMessageEvent event = (ChannelMessageEvent) newValue;

		String msg = event.getMessage().strip();

		if (this.matchesExactFilter(msg)) {
			event.sendReply(this.getHelp());

			return;
		}

		if (this.matchesFilter(msg)) {
			this.setChannelMessageEvent(event);
		}
	}

	protected void setChannelMessageEvent(ChannelMessageEvent event) {
			this.pcs.firePropertyChange(PROP_OUT_CHANNEL_MESSAGE_EVENT, this.channelMessageEvent, event);
			this.channelMessageEvent = event;
	}

	protected boolean matchesExactFilter(String msg) {
		return this.exactFilter.matcher(msg).matches();
	}

	protected boolean matchesFilter(String msg) {
		return this.filter.matcher(msg).matches();
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

	@Override
	public String getHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Description: ");
		sb.append(this.getDescription());
		sb.append(" ");
		sb.append("Commands: ");

		String commands;

		synchronized (this.serviceInfo) {
			commands = this.serviceInfo.getCommandInfos().stream()
					.map(CommandInfo::getName)
					.collect(Collectors.joining(" ,"));
		}

		sb.append(commands);

		return sb.toString();
	}
}
