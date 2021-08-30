package xyz.vppiet.hellu.eventlisteners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessagePrefixTest {

	@Test
	void matchesPrefixPattern() {
		int minLetters = 3;
		String prefix = ".";

		String msg01 = ".help";
		String msg02 = ".he";
		String msg03 = "....";

		assertTrue(MessagePrefix.matchesPrefixPattern(msg01, prefix, minLetters));
		assertFalse(MessagePrefix.matchesPrefixPattern(msg02, prefix, minLetters));
		assertFalse(MessagePrefix.matchesPrefixPattern(msg03, prefix, minLetters));
	}
}