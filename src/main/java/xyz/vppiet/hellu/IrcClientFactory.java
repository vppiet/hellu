package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.Client.Builder;
import org.kitteh.irc.client.library.Client.Builder.Server.SecurityType;

import java.util.function.Consumer;

@Log4j2
final class IrcClientFactory {
	private static final Consumer<String> CLIENT_LOG_INPUT = s -> log.info("[IN] {}", s);
	private static final Consumer<String> CLIENT_LOG_OUTPUT = s -> log.info("[OUT] {}", s);
	private static final Consumer<Exception> CLIENT_LOG_EXCEPTION = e -> log.error("Exception thrown in IRC client: ", e);

	static Client getInstance(HelluSettings.IrcSettings config) {
		final Builder builder = Client.builder();

		builder.name(config.getNick())
				.nick(config.getNick())
				.realName(config.getNick())
				.user(config.getNick());

		builder.server()
				.host(config.getHost())
				.port(config.getPort(), SecurityType.INSECURE);

		if (config.isDebug()) {
			builder.listeners()
					.input(CLIENT_LOG_INPUT)
					.output(CLIENT_LOG_OUTPUT)
					.exception(CLIENT_LOG_EXCEPTION);
		}

		return builder.build();
	}
}
