package xyz.vppiet.hellu.services;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.Observer;

public interface Command extends Observer {
	String getDescription();
	String getHelp();
	String getName();
	String getService();
	String getUsage();
	ParameterManager getParameterManager();
	boolean matches(CommandInvocation ci);
	void handleServicedMessage(ServicedMessage sm);
	boolean requiredParameterSizeMatches(CommandInvocation ci);
}
