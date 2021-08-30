package xyz.vppiet.hellu.eventlisteners;

import org.kitteh.irc.client.library.event.helper.ClientEvent;
import xyz.vppiet.hellu.Hellu;
import xyz.vppiet.hellu.Subject;

public abstract class EventListenerBase<T extends ClientEvent> extends Subject implements EventListener<T> {

	@Override
	public void addHellu(Hellu h) {
		this.addSubscriber(h);
		h.getIrcClient().getEventManager().registerEventListener(this);
	}

	@Override
	public void removeHellu(Hellu h) {
		h.getIrcClient().getEventManager().unregisterEventListener(this);
		this.removeSubscriber(h);
	}
}
