package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.util.Objects;

@Log4j2
class HelpService extends ChannelService {
	private static final String PROP_SERVICE_REGISTRATION_EVENT = "serviceRegistrationEvent";

	private ServiceRegistrationEvent serviceRegistrationEvent;

	HelpService(String name, String description) {
		super(name, description);
		this.serviceRegistrationEvent = null;
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

		if (propertyName.equals(PROP_SERVICE_REGISTRATION_EVENT)) {
			Object newValue = pce.getNewValue();
			if (!(newValue instanceof ServiceRegistrationEvent)) return;

			ServiceRegistrationEvent serviceRegistrationEvent = (ServiceRegistrationEvent) newValue;
			this.setServiceRegistrationEvent(serviceRegistrationEvent);
		}
	}

	private void setServiceRegistrationEvent(ServiceRegistrationEvent event) {
		this.pcs.firePropertyChange(PROP_SERVICE_REGISTRATION_EVENT, this.serviceRegistrationEvent, event);
		this.serviceRegistrationEvent = event;
	}
}
