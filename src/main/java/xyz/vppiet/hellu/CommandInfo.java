package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@Getter(AccessLevel.PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
final class CommandInfo implements Cloneable {
	@ToString.Include
	private final String name;

	private final String description;

	CommandInfo(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public CommandInfo clone() {
		try {
			return (CommandInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
