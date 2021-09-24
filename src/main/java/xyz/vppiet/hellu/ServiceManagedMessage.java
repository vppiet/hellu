package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.helper.ActorMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.ListenedMessage;

@Getter(AccessLevel.PUBLIC)
public class ServiceManagedMessage extends ListenedMessage {

	private final ServiceManager sourceServiceManager;

	public ServiceManagedMessage(ActorMessageEvent<User> sourceEvent, EventListener<?> sourceListener, ReplyableEvent replyableEvent, String message, MessageType type, User user, CommandInvocation commandInvocation, ServiceManager sourceServiceManager) {
		super(sourceEvent, sourceListener, replyableEvent, message, type, user, commandInvocation);
		this.sourceServiceManager = sourceServiceManager;
	}

	public ServiceManagedMessage(ListenedMessage lm, ServiceManager sourceServiceManager) {
		this(lm.getSourceEvent(), lm.getSourceListener(), lm.getReplyableEvent(), lm.getMessage(), lm.getType(), lm.getUser(), lm.getCommandInvocation(), sourceServiceManager);
	}
}
