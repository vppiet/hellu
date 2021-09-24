package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.helper.ActorMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.MessageType;
import xyz.vppiet.hellu.ServiceManagedMessage;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.eventlisteners.EventListener;

@Getter(AccessLevel.PUBLIC)
public class ServicedMessage extends ServiceManagedMessage {

	private final Service sourceService;

	public ServicedMessage(ActorMessageEvent<User> sourceEvent, EventListener<?> sourceListener, ReplyableEvent replyableEvent, String message, MessageType type, User user, CommandInvocation commandInvocation, ServiceManager sourceServiceManager, Service sourceService) {
		super(sourceEvent, sourceListener, replyableEvent, message, type, user, commandInvocation, sourceServiceManager);
		this.sourceService = sourceService;
	}

	public ServicedMessage(ServiceManagedMessage smm, Service sourceService) {
		this(smm.getSourceEvent(), smm.getSourceListener(), smm.getReplyableEvent(), smm.getMessage(), smm.getType(), smm.getUser(), smm.getCommandInvocation(), smm.getSourceServiceManager(), sourceService);
	}
}
