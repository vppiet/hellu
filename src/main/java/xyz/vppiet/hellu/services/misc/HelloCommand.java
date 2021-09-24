package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

@Log4j2
@ToString(callSuper = true)
public final class HelloCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "hello";
	private static final String DESCRIPTION = "Says hello to a user.";
	private static final ParameterManager PARAMS = new ParameterManager();

	public HelloCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		final ReplyableEvent event = sm.getReplyableEvent();
		final User user = sm.getUser();

		this.replyWithHello(event, user);
	}

	private void replyWithHello(ReplyableEvent event, User user) {
		String nick = user.getNick();

		event.sendReply("Hello " + nick + "!");
	}
}
