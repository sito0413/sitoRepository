package frameWork.databaseConnector.pool;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import frameWork.ThrowableUtil;
import frameWork.databaseConnector.DatabaseConnector;

class ConnectorImp implements DatabaseConnector {
	private final Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	ConnectorImp(final Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public synchronized void executeQuery(final String paramString) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			}
			catch (final SQLException e) {
				//NOOP
			}
			resultSet = null;
		}
		resultSet = statement.executeQuery(paramString);
	}
	
	@Override
	public synchronized void executeUpdate(final String paramString) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			}
			catch (final SQLException e) {
				//NOOP
			}
			resultSet = null;
		}
		statement.executeUpdate(paramString);
	}
	
	@Override
	public synchronized void commit() throws SQLException {
		connection.commit();
	}
	
	@Override
	public synchronized void rollback() throws SQLException {
		connection.rollback();
	}
	
	@Override
	public synchronized void close() {
		try {
			connection.rollback();
		}
		catch (final SQLException e) {
			//NOOP
		}
		try {
			if (resultSet != null) {
				try {
					resultSet.close();
				}
				catch (final SQLException e) {
					//NOOP
				}
				resultSet = null;
			}
			if (statement != null) {
				try {
					statement.close();
				}
				catch (final SQLException e) {
					//NOOP
				}
				statement = null;
			}
			connection.close();
		}
		catch (final SQLException e) {
			//NOOP
		}
	}
	
	@Override
	public Iterator<Iterable<String>> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		try {
			return ((resultSet != null) && resultSet.next());
		}
		catch (final SQLException e) {
			ThrowableUtil.throwable(e);
			return false;
		}
	}
	
	@Override
	public Row next() {
		return new Row(resultSet);
	}
	
	@Override
	public void remove() {
		//NOOP
	}
}
