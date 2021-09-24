package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.Client;

import xyz.vppiet.hellu.eventlisteners.ListenedMessage;
import xyz.vppiet.hellu.external.SqlController;

@Log4j2
@Getter(AccessLevel.PUBLIC)
public final class Hellu implements Observer {

	private static final String ENV_PRIVILEGED_HOST = "HELLU_PRIVILEGED_HOST";

	private final Client ircClient;
	private final ServiceManager serviceManager;

	Hellu(IrcSettings is) {
		this.ircClient = IrcClientFactory.getInstance(is);
		this.serviceManager = new ServiceManager(this);
		this.initializeSchema();
	}

	Hellu(HelluSettings s) {
		this(s.getIrcSettings());
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ListenedMessage) {
			ListenedMessage lm = (ListenedMessage) obj;
			this.getServiceManager().handleListenedMessage(lm);
		}
	}

	private void initializeSchema() {
		SqlController.executeRaw(HelluSchema.COMMAND_MATCH_OBSERVATION_SCHEMA);
	}

	public void addChannel(String c) {
		this.getIrcClient().addChannel(c);
		log.info("Channel {} added", c);
	}

	public void connect() {
		log.info("Connecting...");
		this.getIrcClient().connect();
	}

	public void disconnect() {
		log.info("Shutting down...");
		this.getIrcClient().shutdown("Get to da choppa!");
	}

	public void removeChannel(String c) {
		this.getIrcClient().removeChannel(c);
	}
}
