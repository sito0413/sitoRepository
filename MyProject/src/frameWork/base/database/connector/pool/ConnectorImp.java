package frameWork.base.database.connector.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.database.connector.DatabaseConnector;

class ConnectorImp implements DatabaseConnector {
	private final Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	ConnectorImp(final Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public synchronized void executeQuery(final String paramString) throws SQLException {
		System.out.println(paramString);
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
		System.out.println(paramString);
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
	public synchronized void close() {
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
			FileSystem.Log.logging(e);
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
