package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.Client;

import xyz.vppiet.hellu.eventlisteners.ListenedChannelMessage;
import xyz.vppiet.hellu.eventlisteners.ListenedPrivateMessage;

import javax.sql.ConnectionPoolDataSource;

@Log4j2
@Getter(AccessLevel.PUBLIC)
public final class Hellu implements Observer {

	private final Client ircClient;
	private final ServiceManager serviceManager;
	private final ConnectionPoolDataSource dataSource;

	Hellu(IrcSettings is, DatabaseSettings ds) {
		this.ircClient = IrcClientFactory.getInstance(is);
		this.serviceManager = new ServiceManager(this);
		this.dataSource = DataSourceFactory.getInstance(ds);
	}

	Hellu(HelluSettings s) {
		this(s.getIrcSettings(), s.getDatabaseSettings());
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ListenedChannelMessage) {
			ListenedChannelMessage cmi = (ListenedChannelMessage) obj;
			this.getServiceManager().handleListenedChannelMessage(cmi);
		} else if (obj instanceof ListenedPrivateMessage) {
			ListenedPrivateMessage pmi = (ListenedPrivateMessage) obj;
			this.getServiceManager().handleListenedPrivateMessage(pmi);
		}
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
		this.getIrcClient().shutdown();
	}

	public void removeChannel(String c) {
		this.getIrcClient().removeChannel(c);
	}
}
