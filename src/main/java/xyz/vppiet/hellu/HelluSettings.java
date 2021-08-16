package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Getter(AccessLevel.PACKAGE)
final class HelluSettings {
	private final IrcSettings ircClient;

	HelluSettings(String host, int port, String nick, boolean debug) {
		this.ircClient = new IrcSettings(host, port, nick, debug);
	}

	@Getter(AccessLevel.PACKAGE)
	final static class IrcSettings {
		private static final String HOST_PROPERTY = "irc.host";
		private static final String PORT_PROPERTY = "irc.port";
		private static final String NICK_PROPERTY = "irc.nick";
		private static final String DEBUG_PROPERTY = "irc.debug";

		private final String host;
		private final int port;
		private final String nick;
		private final boolean debug;

		IrcSettings(String host, int port, String nick, boolean debug) {
			this.host = host;
			this.port = port;
			this.nick = nick;
			this.debug = debug;
		}
	}

	static HelluSettings load(String path) throws IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream(path);
		props.load(in);
		in.close();

		final String host = parseStringProperty(IrcSettings.HOST_PROPERTY,
				props.getProperty(IrcSettings.HOST_PROPERTY, ""));

		final int port = parseIntegerProperty(IrcSettings.PORT_PROPERTY,
				props.getProperty(IrcSettings.PORT_PROPERTY, ""));

		final String nick = parseStringProperty(IrcSettings.NICK_PROPERTY,
				props.getProperty(IrcSettings.NICK_PROPERTY, ""));

		final boolean debug = parseBooleanProperty(IrcSettings.DEBUG_PROPERTY,
				props.getProperty(IrcSettings.DEBUG_PROPERTY, "false"));

		return new HelluSettings(host, port, nick, debug);
	}

	private static boolean parseBooleanProperty(String propertyName, String propertyValue) {
		if (Objects.isNull(propertyValue)) {
			throw new IllegalArgumentException(propertyName + " property has not been set");
		}

		return Boolean.parseBoolean(propertyValue);
	}

	private static int parseIntegerProperty(String propertyName, String propertyValue) {
		if (Objects.isNull(propertyValue)) {
			throw new IllegalArgumentException(propertyName + " property has not been set");
		}

		try {
			return Integer.parseInt(propertyValue);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(propertyName + " is not an integer");
		}
	}

	private static String parseStringProperty(String propertyName, String propertyValue) {
		if (Objects.isNull(propertyValue) || propertyValue.isEmpty()) {
			throw new IllegalArgumentException(propertyName + " property has not been set");
		}

		return propertyValue;
	}
}
