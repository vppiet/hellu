package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.ServiceManagedMessage;
import xyz.vppiet.hellu.Subject;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
@ToString(onlyExplicitlyIncluded = true)
public abstract class ServiceBase extends Subject implements Service {

	@ToString.Include
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

		if (this.containsCommand(c)) return this;

		this.addSubscriber(c);
		log.info("Command added: {}", c);

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
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ServiceManagedMessage) {
			ServiceManagedMessage smm = (ServiceManagedMessage) obj;
			if (!this.matches(smm.getCommandInvocation())) return;

			this.handleServiceManagedMessage(smm);
		}
	}

	@Override
	public void handleServiceManagedMessage(ServiceManagedMessage smm) {
		CommandInvocation ci = smm.getCommandInvocation();
		if (ci.isMatchingService()) {
			this.replyWithHelp(smm.getReplyableEvent());
		} else {
			ServicedMessage sm = new ServicedMessage(smm, this);
			this.notifyObservers(this, sm);
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
		if (!this.containsCommand(c)) return this;

		this.removeSubscriber(c);
		log.info("Command removed: {}", c);

		return this;
	}
}
