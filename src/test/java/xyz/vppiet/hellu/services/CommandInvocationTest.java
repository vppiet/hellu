package xyz.vppiet.hellu.services;

import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.CommandInvocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandInvocationTest {

	@Test
	void parseParams() {
		String msg01 = ".weather forecast Las Palmas, 5";
		List<String> expected01 = Arrays.asList("Las Palmas", "5");
		assertEquals(expected01, CommandInvocation.parseParams(msg01));

		String msg02 = ".help services";
		List<String> expected02 = new ArrayList<>();
		assertEquals(expected02, CommandInvocation.parseParams(msg02));
	}
}
