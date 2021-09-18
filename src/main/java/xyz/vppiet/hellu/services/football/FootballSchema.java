package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;

@Log4j2
final class FootballSchema {

	public static final String META_TABLE = "footballmeta";
	public static final String COUNTRY_TABLE = "footballcountry";
	public static final String SEASON_TABLE = "footballleague";

	public static final String META_SCHEMA =
			"CREATE TABLE IF NOT EXISTS " + META_TABLE + "(" +
					"resource TEXT PRIMARY KEY NOT NULL," +
					"updated TEXT NOT NULL CONSTRAINT valid_datetime CHECK (updated IS datetime(updated, '+0 seconds'))" +
					");";
	public static final String COUNTRY_SCHEMA =
			"CREATE TABLE IF NOT EXISTS " + COUNTRY_TABLE + "(" +
					"code TEXT PRIMARY KEY CONSTRAINT code_length_and_upper CHECK (length(code) == 2 AND code == upper(code))," +
					"name TEXT" +
					");";
	public static final String SEASON_SCHEMA =
			"CREATE TABLE IF NOT EXISTS " + SEASON_TABLE + "(" +
					"id INTEGER PRIMARY KEY NOT NULL CHECK (id > 0)," +
					"name TEXT NOT NULL," +
					"type TEXT NOT NULL," +
					"countrycode TEXT," +
					"year INTEGER NOT NULL CHECK (year > 1900)," +
					"current BOOLEAN NOT NULL," +
					"start TEXT NOT NULL CONSTRAINT valid_date CHECK (start IS date(start, '+0 days')),\n" +
					"end TEXT NOT NULL CONSTRAINT valid_date CHECK (end IS date(end, '+0 days')),\n" +
					"FOREIGN KEY(countrycode) REFERENCES " + COUNTRY_TABLE + "(code)" +
					");";

	private FootballSchema() {}
}
