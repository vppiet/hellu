package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;

import lombok.ToString;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import xyz.vppiet.hellu.ServiceManagedChannelMessage;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.eventlisteners.EventListener;

@Getter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class ServicedChannelMessage extends ServiceManagedChannelMessage {

	private final Service sourceService;

	public ServicedChannelMessage(ChannelMessageEvent event,
								  EventListener<ChannelMessageEvent> sourceListener,
								  ServiceManager sourceServiceManager,
								  Service sourceService) {
		super(event, sourceListener, sourceServiceManager);
		this.sourceService = sourceService;
	}

	public ServicedChannelMessage(ServiceManagedChannelMessage serviceManagedChannelMessage, Service sourceService) {
		this(serviceManagedChannelMessage.getEvent(),
				serviceManagedChannelMessage.getSourceListener(),
				serviceManagedChannelMessage.getSourceServiceManager(),
				sourceService);
	}
}
