package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter(AccessLevel.PUBLIC)
@Log4j2
@ToString
public abstract class CommandBase implements Command {

	protected final String name;
	protected final String description;

	public CommandBase(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
