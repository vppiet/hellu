package xyz.vppiet.hellu;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import java.beans.PropertyChangeListener;

public interface EventListener<T extends ReplyableEvent> extends PropertyChangeListener {
	String getName();
	void addPropertyChangeListener(String property, PropertyChangeListener subscriber);
}
