package xyz.vppiet.hellu.eventlisteners;

import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.CommandProperties;

import static org.junit.jupiter.api.Assertions.*;

class CommandPropertiesTest {

	@Test
	void matchesPrefixPattern() {
		int minLetters = 3;
		String prefix = ".";

		String msg01 = ".help";
		String msg02 = ".he";
		String msg03 = "....";

		assertTrue(CommandProperties.matchesPrefixPattern(msg01, prefix, minLetters));
		assertFalse(CommandProperties.matchesPrefixPattern(msg02, prefix, minLetters));
		assertFalse(CommandProperties.matchesPrefixPattern(msg03, prefix, minLetters));
	}
}