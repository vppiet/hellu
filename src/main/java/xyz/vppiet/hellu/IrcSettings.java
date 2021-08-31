package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
final class IrcSettings {

	static final String HOST_PROPERTY = "irc.host";
	static final String PORT_PROPERTY = "irc.port";
	static final String NICK_PROPERTY = "irc.nick";
	static final String DEBUG_PROPERTY = "irc.debug";

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
