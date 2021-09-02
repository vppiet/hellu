package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.ServiceManagedChannelMessage;
import xyz.vppiet.hellu.ServiceManagedPrivateMessage;
import xyz.vppiet.hellu.Subject;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
@ToString
public abstract class ServiceBase extends Subject implements Service {

	protected final String name;
	protected final String description;

	public ServiceBase(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public Service addCommand(Command c) {
		if (!c.getService().equals(this.name)) {
			log.warn("Command's service name doesn't match this service's name. Command was not added.");

			return this;
		}

		this.addSubscriber(c);
		return this;
	}

	@Override
	public boolean containsCommand(Command c) {
		return this.getCommands().contains(c);
	}

	@Override
	public Optional<Command> getCommand(String c) {
		synchronized (this.observers) {
			return this.getCommands().stream()
					.filter(command -> command.getName().equals(c))
					.findFirst();
		}
	}

	@Override
	public Collection<String> getCommandNames() {
		synchronized (this.observers) {
			return this.getCommands().stream().map(Command::getName).collect(Collectors.toUnmodifiableSet());
		}
	}

	@Override
	public Set<Command> getCommands() {
		synchronized (this.observers) {
			return this.observers.stream()
					.filter(Command.class::isInstance)
					.map(Command.class::cast)
					.collect(Collectors.toUnmodifiableSet());
		}
	}

	@Override
	public String getHelp() {
		String commands = this.getCommands().stream().map(Command::getName).collect(Collectors.joining(", "));
		return this.getDescription() + " Commands: " + commands;
	}

	@Override
	public void handleServiceManagedChannelMessage(ServiceManagedChannelMessage smcm) {
		CommandInvocation ci = smcm.getCommandInvocation();

		if (ci.isMatchingService()) {
			ReplyableEvent re = smcm.getEvent();
			this.replyWithHelp(re);
		} else {
			ServicedChannelMessage scm = new ServicedChannelMessage(smcm, this);
			this.notifyObservers(this, scm);
		}
	}

	@Override
	public void handleServiceManagedPrivateMessage(ServiceManagedPrivateMessage smpm) {
		CommandInvocation ci = smpm.getCommandInvoke();

		if (ci.isMatchingService()) {
			ReplyableEvent re = smpm.getEvent();
			this.replyWithHelp(re);
		} else {
			ServicedPrivateMessage spm = new ServicedPrivateMessage(smpm, this);
			this.notifyObservers(this, spm);
		}
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ServiceManagedChannelMessage) {
			ServiceManagedChannelMessage smcm = (ServiceManagedChannelMessage) obj;
			CommandInvocation ci = smcm.getCommandInvocation();

			if (!this.matches(ci)) return;

			this.handleServiceManagedChannelMessage(smcm);
		} else if (obj instanceof ServiceManagedPrivateMessage) {
			ServiceManagedPrivateMessage smpm = (ServiceManagedPrivateMessage) obj;
			CommandInvocation ci = smpm.getCommandInvoke();

			if (!this.matches(ci)) return;

			this.handleServiceManagedPrivateMessage(smpm);
		}
	}

	@Override
	public boolean matches(CommandInvocation ci) {
		String service = ci.getService();
		return service.equals(this.name);
	}

	@Override
	public void replyWithHelp(ReplyableEvent event) {
		String reply = this.getHelp();
		event.sendReply(reply);
	}

	@Override
	public Service removeCommand(Command c) {
		this.removeSubscriber(c);
		return this;
	}
}
