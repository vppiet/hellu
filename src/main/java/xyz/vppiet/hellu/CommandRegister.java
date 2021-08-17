package xyz.vppiet.hellu;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
final class CommandRegister implements Cloneable {
	@ToString.Include
	private final String service;

	private Map<String, CommandInfo> commands;

	CommandRegister(String service) {
		this.service = service;
		this.commands = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	void update(CommandInfo commandInfo) {
		String name = Objects.requireNonNull(commandInfo).getName();
		this.commands.put(name, commandInfo);
	}

	Set<String> getCommands() {
		return this.commands.keySet();
	}

	@Override
	public CommandRegister clone() {
		try {
			CommandRegister clone = (CommandRegister) super.clone();
			clone.commands = Collections.synchronizedSortedMap(new TreeMap<>());
			this.commands.forEach((key, value) -> {
				CommandInfo cloneValue = value.clone();
				clone.commands.put(key, cloneValue);
			});

			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
