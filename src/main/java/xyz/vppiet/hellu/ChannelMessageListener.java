package xyz.vppiet.hellu;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.subscription.SubscriptionContext;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.regex.Pattern;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
class ChannelMessageListener implements EventListener<ChannelMessageEvent> {

	@ToString.Include
	public static final String NAME = "channel";

	@ToString.Include
	public static final String PREFIX = ".";

	private static final Pattern FILTER = Pattern.compile("^\\" + PREFIX + "\\b\\w{2,}\\b.*?");

	private static final String PROP_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	private static final String PROP_COMMAND_REGISTRATION_EVENT = "commandRegistrationEvent";
	private static final String PROP_SERVICE_REGISTRATION_EVENT = "serviceRegistrationEvent";

	private ChannelMessageEvent channelMessageEvent;
	private CommandRegistrationEvent commandRegistrationEvent;
	private ServiceRegistrationEvent serviceRegistrationEvent;

	private final PropertyChangeSupport pcs;
	private final ServiceRegistry serviceRegistry;

	ChannelMessageListener() {
		this.channelMessageEvent = null;
		this.commandRegistrationEvent = null;
		this.serviceRegistrationEvent = null;
		this.pcs = new PropertyChangeSupport(this);
		this.serviceRegistry = new ServiceRegistry();
	}

	public static class ChannelMessageFilter implements IMessageFilter<ChannelMessageEvent> {
		@Override
		public boolean accepts(ChannelMessageEvent event, SubscriptionContext context) {
			String msg = event.getMessage().strip();
			return FILTER.matcher(msg).matches();
		}
	}

	@Handler(delivery = Invoke.Asynchronously, filters = {@Filter(ChannelMessageFilter.class)}, rejectSubtypes = true)
	public void setMessageEvent(ChannelMessageEvent channelMessageEvent) {
		this.pcs.firePropertyChange(PROP_CHANNEL_MESSAGE_EVENT, this.channelMessageEvent, channelMessageEvent);
		this.channelMessageEvent = channelMessageEvent;
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (this.hasPropertyChangeListener(property, listener)) {
			log.error("Listener is already subscribed to the property");

			return;
		}

		this.pcs.addPropertyChangeListener(property, listener);
		log.info("New subscription from {} to property {} on {}", listener, property, this);

		if (listener instanceof ChannelService) {
			ChannelService service = (ChannelService) listener;

			String name = service.getName();
			String description = service.getDescription();
			ServiceRegistrationEvent event = new ServiceRegistrationEvent(name, description);

			this.handleServiceRegistrationEvent(event);
		}
	}

	private boolean hasPropertyChangeListener(String property, PropertyChangeListener pcl) {
		PropertyChangeListener[] listeners = this.pcs.getPropertyChangeListeners(property);

		for (PropertyChangeListener listener : listeners) {
			if (listener.equals(pcl)) {
				return true;
			}
		}

		return false;
	}

	private void handleServiceRegistrationEvent(ServiceRegistrationEvent event) {
		String name = event.getName();
		String description = event.getDescription();

		this.serviceRegistry.addService(name, description);
		this.setServiceRegistrationEvent(event);
	}

	private void setServiceRegistrationEvent(ServiceRegistrationEvent event) {
		this.pcs.firePropertyChange(PROP_SERVICE_REGISTRATION_EVENT, this.serviceRegistrationEvent, event);
		this.serviceRegistrationEvent = event;
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		if (propertyName.equals(PROP_COMMAND_REGISTRATION_EVENT)) {
			Object newValue = pce.getNewValue();
			if (!(newValue instanceof CommandRegistrationEvent)) return;

			CommandRegistrationEvent event = (CommandRegistrationEvent) newValue;
			this.handleCommandRegistrationEvent(event);
		}
	}

	private void handleCommandRegistrationEvent(CommandRegistrationEvent event) {
		String service = event.getService();
		String name = event.getName();
		String description = event.getDescription();

		this.serviceRegistry.addCommand(service, name, description);
		this.setCommandRegistrationEvent(event);
	}

	private void setCommandRegistrationEvent(CommandRegistrationEvent commandRegistrationEvent) {
		this.pcs.firePropertyChange(PROP_COMMAND_REGISTRATION_EVENT, this.commandRegistrationEvent, commandRegistrationEvent);
		this.commandRegistrationEvent = commandRegistrationEvent;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
