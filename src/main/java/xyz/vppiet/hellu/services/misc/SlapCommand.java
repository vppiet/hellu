package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;
import org.kitteh.irc.client.library.util.CtcpUtil;

import xyz.vppiet.hellu.MessageType;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ServicedMessage;

import java.util.Optional;

@Log4j2
@ToString(callSuper = true)
public class SlapCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "slap";
	private static final String DESCRIPTION = "Slaps a user.";
	private static final ParameterManager PARAMS = new ParameterManager().addRequired("user");

	public SlapCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		MessageType type = sm.getType();
		String targetUser = sm.getCommandInvocation().getParams().get(0);
		String currentNick = sm.getSourceServiceManager().getHellu().getIrcClient().getNick();

		if (type.equals(MessageType.CHANNEL)) {
			this.handleChannelMessage((ChannelMessageEvent) sm.getSourceEvent(), targetUser, currentNick);
		} else if (type.equals(MessageType.PRIVATE)) {
			this.handlePrivateMessage((PrivateMessageEvent) sm.getSourceService(), targetUser, currentNick);
		}
	}

	private void handleChannelMessage(ChannelMessageEvent event, String targetUser, String currentNick) {
		Optional<User> optUser = event.getChannel().getUser(targetUser);
		if (optUser.isEmpty()) return;

		User user = optUser.get();
		if (user.getNick().equals(currentNick)) {
			this.slapMyself(event);
		} else {
			this.slapUser(event, user);
		}
	}

	private void handlePrivateMessage(PrivateMessageEvent event, String targetUser, String currentNick) {
		if (!targetUser.equals(currentNick)) return;

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
