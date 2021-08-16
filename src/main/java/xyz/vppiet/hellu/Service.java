package xyz.vppiet.hellu;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import java.beans.PropertyChangeListener;

public interface Service<T extends ReplyableEvent> extends PropertyChangeListener {
	String getDescription();
	String getHelp();
	String getName();
	void addPropertyChangeListener(String property, PropertyChangeListener listener);
}
