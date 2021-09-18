package xyz.vppiet.hellu.services.football;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;
import xyz.vppiet.hellu.services.StringCommandParameter;

public class LeaguesCommand extends CommandBase {

	private static final String SERVICE = "football";
	private static final String NAME = "leagues";
	private static final String DESCRIPTION = "Displays leagues available for a given country code (ISO 3166-1 alpha-2).";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("country code", "")
	);

	public LeaguesCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {

	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {

	}
}
