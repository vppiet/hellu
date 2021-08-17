package xyz.vppiet.hellu;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@Log4j2
@ToString
final class ServiceRegister implements Cloneable {
	private Map<String, ServiceInfo> services;

	ServiceRegister() {
		this.services = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	void update(ServiceInfo serviceInfo) {
		String name = Objects.requireNonNull(serviceInfo).getName();
		this.services.put(name, serviceInfo);
	}

	Collection<ServiceInfo> getServiceInfos() {
		return this.services.values();
	}

	ServiceInfo getServiceInfo(String name) {
		return this.services.getOrDefault(name, new ServiceInfo("Service not found", "Service not found"));
	}

	Set<String> getServiceNames() {
		return this.services.keySet();
	}

	CommandInfo getCommand(String service, String command) {
		return this.getServiceInfo(service).getCommandInfo(command);
	}

	@Override
	public ServiceRegister clone() {
		try {
			ServiceRegister clone = (ServiceRegister) super.clone();
			clone.services = Collections.synchronizedSortedMap(new TreeMap<>());
			this.services.forEach((key, value) -> {
				ServiceInfo cloneValue = value.clone();
				clone.services.put(key, cloneValue);
			});

			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public boolean hasService(String service) {
		return this.services.containsKey(service);
	}

	public boolean hasCommand(String service, String command) {
		return this.services.containsKey(service) && this.services.get(service).hasCommand(command);
	}
}
