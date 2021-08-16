package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class HelpServicesCommand extends ChannelCommand {
	private static final String PROP_SERVICE_REGISTRATION_EVENT = "serviceRegistrationEvent";

	private final Set<String> services;

	public HelpServicesCommand(String service, String name, String description) {
		super(service, name, description);
		this.services = Collections.synchronizedSortedSet(new TreeSet<>());
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		if (propertyName.equals(PROP_CHANNEL_MESSAGE_EVENT)) {
			Object newValue = pce.getNewValue();
			if (!(newValue instanceof ChannelMessageEvent)) return;

			ChannelMessageEvent messageEvent = (ChannelMessageEvent) newValue;
			this.handleChannelMessageEvent(messageEvent);
		}

		if (propertyName.equals(PROP_SERVICE_REGISTRATION_EVENT)) {
			Object newValue = pce.getNewValue();
			if (!(newValue instanceof ServiceRegistrationEvent)) return;

			ServiceRegistrationEvent event = (ServiceRegistrationEvent) newValue;
			this.handleServiceRegistrationEvent(event);
		}
	}

	@Override
	public void handleChannelMessageEvent(ChannelMessageEvent event) {
		String msg = event.getMessage().strip();
		if (!this.matchesFilter(msg)) return;

		event.sendReply("Services: " + String.join(", ", this.services));
	}

	private void handleServiceRegistrationEvent(ServiceRegistrationEvent event) {
		String name = event.getName();
		this.services.add(name);
	}
}
