package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;

import java.util.ArrayList;

@Log4j2
@ToString(callSuper = true)
public final class HelloCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "hello";
	private static final String DESCRIPTION = "Says hello to a user.";

	public HelloCommand() {
		super(SERVICE, NAME, DESCRIPTION, new ArrayList<>());
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		final ChannelMessageEvent event = scm.getEvent();
		final User user = event.getActor();

		this.replyWithHello(event, user);
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {
		final PrivateMessageEvent event = spm.getEvent();
		final User user = event.getActor();

		this.replyWithHello(event, user);
	}

	private void replyWithHello(ReplyableEvent event, User user) {
		String nick = user.getNick();

		event.sendReply("Hello " + nick + "!");
	}
}
