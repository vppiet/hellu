package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.Subject;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandInvoke;

// TODO: requires privilege check
@Log4j2
@ToString(callSuper = true)
public class QuitCommand extends CommandBase {
	private static final String NAME = "quit";
	private static final String DESCRIPTION = "Shuts down the irc bot.";

	public QuitCommand() {
		super(NAME, DESCRIPTION);
	}

	@Override
	public void handleCommandInvoke(Subject sub, CommandInvoke ci) {
		String command = ci.getCommand();
		if (!command.equals(this.name)) return;

		log.info("Call to: {}", this);		// DEBUG

		if (sub instanceof ServiceManager) {
			ServiceManager sm = (ServiceManager) sub;
			sm.getHellu().disconnect();
		}
	}

	@Override
	public void onNext(Subject sub, Object obj) {
		if (obj instanceof CommandInvoke) {
			CommandInvoke ci = (CommandInvoke) obj;
			this.handleCommandInvoke(sub, ci);
		}
	}
}
