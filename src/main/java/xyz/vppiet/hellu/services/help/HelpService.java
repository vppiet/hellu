package xyz.vppiet.hellu.services.help;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.ServiceManagedChannelMessage;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.services.Command;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServiceBase;
import xyz.vppiet.hellu.services.ServicedChannelMessage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HelpService extends ServiceBase {

	private static final String NAME = "help";
	private static final String DESCRIPTION =
			"All commands belong to a service. List all services by issuing '.help services'." +
			" Get service information by issuing '.help <service>'." +
			" Get command information by issuing '.help <service> <command>'.";

	public HelpService() {
		super(NAME, DESCRIPTION);
	}

	@Override
	public void handleServiceManagedChannelMessage(ServiceManagedChannelMessage smcm) {
		CommandInvocation ci = smcm.getCommandInvocation();
		ReplyableEvent re = smcm.getEvent();
		if (ci.isMatchingService()) {
			this.replyWithHelp(re);

			return;
		}

		String command = ci.getCommand();
		Optional<Command> c = this.getCommand(command);
		if (c.isPresent()) {
			ServicedChannelMessage scm = new ServicedChannelMessage(smcm, this);
			this.notifyObservers(this, scm);

			return;
		}

		ServiceManager sm = smcm.getSourceServiceManager();
		Optional<Service> s = sm.getService(ci.getCommand());
		if (s.isEmpty()) {
			Collection<String> serviceNames = sm.getServiceNames();
			this.replyWithServiceNotFound(re, serviceNames);

			return;
		}

		this.handleServiceHelp(re, ci, sm, s.get());
	}

	private void handleServiceHelp(ReplyableEvent re, CommandInvocation ci, ServiceManager sm, Service s) {
		List<String> params = ci.getParams();
		if (params.size() > 0) {
			this.handleCommandHelp(re, ci, s);

			return;
		}

		this.replyWithServiceHelp(re, s);
	}

	private void handleCommandHelp(ReplyableEvent re, CommandInvocation ci, Service s) {
		String commandName = ci.getParams().get(0);
		Optional<Command> c = s.getCommand(commandName);
		if (c.isEmpty()) {
			String serviceName = s.getName();
			String commandNames = String.join(", ", s.getCommandNames());
			this.replyWithCommandNotFound(re, serviceName, commandNames);

			return;
		}

		this.replyWithCommandHelp(re, c.get());
	}

	private void replyWithCommandHelp(ReplyableEvent re, Command c) {
		String commandHelp = c.getHelp();
		re.sendReply(commandHelp);
	}

	private void replyWithCommandNotFound(ReplyableEvent re, String sName, String cNames) {
		re.sendReply("Command not found. Service " + sName + " has commands: " + cNames);
	}

	private void replyWithServiceHelp(ReplyableEvent re, Service s) {
		String serviceHelp = s.getHelp();
		re.sendReply(serviceHelp);
	}

	private void replyWithServiceNotFound(ReplyableEvent re, Collection<String> serviceNames) {
		String services = String.join(", ", serviceNames);
		String helpCommands = String.join(", ", this.getCommandNames());
		re.sendReply("Service/command not found. Help commands: " + helpCommands + ". All services: " + services + ".");
	}
}
