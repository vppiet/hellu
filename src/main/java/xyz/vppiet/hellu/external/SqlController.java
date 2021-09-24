package xyz.vppiet.hellu.external;

import lombok.extern.log4j.Log4j2;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public final class SqlController {

	private static final SQLiteConnectionPoolDataSource CONNECTION_POOL = new SQLiteConnectionPoolDataSource();

	static {
		CONNECTION_POOL.setUrl("jdbc:sqlite:hellu.db");
		CONNECTION_POOL.setEnforceForeignKeys(true);
	}

	private SqlController() {}

	public static Connection getConnection() throws SQLException {
		return CONNECTION_POOL.getConnection();
	}

	public static DataSource getDataSource() {
		return CONNECTION_POOL;
	}

	public static void executeRaw(String rawSql) {
		try (Connection connection = SqlController.getConnection()) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);
			statement.executeUpdate(rawSql);
		} catch (SQLException e) {
			log.error("Exception during the execution of an SQL statement: {}", rawSql, e);
		}
	}
}
