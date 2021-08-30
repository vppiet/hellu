package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.element.Actor;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.helper.ActorMessageEvent;
import org.kitteh.irc.client.library.event.helper.MessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.Subject;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandInvoke;

@Log4j2
@ToString(callSuper = true)
public class HelloCommand extends CommandBase {

	private static final String NAME = "hello";
	private static final String DESCRIPTION = "Says hello to a user.";

	public HelloCommand() {
		super(NAME, DESCRIPTION);
	}

	@Override
	public void handleCommandInvoke(Subject sub, CommandInvoke ci) {
		String command = ci.getCommand();
		if (!command.equals(this.name)) return;

		log.info("Call to: {}", this);		// DEBUG

		MessageEvent msgEvent = ci.getSourceEvent();
		if (msgEvent instanceof ReplyableEvent) {
			ReplyableEvent replEvent = (ReplyableEvent) msgEvent;
			String reply = "Hello!";

			if (msgEvent instanceof ActorMessageEvent) {
				ActorMessageEvent<?> actEvent = (ActorMessageEvent<?>) msgEvent;
				Actor actor = actEvent.getActor();

				if (actor instanceof User) {
					User user = (User) actEvent.getActor();
					String nick = user.getNick();

					reply = "Hello, " + nick + "!";
				}
			}

			replEvent.sendReply(reply);
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
