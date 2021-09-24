package xyz.vppiet.hellu.eventlisteners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.helper.ActorMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.MessageType;

@Getter(AccessLevel.PUBLIC)
@ToString(onlyExplicitlyIncluded = true)
public class ListenedMessage {

	private final ActorMessageEvent<User> sourceEvent;
	private final EventListener<?> sourceListener;
	private final ReplyableEvent replyableEvent;
	@ToString.Include private final String message;
	@ToString.Include private final MessageType type;
	@ToString.Include private final User user;
	private final CommandInvocation commandInvocation;

	public ListenedMessage(ActorMessageEvent<User> sourceEvent, EventListener<?> sourceListener, ReplyableEvent replyableEvent, String message, MessageType type, User user, CommandInvocation commandInvocation) {
		this.sourceEvent = sourceEvent;
		this.sourceListener = sourceListener;
		this.replyableEvent = replyableEvent;
		this.message = message;
		this.type = type;
		this.user = user;
		this.commandInvocation = commandInvocation;
	}
}
