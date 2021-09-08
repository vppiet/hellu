package xyz.vppiet.hellu.services.misc;

import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;
import org.kitteh.irc.client.library.util.CtcpUtil;

import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.StringCommandParameter;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;

import java.util.Optional;

@Log4j2
public class SlapCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "slap";
	private static final String DESCRIPTION = "Slaps a user.";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("user", "")
	);

	public SlapCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		ChannelMessageEvent event = scm.getEvent();
		Channel channel = event.getChannel();

		CommandInvocation ci = scm.getCommandInvocation();
		String paramUser = ci.getParams().get(0);

		Optional<User> optUser = channel.getUser(paramUser);

		if (optUser.isEmpty()) return;

		User user = optUser.get();
		String currentNick = scm.getSourceServiceManager().getHellu().getIrcClient().getNick();

		if (user.getNick().equals(currentNick)) {
			this.slapMyself(event);
		} else {
			this.slapUser(event, user);
		}
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {
		PrivateMessageEvent event = spm.getEvent();

		CommandInvocation ci = spm.getCommandInvocation();
		String paramUser = ci.getParams().get(0);
		String helluNick = spm.getSourceServiceManager().getHellu().getIrcClient().getNick();

		if (!paramUser.equals(helluNick)) return;

		this.slapMyself(event);
	}

	private void slapUser(ReplyableEvent event, User user) {
		String nick = user.getNick();
		String reply = "ACTION slaps " + nick + " around a bit with a large trout";
		String ctcpReply = CtcpUtil.toCtcp(reply);

		event.sendReply(ctcpReply);
	}

	private void slapMyself(ReplyableEvent event) {
		String reply = "ACTION slaps herself around a bit with a large trout";
		String ctcpReply = CtcpUtil.toCtcp(reply);

		event.sendReply(ctcpReply);
	}
}
