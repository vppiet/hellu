package xyz.vppiet.hellu.eventlisteners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import xyz.vppiet.hellu.CommandInvocation;

@Getter(AccessLevel.PUBLIC)
@ToString(onlyExplicitlyIncluded = true)
public class ListenedChannelMessage {

	private final ChannelMessageEvent event;
	@ToString.Include
	private final CommandInvocation commandInvocation;
	private final EventListener<ChannelMessageEvent> sourceListener;

	public ListenedChannelMessage(ChannelMessageEvent event, EventListener<ChannelMessageEvent> sourceListener) {
		this.event = event;
		this.commandInvocation = CommandInvocation.from(event);
		this.sourceListener = sourceListener;
	}
}
