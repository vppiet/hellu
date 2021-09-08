package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Getter(AccessLevel.PACKAGE)
@Log4j2
final class HelluSettings {

	private final IrcSettings ircSettings;
	private final DatabaseSettings databaseSettings;
	private final ServiceSettings serviceSettings;

	HelluSettings(String host, int port, String nick, boolean debug, String dbUrl, Map<String, String> serviceSettings) {
		this.ircSettings = new IrcSettings(host, port, nick, debug);
		this.databaseSettings = new DatabaseSettings(dbUrl);
		this.serviceSettings = new ServiceSettings(serviceSettings);
	}

	static HelluSettings load(String path) throws IOException {
		final Properties props = new Properties();
		final FileInputStream in = new FileInputStream(path);
		props.load(in);
		in.close();

		final String host = parseStringProperty(props, IrcSettings.HOST_PROPERTY);
		final int port = parseIntegerProperty(props, IrcSettings.PORT_PROPERTY);
		final String nick = parseStringPropertyOrDefault(props, IrcSettings.NICK_PROPERTY, IrcSettings.DEFAULT_NICK);
		final boolean debug = parseBooleanPropertyOrDefault(props, IrcSettings.DEBUG_PROPERTY, false);

		final String dbUrl = parseStringPropertyOrDefault(props, DatabaseSettings.URL_PROPERTY,
				DatabaseSettings.DEFAULT_URL);

		Map<String, String> serviceSettingMapping = new HashMap<>();

		props.keySet().stream()
				.map(k -> (String) k)
				.filter(k -> k.startsWith(ServiceSettings.PROPERTY_PREFIX))
				.forEach(k -> {
					String value = props.getProperty(k);
					serviceSettingMapping.put(k, value);
				});

		return new HelluSettings(host, port, nick, debug, dbUrl, serviceSettingMapping);
	}

	private static boolean parseBooleanProperty(Properties props, String property) {
		String strValue = props.getProperty(property);

		if (Objects.isNull(strValue) || strValue.isEmpty()) {
			throw new IllegalArgumentException(property + " property is not set");
		}

		return Boolean.parseBoolean(strValue);
	}

	private static boolean parseBooleanPropertyOrDefault(Properties props, String property, boolean defaultValue) {
		String strValue = props.getProperty(property);

		if (Objects.isNull(strValue) || strValue.isEmpty()) {
			log.info("{} is defaulting to {}", property, defaultValue);

			return defaultValue;
		}

		return parseBooleanProperty(props, property);
	}

	private static int parseIntegerProperty(Properties props, String property) {
		String strValue = props.getProperty(property);

		if (Objects.isNull(strValue) || strValue.isEmpty()) {
			throw new IllegalArgumentException(property + " property is not set");
		}

		try {
			return Integer.parseInt(strValue);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(property + " is not an integer");
		}
	}

	private static int parseIntegerPropertyOrDefault(Properties props, String property, int defaultValue) {
		String strValue = props.getProperty(property);

		if (Objects.isNull(strValue) || strValue.isEmpty()) {
			log.info("{} is defaulting to {}", property, defaultValue);

			return defaultValue;
		}

		return parseIntegerProperty(props, property);
	}

	private static String parseStringProperty(Properties props, String property) {
		String value = props.getProperty(property);

		if (Objects.isNull(value) || value.isEmpty()) {
			throw new IllegalArgumentException(property + " property is not set");
		}

		return value;
	}

	private static String parseStringPropertyOrDefault(Properties props, String property, String defaultValue) {
		String value = props.getProperty(property);

		if (Objects.isNull(value) || value.isEmpty()) {
			log.info("{} is defaulting to {}", property, defaultValue);

			return defaultValue;
		}

		return parseStringProperty(props, property);
	}
}
