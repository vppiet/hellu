package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
final class DatabaseSettings {

	static final String DEFAULT_URL = "jdbc:sqlite:hellu.db";

	static final String URL_PROPERTY = "db.url";

	private final String url;

	DatabaseSettings(String url) {
		this.url = url;
	}
}
