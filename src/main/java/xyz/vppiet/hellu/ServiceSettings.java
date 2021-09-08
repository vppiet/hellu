package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Map;

@Getter(AccessLevel.PACKAGE)
final class ServiceSettings {

	public static final String PROPERTY_PREFIX = "service.";

	private final Map<String, String> mapping;

	ServiceSettings(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	String getProperty(String property) {
		return this.getMapping().get(property);
	}
}
