package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.eventlisteners.ListenedMessage;
import xyz.vppiet.hellu.services.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
public final class ServiceManager extends Subject {

	private final Hellu hellu;

	public ServiceManager(Hellu hellu) {
		this.hellu = hellu;
	}

	public ServiceManager addService(Service s) {
		this.addSubscriber(s);
		log.info("Service added: {}", s);

		return this;
	}

	public Collection<Service> getServices() {
		synchronized (this.observers) {
			return this.observers.stream()
					.filter(Service.class::isInstance)
					.map(Service.class::cast)
					.collect(Collectors.toUnmodifiableSet());
		}
	}

	public Optional<Service> getService(String s) {
		synchronized (this.observers) {
			return this.getServices().stream()
					.filter(service -> service.getName().equals(s))
					.findFirst();
		}
	}

	public Collection<String> getServiceNames() {
		synchronized (this.observers) {
			return this.getServices().stream().map(Service::getName).collect(Collectors.toUnmodifiableList());
		}
	}

	public void handleListenedMessage(ListenedMessage lm) {
		ServiceManagedMessage smm = new ServiceManagedMessage(lm, this);
		this.notifyObservers(this, smm);
	}

	public ServiceManager removeService(Service s) {
		this.removeSubscriber(s);
		log.info("Service removed: {}", s);

		return this;
	}
}
