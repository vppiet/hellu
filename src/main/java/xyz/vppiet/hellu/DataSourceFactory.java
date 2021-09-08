package xyz.vppiet.hellu;

import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.ConnectionPoolDataSource;

public final class DataSourceFactory {

	public static ConnectionPoolDataSource getInstance(DatabaseSettings ds) {
		final SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);

		final SQLiteConnectionPoolDataSource pooledDataSource = new SQLiteConnectionPoolDataSource(config);

		final String url = ds.getUrl();
		pooledDataSource.setUrl(url);

		return pooledDataSource;
	}
}
