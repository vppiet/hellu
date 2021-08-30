package xyz.vppiet.hellu.eventlisteners;

import net.engio.mbassy.listener.Handler;
import org.kitteh.irc.client.library.event.helper.ClientEvent;
import xyz.vppiet.hellu.Hellu;

public interface EventListener<T extends ClientEvent> {
	void addHellu(Hellu h);
	@Handler void handleEvent(T event);
	void removeHellu(Hellu h);
}
