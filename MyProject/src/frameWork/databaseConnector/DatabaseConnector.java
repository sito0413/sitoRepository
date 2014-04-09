package frameWork.databaseConnector;

import java.sql.SQLException;
import java.util.Iterator;

public interface DatabaseConnector extends AutoCloseable, Iterable<Iterable<String>>, Iterator<Iterable<String>> {
	public void executeQuery(final String paramString) throws SQLException;
	
	public void executeUpdate(final String paramString) throws SQLException;
	
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
}