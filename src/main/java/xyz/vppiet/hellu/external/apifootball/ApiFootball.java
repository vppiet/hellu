package xyz.vppiet.hellu.external.apifootball;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ApiFootball {

	private static final String ENV_VAR = "HELLU_APIFOOTBALL";
	private static final String SCHEME = "https";
	private static final String HOST = "v3.football.api-sports.io";
	private static final String LEAGUES_PATH = "/leagues";
	private static final String FIXTURES_PATH = "/fixtures";

	private static final String KEY_HEADER = "x-apisports-key";

	private ApiFootball() {}

	// TODO: 21.9.2021 get countries and update to db
	public static boolean updateInternalCountries() {
		return false;
	}
}
