package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.services.Command;
import xyz.vppiet.hellu.services.CommandInvoke;
import xyz.vppiet.hellu.services.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
public final class ServiceManager extends Subject implements Observer {

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

	public void handleCommandInvokeEvent(CommandInvoke ci) {
		this.notifyObservers(this, ci);
	}

	public ServiceManager removeService(Service s) {
		this.removeSubscriber(s);
		return this;
	}

	@Override
	public void onNext(Subject sub, Object o) {
		// handle ServiceInfo
	}
}
