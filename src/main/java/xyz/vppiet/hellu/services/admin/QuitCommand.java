package xyz.vppiet.hellu.services.admin;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.Hellu;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

@Log4j2
@ToString(callSuper = true)
public final class QuitCommand extends CommandBase {

	private static final String SERVICE = "admin";
	private static final String NAME = "quit";
	private static final String DESCRIPTION = "Shuts down the irc bot.";
	private static final ParameterManager PARAMS = new ParameterManager();

	public QuitCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		String userHost = sm.getUser().getHost();
		if (userHost.equals(System.getenv(AdminService.ENV_VAR))) {
			Hellu hellu = sm.getSourceServiceManager().getHellu();
			hellu.disconnect();
		}
	}
}
