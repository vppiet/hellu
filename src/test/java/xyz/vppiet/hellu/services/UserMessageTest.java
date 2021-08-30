package xyz.vppiet.hellu.services;

import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.eventlisteners.MessagePrefix;

import static org.junit.jupiter.api.Assertions.*;

class UserMessageTest {

	@Test
	void testParseService() {
		String msg01 = ".help";
		String expected01 = "help";
		assertEquals(expected01, CommandInvoke.parseService(msg01, MessagePrefix.PREFIX));

		String msg02 = ".help services";
		String expected02 = "help";
		assertEquals(expected02, CommandInvoke.parseService(msg02, MessagePrefix.PREFIX));
	}

	@Test
	void parseCommand() {
		String msg01 = ".help";
		String expected01 = "";
		assertEquals(expected01, CommandInvoke.parseCommand(msg01));

		String msg02 = ".help services";
		String expected02 = "services";
		assertEquals(expected02, CommandInvoke.parseCommand(msg02));
	}

	@Test
	void parseParams() {
		String msg01 = ".weather city";
		String expected01 = "";
		assertEquals(expected01, CommandInvoke.parseParams(msg01));

		String msg02 = ".weather city Malmö";
		String expected02 = "Malmö";
		assertEquals(expected02, CommandInvoke.parseParams(msg02));

		String msg03 = ".weather city Las Palmas";
		String expected03 = "Las Palmas";
		assertEquals(expected03, CommandInvoke.parseParams(msg03));
	}
}