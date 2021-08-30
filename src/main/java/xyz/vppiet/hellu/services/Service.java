package xyz.vppiet.hellu.services;

import xyz.vppiet.hellu.Observer;
import xyz.vppiet.hellu.Subject;

import java.util.Set;

public interface Service extends Observer {
	Service addCommand(Command c);
	boolean containsCommand(Command c);
	Set<Command> getCommands();
	String getDescription();
	String getName();
	void handleCommandInvoke(Subject sub, CommandInvoke ci);
	Service removeCommand(Command c);
}
