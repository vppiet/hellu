package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter(AccessLevel.PUBLIC)
@ToString
public abstract class CommandParameter<T extends Comparable<T>> {

	private final String name;

	@Setter(AccessLevel.PUBLIC)
	private T value;

	public CommandParameter(String name, T value) {
		this.name = name;
		this.value = value;
	}
}
