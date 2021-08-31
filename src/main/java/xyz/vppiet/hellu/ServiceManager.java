package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.eventlisteners.ListenedChannelMessage;
import xyz.vppiet.hellu.eventlisteners.ListenedPrivateMessage;
import xyz.vppiet.hellu.services.Command;
import xyz.vppiet.hellu.services.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
public final class ServiceManager extends Subject {

	public static final String SERVICE_PREFIX = "";
	public static final String COMMAND_SEPARATOR = " ";

	private final Hellu hellu;

	public ServiceManager(Hellu hellu) {
		this.hellu = hellu;
	}

	public ServiceManager addService(Service s) {
		this.addSubscriber(s);
		return this;
	}

	public Set<Service> getServices() {
		synchronized (this.observers) {
			return this.observers.stream()
					.filter(Service.class::isInstance)
					.map(Service.class::cast)
					.collect(Collectors.toUnmodifiableSet());
		}
	}

	public Set<Service> getServicesByCommand(Command c) {
		synchronized (this.observers) {
			return this.getServices().stream()
					.filter(s -> s.containsCommand(c))
					.collect(Collectors.toUnmodifiableSet());
		}
	}

	public void handleListenedChannelMessage(ListenedChannelMessage lcm) {
		ServiceManagedChannelMessage smcm = new ServiceManagedChannelMessage(lcm, this);
		this.notifyObservers(this, smcm);
	}

	public void handleListenedPrivateMessage(ListenedPrivateMessage lpm) {
		ServiceManagedPrivateMessage smpm = new ServiceManagedPrivateMessage(lpm, this);
		this.notifyObservers(this, smpm);
	}

	public ServiceManager removeService(Service s) {
		this.removeSubscriber(s);
		return this;
	}
}
