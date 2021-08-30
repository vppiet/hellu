package xyz.vppiet.hellu;

import org.junit.jupiter.api.Test;
import org.kitteh.irc.client.library.Client;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class IrcSettingsFactoryTest {

	@Test
	void getInstance_Properties_Client() throws IOException {
		String path = "src/test/resources/hellu-test.properties";
		HelluSettings config = HelluSettings.load(path);
		IrcSettings ircSettings = config.getIrcSettings();

		assertDoesNotThrow(() -> {
			Client client = IrcClientFactory.getInstance(ircSettings);
		});
	}
}