package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

import lombok.ToString;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.ListenedChannelMessage;

@Getter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class ServiceManagedChannelMessage extends ListenedChannelMessage {

	private final ServiceManager sourceServiceManager;

	public ServiceManagedChannelMessage(ChannelMessageEvent event,
										EventListener<ChannelMessageEvent> sourceListener,
										ServiceManager sourceServiceManager) {
		super(event, sourceListener);
		this.sourceServiceManager = sourceServiceManager;
	}

	public ServiceManagedChannelMessage(ListenedChannelMessage listenedChannelMessage, ServiceManager serviceManager) {
		this(listenedChannelMessage.getEvent(), listenedChannelMessage.getSourceListener(), serviceManager);
	}
}
