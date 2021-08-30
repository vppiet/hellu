package xyz.vppiet.hellu.services;

import xyz.vppiet.hellu.Observer;
import xyz.vppiet.hellu.Subject;

public interface Command extends Observer {
	String getName();
	String getDescription();
	void handleCommandInvoke(Subject sub, CommandInvoke ci);
}
