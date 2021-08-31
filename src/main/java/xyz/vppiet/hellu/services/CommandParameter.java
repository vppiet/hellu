package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@Getter(AccessLevel.PUBLIC)
@ToString
public class CommandParameter {

	private final String name;
	private final boolean numeric;

	public CommandParameter(String name, boolean numeric) {
		this.name = name;
		this.numeric = numeric;
	}
}
