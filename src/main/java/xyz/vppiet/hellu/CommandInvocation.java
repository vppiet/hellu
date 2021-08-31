package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import org.kitteh.irc.client.library.event.helper.MessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@ToString
public final class CommandInvocation {

	private static final String COMMAND_SEPARATOR = CommandProperties.COMMAND_SEPARATOR;
	private static final String PARAM_SEPARATOR = CommandProperties.PARAM_SEPARATOR;

	private static final Pattern commandSeparatorPattern = Pattern.compile(COMMAND_SEPARATOR);
	private static final Pattern paramSeparatorPattern = Pattern.compile(PARAM_SEPARATOR);

	private final String prefix;
	private final String service;
	private final String command;
	private final List<String> params;
	private final boolean matchingService;

	public CommandInvocation(String service, String command, List<String> params, boolean matchingService) {
		this.prefix = CommandProperties.SERVICE_PREFIX;
		this.service = service;
		this.command = command;
		this.params = params;
		this.matchingService = matchingService;
	}

	public static CommandInvocation from(MessageEvent event) {
		final String msg = Objects.requireNonNull(event).getMessage().strip();
		final String service = parseService(msg, CommandProperties.SERVICE_PREFIX);
		final String command = parseCommand(msg);
		final List<String> params = parseParams(msg);

		final boolean matchingService = command.isEmpty() && params.isEmpty();

		return new CommandInvocation(service, command, params, matchingService);
	}

	public static String parseService(String msg, String prefix) {
		String msgWithoutPrefix = msg.substring(prefix.length());
		String[] parts = commandSeparatorPattern.split(msgWithoutPrefix);

		return parts[0];
	}

	public static String parseCommand(String msg) {
		String[] parts = commandSeparatorPattern.split(msg, 3);

		if (parts.length < 2) return "";

		return parts[1];
	}

	public static List<String> parseParams(String msg) {
		String[] parts = commandSeparatorPattern.split(msg, 3);

		if (parts.length < 3) return new ArrayList<>();

		return Arrays.stream(paramSeparatorPattern.split(parts[2]))
				.map(String::strip)
				.collect(Collectors.toUnmodifiableList());
	}
}
