package frameWork.base.database.connector.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.util.ThrowableUtil;

class PooledConnection {
	private final Driver driver;
	private final String name;
	private volatile Handler handler;
	volatile boolean released = false;
	volatile Connection innerConnection;
	volatile boolean isDiscarded = true;
	
	PooledConnection(final String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
	        SQLException {
		this.name = name;
		this.driver = (java.sql.Driver) Class.forName(
		        FileSystem.Config.getString("DatabaseDriverClassName", "org.sqlite.JDBC")).newInstance();
		this.innerConnection = this.driver.connect(
		        FileSystem.Config.getString("DatabaseURL", FileSystem.Data.getName() + name + ".data"),
		        new Properties());
		this.innerConnection.setAutoCommit(false);
		this.isDiscarded = false;
	}
	
	private void connect() {
		try {
			if (released) {
				return;
			}
			if (innerConnection != null) {
				disconnect();
			}
			innerConnection = driver.connect(
			        FileSystem.Config.getString("DatabaseURL", FileSystem.Data.getName() + name + ".data"),
			        new Properties());
			this.isDiscarded = false;
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
		}
	}
	
	private void disconnect() {
		final Connection c = innerConnection;
		isDiscarded = true;
		innerConnection = null;
		if (c != null) {
			try {
				c.close();
			}
			catch (final Exception e) {
				ThrowableUtil.throwable(e);
			}
		}
	}
	
	void release() {
		disconnect();
		if (handler != null) {
			handler.set(null, null);
			handler = null;
		}
		released = true;
	}
	
	Handler getHandler(final ConnectorPool connectionPool) {
		if (handler == null) {
			handler = new Handler();
		}
		handler.set(this, connectionPool);
		return handler;
	}
	
	boolean reuseValidate() {
		if (!isDiscarded && (innerConnection == null)) {
			connect();
		}
		if (!isDiscarded) {
			try (Statement stmt = innerConnection.createStatement()) {
				stmt.execute("SELECT 1;");
				return true;
			}
			catch (final Exception e) {
				ThrowableUtil.throwable(e);
			}
		}
		disconnect();
		connect();
		return !isDiscarded;
	}
}