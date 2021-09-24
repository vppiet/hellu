package xyz.vppiet.hellu.services;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.Observer;
import xyz.vppiet.hellu.ServiceManagedMessage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface Service extends Observer {
	Service addCommand(Command c);
	boolean containsCommand(Command c);
	Optional<Command> getCommand(String c);
	Collection<String> getCommandNames();
	Set<Command> getCommands();
	String getDescription();
	String getHelp();
	String getName();
	boolean matches(CommandInvocation ci);
	void replyWithHelp(ReplyableEvent re);
	void handleServiceManagedMessage(ServiceManagedMessage smm);
	Service removeCommand(Command c);
}
