package xyz.vppiet.hellu.services;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.Observer;

public interface Command extends Observer {
	String getDescription();
	String getHelp();
	String getName();
	String getService();
	String getUsage();
	CommandParameterManager getParameterManager();
	boolean matches(CommandInvocation ci);
	void handleServicedChannelMessage(ServicedChannelMessage scm);
	void handleServicedPrivateMessage(ServicedPrivateMessage spm);
	boolean parameterCountMatches(CommandInvocation ci);
}
