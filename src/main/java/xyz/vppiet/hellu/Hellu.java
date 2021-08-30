package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.Client;

import xyz.vppiet.hellu.services.CommandInvoke;

@Log4j2
@Getter(AccessLevel.PUBLIC)
public final class Hellu implements Observer {

	private final Client ircClient;
	private final ServiceManager serviceManager;

	Hellu(IrcSettings is) {
		this.ircClient = IrcClientFactory.getInstance(is);
		this.serviceManager = new ServiceManager(this);
	}

	Hellu(HelluSettings s) {
		this(s.getIrcSettings());
	}

	@Override
	public void onNext(Subject sub, Object obj) {
		if (obj instanceof CommandInvoke) {
			CommandInvoke ci = (CommandInvoke) obj;
			this.getServiceManager().handleCommandInvokeEvent(ci);
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
