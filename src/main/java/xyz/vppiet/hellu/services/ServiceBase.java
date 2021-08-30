package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.Subject;

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
		this.addSubscriber(c);
		return this;
	}

	@Override
	public boolean containsCommand(Command c) {
		return this.getCommands().contains(c);
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
	public Service removeCommand(Command c) {
		this.removeSubscriber(c);
		return this;
	}
}
