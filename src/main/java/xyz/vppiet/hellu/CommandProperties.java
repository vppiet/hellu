package xyz.vppiet.hellu;

import xyz.vppiet.hellu.services.CommandParameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CommandProperties {

	public static final String SERVICE_PREFIX = ".";
	public static final String COMMAND_SEPARATOR = " ";
	public static final String PARAM_SEPARATOR = ", ";
	public static final int MIN_LETTERS = 2;

	public static String getUsageDescription(String service, String command, List<CommandParameter> params) {
		// usageDescription:	.weather current <city>, <days>
		// ex:					.weather current Las Palmas, 5

		if (params.isEmpty()) {
			return SERVICE_PREFIX + service + COMMAND_SEPARATOR + command;
		}

		String strParams = params.stream().map(p -> "<" + p.getName() + ">").collect(Collectors.joining(PARAM_SEPARATOR));
		return SERVICE_PREFIX + service + COMMAND_SEPARATOR + command + COMMAND_SEPARATOR + strParams;
	}

	public static boolean matchesPrefixPattern(String msg) {
		return matchesPrefixPattern(msg, SERVICE_PREFIX, MIN_LETTERS);
	}

	public static boolean matchesPrefixPattern(String msg, String prefix) {
		return matchesPrefixPattern(msg, prefix, MIN_LETTERS);
	}

	public static boolean matchesPrefixPattern(String msg, String prefix, int minLetters) {
		if (minLetters < 0) {
			throw new IllegalArgumentException("Minimum amount of letters followed by the prefix" +
					"should be equal or greater than zero");
		}

		Objects.requireNonNull(msg);
		Objects.requireNonNull(prefix);

		if (msg.length() < prefix.length() + minLetters) return false;
		if (!msg.startsWith(prefix)) return false;

		for (int i = prefix.length(); i <= minLetters; i++) {
			if (!Character.isLetter(msg.charAt(i))) return false;
		}

		return true;
	}
}
