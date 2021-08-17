package xyz.vppiet.hellu;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import java.util.Set;

@Log4j2
@ToString(callSuper = true)
final class HelpService extends ChannelService {
	private static final String PROP_IN_SERVICE_REGISTER = "serviceRegister";
	private static final String PROP_OUT_SERVICE_REGISTER = "serviceRegister";

	private ServiceRegister serviceRegister;

	HelpService(String name, String description) {
		super(name, description);
		this.serviceRegister = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		switch (propertyName) {
			case PROP_IN_CHANNEL_MESSAGE_EVENT:
				this.handleIncomingChannelMessageEvent(pce);
				break;
			case PROP_IN_SERVICE_REGISTER:
				this.handleIncomingServiceRegister(pce);
				break;
			default:
				log.warn("Property {} dismissed", propertyName);
		}
	}

	@Override
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
			String[] parts = msg.split(" ", 3);

			if (parts.length == 2) {
				String service = parts[1];

				if (this.serviceRegister.hasService(service)) {
					event.sendReply(getServiceDescription(service));

					return;
				}
			}

			if (parts.length == 3) {
				String service = parts[1];
				String command = parts[2];

				if (this.serviceRegister.hasCommand(service, command)) {
					event.sendReply(getCommandDescription(service, command));

					return;
				}
			}

			this.setChannelMessageEvent(event);
		}
	}

	private void handleIncomingServiceRegister(PropertyChangeEvent pce) {
		Object newValue = pce.getNewValue();

		if (!(newValue instanceof ServiceRegister)) return;

		ServiceRegister newServiceRegister = (ServiceRegister) newValue;
		this.setServiceRegister(newServiceRegister);
	}

	private String getServiceDescription(String service) {
		return this.serviceRegister.getServiceInfo(service).getDescription();
	}

	private String getCommandDescription(String service, String command) {
		return this.serviceRegister.getCommand(service, command).getDescription();
	}

	private void setServiceRegister(ServiceRegister newServiceRegister) {
		this.pcs.firePropertyChange(PROP_OUT_SERVICE_REGISTER, this.serviceRegister, newServiceRegister);
		this.serviceRegister = newServiceRegister;
	}
}
