package xyz.vppiet.hellu.services.football;

import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;
import xyz.vppiet.hellu.services.StringCommandParameter;

public final class LiveCommand extends CommandBase {

	private static final String SERVICE = "football";
	private static final String NAME = "live";
	private static final String DESCRIPTION = "Displays live fixtures for maximum of three leagues.";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("league01", ""),
			new StringCommandParameter("league02", ""),
			new StringCommandParameter("league03", "")
	);

	public LiveCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {

	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {

	}
}
