package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter(AccessLevel.PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
final class ServiceInfo implements Cloneable {
	@ToString.Include
	private final String name;

	private final String description;

	@ToString.Include
	private Map<String, CommandInfo> commands;

	ServiceInfo(String name, String description) {
		this.name = name;
		this.description = description;
		this.commands = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	void update(CommandInfo commandInfo) {
		String name = commandInfo.getName();
		this.commands.put(name, commandInfo);
	}

	Collection<CommandInfo> getCommandInfos() {
		return this.commands.values();
	}

	CommandInfo getCommandInfo(String name) {
		return this.commands.getOrDefault(name, new CommandInfo("Command not found", "Command not found"));
	}

	boolean hasCommand(String command) {
		return this.commands.containsKey(command);
	}

	@Override
	public ServiceInfo clone() {
		try {
			ServiceInfo clone = (ServiceInfo) super.clone();
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
