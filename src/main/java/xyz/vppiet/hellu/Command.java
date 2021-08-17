package xyz.vppiet.hellu;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import java.beans.PropertyChangeListener;

public interface Command<T extends ReplyableEvent> extends PropertyChangeListener {
	String getDescription();
	String getName();
	boolean matchesFilter(String msg);
}
