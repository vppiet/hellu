package xyz.vppiet.hellu;

import java.util.Objects;

public final class CommandProperties {

	public static final String SERVICE_PREFIX = ".";
	public static final String COMMAND_SEPARATOR = " ";
	public static final String PARAM_SEPARATOR = ", ";
	public static final int MIN_LETTERS = 2;

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
