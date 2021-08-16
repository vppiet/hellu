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
class CommandRegistry {
	@ToString.Include
	private final String service;

	private final Map<String, String> mapping;

	CommandRegistry(String service) {
		this.service = service;
		this.mapping = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	void addCommand(String name, String description) {
		this.mapping.put(Objects.requireNonNull(name), Objects.requireNonNull(description));
	}

	Set<String> getCommands() {
		return this.mapping.keySet();
	}
}
