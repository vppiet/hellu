package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.CommandProperties;
import xyz.vppiet.hellu.Hellu;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;

// TODO: requires privilege check
@Log4j2
@ToString(callSuper = true)
public final class QuitCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "quit";
	private static final String DESCRIPTION = "Shuts down the irc bot.";
	private static final CommandParameterManager PARAMS = new CommandParameterManager();

	public QuitCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		final Hellu hellu = scm.getSourceServiceManager().getHellu();
		this.disconnectServer(hellu);
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {
		final Hellu hellu = spm.getSourceServiceManager().getHellu();
		this.disconnectServer(hellu);
	}

	private void disconnectServer(Hellu hellu) {
		hellu.disconnect();
	}
}
