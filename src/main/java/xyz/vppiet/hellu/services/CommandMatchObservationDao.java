package xyz.vppiet.hellu.services;

import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.HelluSchema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
final class CommandMatchObservationDao {

	private static final String TABLE = HelluSchema.COMMAND_MATCH_OBSERVATION_TABLE;

	private final DataSource dataSource;

	CommandMatchObservationDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	boolean add(CommandMatchObservation cmo) {
		String query = String.format(
				"INSERT INTO %s VALUES ('%s', '%s', '%s')",
				TABLE, cmo.service(), cmo.command(), cmo.timestamp());

		try (Connection conn = this.dataSource.getConnection()) {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			log.error("Exception thrown while inserting a command match observation.", e);
			return false;
		}

		return true;
	}
}
