package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PACKAGE)
class CommandRegistrationEvent {
	private final String name;
	private final String description;
	private final String service;

	CommandRegistrationEvent(String name, String description, String service) {
		this.name = name;
		this.description = description;
		this.service = service;
	}
}
