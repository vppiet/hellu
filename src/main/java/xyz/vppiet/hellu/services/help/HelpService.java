package xyz.vppiet.hellu.services.help;

import lombok.ToString;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.ServiceManagedMessage;
import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.services.Command;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServiceBase;
import xyz.vppiet.hellu.services.ServicedMessage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ToString(callSuper = true)
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
	public void handleServiceManagedMessage(ServiceManagedMessage smm) {
		CommandInvocation ci = smm.getCommandInvocation();
		ReplyableEvent re = smm.getReplyableEvent();
		if (ci.isMatchingService()) {
			this.replyWithHelp(re);

			return;
		}

		// help service's command
		String command = ci.getCommand();
		if (this.getCommand(command).isPresent()) {
			ServicedMessage sm = new ServicedMessage(smm, this);
			this.notifyObservers(this, sm);

			return;
		}

		// commands of other services
		ServiceManager sm = smm.getSourceServiceManager();
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
