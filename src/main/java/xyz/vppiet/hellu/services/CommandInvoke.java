package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import org.kitteh.irc.client.library.event.helper.MessageEvent;
import xyz.vppiet.hellu.eventlisteners.MessagePrefix;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter(AccessLevel.PUBLIC)
@ToString
public final class CommandInvoke {

	private final String service;
	private final String command;
	private final String params;

	@ToString.Exclude
	private final MessageEvent sourceEvent;

	public CommandInvoke(String service, String command, String params, MessageEvent sourceEvent) {
		this.service = service;
		this.command = command;
		this.params = params;
		this.sourceEvent = sourceEvent;
	}

	public static CommandInvoke from(MessageEvent event) {
		final String msg = Objects.requireNonNull(event).getMessage().strip();
		final String service = parseService(msg, MessagePrefix.PREFIX);
		final String command = parseCommand(msg);
		final String params = parseParams(msg);

		return new CommandInvoke(service, command, params, event);
	}

	private static final Pattern spacePattern = Pattern.compile(" ");

	public static String parseService(String msg, String prefix) {
		String msgWithoutPrefix = msg.substring(prefix.length());
		String[] parts = spacePattern.split(msgWithoutPrefix);

		return parts[0];
	}

	public static String parseCommand(String msg) {
		String[] parts = spacePattern.split(msg, 3);

		if (parts.length < 2) return "";

		return parts[1];
	}

	public static String parseParams(String msg) {
		String[] parts = spacePattern.split(msg, 3);

		if (parts.length < 3) return "";

		return parts[2];
	}
}
