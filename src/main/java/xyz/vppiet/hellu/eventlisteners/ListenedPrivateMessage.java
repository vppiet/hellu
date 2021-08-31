package xyz.vppiet.hellu.eventlisteners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;
import xyz.vppiet.hellu.CommandInvocation;

@Getter(AccessLevel.PUBLIC)
@ToString(onlyExplicitlyIncluded = true)
public class ListenedPrivateMessage {

	private final PrivateMessageEvent event;
	@ToString.Include
	private final CommandInvocation commandInvoke;
	private final EventListener<PrivateMessageEvent> sourceListener;

	public ListenedPrivateMessage(PrivateMessageEvent event, EventListener<PrivateMessageEvent> sourceListener) {
		this.event = event;
		this.commandInvoke = CommandInvocation.from(event);
		this.sourceListener = sourceListener;
	}
}
