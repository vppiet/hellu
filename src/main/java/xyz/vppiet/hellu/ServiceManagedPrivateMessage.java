package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

import lombok.ToString;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.ListenedPrivateMessage;

@Getter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class ServiceManagedPrivateMessage extends ListenedPrivateMessage {

	private final ServiceManager sourceServiceManager;

	public ServiceManagedPrivateMessage(PrivateMessageEvent event,
										EventListener<PrivateMessageEvent> sourceListener,
										ServiceManager sourceServiceManager) {
		super(event, sourceListener);
		this.sourceServiceManager = sourceServiceManager;
	}

	public ServiceManagedPrivateMessage(ListenedPrivateMessage listenedPrivateMessage, ServiceManager serviceManager) {
		this(listenedPrivateMessage.getEvent(), listenedPrivateMessage.getSourceListener(), serviceManager);
	}
}
