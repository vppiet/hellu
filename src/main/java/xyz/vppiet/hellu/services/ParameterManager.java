package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.CommandProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
public final class ParameterManager {

	private final List<String> required;
	private final List<String> optional;

	public ParameterManager() {
		this.required = Collections.synchronizedList(new ArrayList<>());    // TODO: new
		this.optional = Collections.synchronizedList(new ArrayList<>());    // TODO: new
	}

	public ParameterManager addRequired(String requiredParameter) {
		this.getRequired().add(requiredParameter);
		return this;
	}

	public ParameterManager addOptional(String optionalParameter) {
		this.getOptional().add(optionalParameter);
		return this;
	}

	public int getSize() {
		return this.getRequired().size() + this.getOptional().size();
	}

	public String getUsage() {
		String requiredUsage = this.required.stream()
				.map(param -> "<" + param + ">")
				.collect(Collectors.joining(CommandProperties.PARAM_SEPARATOR));
		String optionalUsage = this.getOptional().stream()
				.map(param -> "[" + param + "]")
				.collect(Collectors.joining(CommandProperties.PARAM_SEPARATOR));

		return String.join(CommandProperties.PARAM_SEPARATOR, requiredUsage, optionalUsage);
	}
}
