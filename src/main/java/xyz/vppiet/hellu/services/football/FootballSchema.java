package xyz.vppiet.hellu.services.football;

final class FootballSchema {

	public static final String UPDATED_TABLE = "footballUpdate";
	public static final String UPDATED_SCHEMA =
			"CREATE TABLE IF NOT EXISTS footballUpdated(\n" +
					"\tresource TEXT PRIMARY KEY NOT NULL,\n" +
					"\tupdated TEXT NOT NULL CONSTRAINT updated_is_valid_datetime CHECK (updated IS datetime(updated, '+0 seconds'))\n" +
					");";

	public static final String COUNTRY_TABLE = "footballCountry";
	public static final String COUNTRY_SCHEMA =
			"CREATE TABLE IF NOT EXISTS footballCountry(\n" +
					"\tcountryCode TEXT PRIMARY KEY CONSTRAINT countryCode_length_and_upper CHECK (length(countryCode) = 2 AND countryCode = upper(countryCode)),\n" +
					"\tname TEXT\n" +
					");";

	public static final String SEASON_TABLE = "footballSeason";
	public static final String SEASON_SCHEMA =
			"CREATE TABLE IF NOT EXISTS footballSeason(\n" +
					"\tid INTEGER PRIMARY KEY NOT NULL CHECK (id > 0),\n" +
					"\tname TEXT NOT NULL,\n" +
					"\ttype TEXT NOT NULL,\n" +
					"\tcountryCode TEXT,\n" +
					"\tyear INTEGER NOT NULL CHECK (year > 1900),\n" +
					"\tcurrent BOOLEAN NOT NULL,\n" +
					"\tstartTimestamp TEXT NOT NULL CONSTRAINT startTimestamp_is_valid_date CHECK (startTimestamp IS date(startTimestamp, '+0 days')),\n" +
					"\tendTimestamp TEXT NOT NULL CONSTRAINT endTimestamp_is_valid_date CHECK (endTimestamp IS date(endTimestamp, '+0 days')),\n" +
					"\tFOREIGN KEY(countryCode) REFERENCES footballCountry(countryCode)\n" +
					");";

	private FootballSchema() {}
}
