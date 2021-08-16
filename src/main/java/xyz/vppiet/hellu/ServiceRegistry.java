package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@Log4j2
class ServiceRegistry {
	private static final String DESCRIPTION_KEY = "description";

	private final Map<String, Map<String, String>> mapping;

	ServiceRegistry() {
		this.mapping = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	void addService(String service, String description) {
		if (this.mapping.containsKey(Objects.requireNonNull(service))) {
			this.mapping.get(service).put(DESCRIPTION_KEY, description);

			return;
		}

		Map<String, String> serviceInfo = Collections.synchronizedSortedMap(new TreeMap<>());
		serviceInfo.put(DESCRIPTION_KEY, Objects.requireNonNull(description));

		this.mapping.put(service, Objects.requireNonNull(serviceInfo));
	}

	void addCommand(String service, String command, String description) {
		if (!this.mapping.containsKey(Objects.requireNonNull(service))) {
			throw new IllegalArgumentException("No service named " + service + " found in registry");
		}

		this.mapping.get(service).put(Objects.requireNonNull(command), Objects.requireNonNull(description));
	}

	Set<String> getCommands(String service) {
		if (!this.mapping.containsKey(Objects.requireNonNull(service))) {
			throw new IllegalArgumentException("No service named " + service + " found in registry");
		}

		return this.mapping.get(service).keySet();
	}

	Map<String, String> getServiceInfo(String service) {
		if (!this.mapping.containsKey(Objects.requireNonNull(service))) {
			throw new IllegalArgumentException("No service named " + service + " found in registry");
		}

		return this.mapping.get(service);
	}

	Set<String> getServices() {
		return this.mapping.keySet();
	}
}
