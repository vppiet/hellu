package xyz.vppiet.hellu.eventlisteners;

import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.subscription.SubscriptionContext;

import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.CommandProperties;
import xyz.vppiet.hellu.MessageType;

@Listener
public final class ChannelMessageListener extends EventListenerBase<ChannelMessageEvent> {

	@Override
	@Handler(delivery = Invoke.Asynchronously, filters = {@Filter(MessageFilter.class)}, rejectSubtypes = true)
	public void handleEvent(ChannelMessageEvent event) {
		String message = event.getMessage();
		MessageType type = MessageType.CHANNEL;
		User user = event.getActor();
		CommandInvocation ci = CommandInvocation.from(event);

		ListenedMessage listenedMessage = new ListenedMessage(event, this, event, message, type, user, ci);
		this.notifyObservers(this, listenedMessage);
	}

	public static final class MessageFilter implements IMessageFilter<ChannelMessageEvent> {
		@Override
		public boolean accepts(ChannelMessageEvent event, SubscriptionContext context) {
			String msg = event.getMessage();

			return CommandProperties.matchesPrefixPattern(msg);
		}
	}
}
