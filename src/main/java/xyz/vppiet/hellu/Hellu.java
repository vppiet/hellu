package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import java.util.HashMap;
import java.util.Map;

@Log4j2
final class Hellu {

	private final Client ircClient;
	private final Map<String, EventListener<? extends ReplyableEvent>> listeners = new HashMap<>();

	Hellu(HelluSettings settings) {
		this.ircClient = IrcClientFactory.getInstance(settings.getIrcClient());
	}

	Client getIrcClient() {
		return this.ircClient;
	}

	void connect() {
		log.info("Connecting...");
		this.getIrcClient().connect();
	}

	void disconnect() {
		log.info("Shutting down...");
		this.getIrcClient().shutdown();
	}

	void joinChannel(String channel) {
		this.getIrcClient().addChannel(channel);
	}

	void addListener(EventListener<? extends ReplyableEvent> listener) {
		this.ircClient.getEventManager().registerEventListener(listener);
		this.listeners.put(listener.getName(), listener);
	}
}
