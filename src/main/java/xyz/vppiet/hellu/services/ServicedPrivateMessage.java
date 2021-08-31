package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;

import lombok.ToString;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.ServiceManagedPrivateMessage;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.eventlisteners.EventListener;

@Getter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class ServicedPrivateMessage extends ServiceManagedPrivateMessage {

	private final Service sourceService;

	public ServicedPrivateMessage(PrivateMessageEvent event,
								  EventListener<PrivateMessageEvent> sourceListener,
								  ServiceManager sourceServiceManager,
								  Service sourceService) {
		super(event, sourceListener, sourceServiceManager);
		this.sourceService = sourceService;
	}

	public ServicedPrivateMessage(ServiceManagedPrivateMessage serviceManagedPrivateMessage, Service sourceService) {
		this(serviceManagedPrivateMessage.getEvent(),
				serviceManagedPrivateMessage.getSourceListener(),
				serviceManagedPrivateMessage.getSourceServiceManager(),
				sourceService);
	}
}
