package xyz.vppiet.hellu.services.help;

import lombok.ToString;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

@ToString(callSuper = true)
public class ServicesCommand extends CommandBase {

	private static final String SERVICE = "help";
	private static final String NAME = "services";
	private static final String DESCRIPTION = "Lists all services.";
	private static final ParameterManager PARAMS = new ParameterManager();

	public ServicesCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		String services = String.join(", ", sm.getSourceServiceManager().getServiceNames());
		this.replyWithServices(sm.getReplyableEvent(), services);
	}

	private void replyWithServices(ReplyableEvent event, String services) {
		event.sendReply("Services: " + services);
	}
}
