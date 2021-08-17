package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class HelpServicesCommand extends ChannelCommand {
	private static final String PROP_IN_SERVICE_REGISTER = "serviceRegister";

	private ServiceRegister serviceRegister;

	public HelpServicesCommand(String service, String name, String description) {
		super(service, name, description);
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

	private void handleIncomingServiceRegister(PropertyChangeEvent pce) {
		Object newValue = pce.getNewValue();

		if (!(newValue instanceof ServiceRegister)) return;

		this.serviceRegister = (ServiceRegister) newValue;
	}

	@Override
	String getReply(ChannelMessageEvent event) {
		StringBuilder sb = new StringBuilder();
		sb.append("Services: ");

		String services;

		synchronized (this.serviceRegister) {
			services = this.serviceRegister.getServiceInfos().stream()
					.map(ServiceInfo::getName)
					.collect(Collectors.joining(", "));
		}

		sb.append(services);

		return sb.toString();
	}
}
