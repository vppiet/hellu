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

	private static final String PROP_IN_SERVICE_INFO = "serviceInfo";
	private static final String PROP_OUT_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	private static final String PROP_OUT_SERVICE_REGISTER = "serviceRegister";

	private ChannelMessageEvent channelMessageEvent;
	private ServiceRegister serviceRegister;

	private final PropertyChangeSupport pcs;

	ChannelMessageListener() {
		this.channelMessageEvent = null;
		this.pcs = new PropertyChangeSupport(this);
		this.serviceRegister = new ServiceRegister();
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
		this.pcs.firePropertyChange(PROP_OUT_CHANNEL_MESSAGE_EVENT, this.channelMessageEvent, channelMessageEvent);
		this.channelMessageEvent = channelMessageEvent;
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (this.hasPropertyChangeListener(property, listener)) {
			log.error("Listener is already subscribed to the property. Duplicate listening is prohibited.");

			return;
		}

		this.pcs.addPropertyChangeListener(property, listener);
		log.info("New subscription from {} to property {} on {}", listener, property, this);

		if (listener instanceof ChannelService) {
			ChannelService service = (ChannelService) listener;
			handleServiceRegistration(service);
		}
	}

	private void handleServiceRegistration(ChannelService service) {
		String name = service.getName();
		String description = service.getDescription();

		ServiceInfo serviceInfo = new ServiceInfo(name, description);

		ServiceRegister newServiceRegister = this.serviceRegister.clone();
		newServiceRegister.update(serviceInfo);
		this.setServiceRegister(newServiceRegister);
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		switch (propertyName) {
			case PROP_IN_SERVICE_INFO:
				this.handleIncomingServiceInfo(pce);
				break;
			default:
				log.warn("Property {} dismissed", propertyName);
		}
	}

	private void handleIncomingServiceInfo(PropertyChangeEvent pce) {
		Object newValue = pce.getNewValue();

		if (!(newValue instanceof ServiceInfo)) return;

		ServiceInfo serviceInfo = (ServiceInfo) newValue;
		ServiceRegister newServiceRegister = this.serviceRegister.clone();

		newServiceRegister.update(serviceInfo);
		this.setServiceRegister(newServiceRegister);
	}

	private void setServiceRegister(ServiceRegister newServiceRegister) {
		this.pcs.firePropertyChange(PROP_OUT_SERVICE_REGISTER, this.serviceRegister, newServiceRegister);
		this.serviceRegister = newServiceRegister;
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

	@Override
	public String getName() {
		return NAME;
	}
}
