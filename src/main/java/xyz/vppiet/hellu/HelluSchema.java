package xyz.vppiet.hellu;

public final class HelluSchema {

	public static final String COMMAND_MATCH_OBSERVATION_TABLE = "commandMatchObservation";
	public static final String COMMAND_MATCH_OBSERVATION_SCHEMA =
			"CREATE TABLE IF NOT EXISTS commandMatchObservation(\n" +
					"\tservice TEXT NOT NULL,\n" +
					"\tcommand TEXT NOT NULL,\n" +
					"\ttimestamp TEXT NOT NULL CONSTRAINT timestamp_is_valid_datetime CHECK (datetime(timestamp) IS NOT NULL)\n" +
					");";

	private HelluSchema() {
	}
}
