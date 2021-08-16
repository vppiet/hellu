package xyz.vppiet.hellu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelluSettingsTest {

	@Test
	void create_PropertiesFile_DoesNotThrow() {
		String path = "src/test/resources/hellu-test.properties";

		assertDoesNotThrow(() -> {
			HelluSettings config = HelluSettings.load(path);
		});
	}
}