package xyz.vppiet.hellu.services;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.Observer;
import xyz.vppiet.hellu.ServiceManagedChannelMessage;
import xyz.vppiet.hellu.ServiceManagedPrivateMessage;

import java.util.Set;

public interface Service extends Observer {
	Service addCommand(Command c);
	boolean containsCommand(Command c);
	Set<Command> getCommands();
	String getDescription();
	String getHelp();
	String getName();
	boolean matches(CommandInvocation ci);
	void replyWithHelp(ReplyableEvent re);
	void handleServiceManagedChannelMessage(ServiceManagedChannelMessage smcm);
	void handleServiceManagedPrivateMessage(ServiceManagedPrivateMessage smpm);
	Service removeCommand(Command c);
}
