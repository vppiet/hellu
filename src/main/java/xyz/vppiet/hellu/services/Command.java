package xyz.vppiet.hellu.services;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.Observer;

import java.util.List;

public interface Command extends Observer {
	String getDescription();
	String getName();
	String getService();
	String getUsageDescription();
	List<CommandParameter> getParams();
	boolean matches(CommandInvocation ci);
	void handleServicedChannelMessage(ServicedChannelMessage scm);
	void handleServicedPrivateMessage(ServicedPrivateMessage spm);
}
