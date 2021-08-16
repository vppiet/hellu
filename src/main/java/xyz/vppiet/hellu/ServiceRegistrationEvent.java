package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PACKAGE)
class ServiceRegistrationEvent {
	private final String name;
	private final String description;

	ServiceRegistrationEvent(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
