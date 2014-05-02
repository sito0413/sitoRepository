package frameWork.database.connector.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;

import frameWork.ThrowableUtil;
import frameWork.database.connector.DatabaseConnector;

class PooledConnection {
	private final DatabaseConnector key;
	private final Driver driver;
	private volatile Handler handler;
	volatile boolean released = false;
	volatile Connection innerConnection;
	volatile boolean isDiscarded = true;
	
	PooledConnection(final DatabaseConnector key) throws InstantiationException, IllegalAccessException,
	        ClassNotFoundException, SQLException {
		this.key = key;
		this.driver = (java.sql.Driver) Class.forName(key.getDriverClassName()).newInstance();
		this.innerConnection = this.driver.connect(key.getUrl(), key.getProperties());
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
			innerConnection = driver.connect(key.getUrl(), key.getProperties());
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