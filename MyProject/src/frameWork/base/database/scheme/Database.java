package frameWork.base.database.scheme;

import java.sql.SQLException;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.database.connector.DatabaseConnector;
import frameWork.base.database.connector.pool.ConnectorPool;
import frameWork.base.database.sql.SQLBuilder;

public abstract class Database {
	private final ConnectorPool pool;
	
	public Database(final String name) {
		this.pool = new ConnectorPool(name);
	}
	
	public abstract Table<?>[] getTables();
	
	public final DatabaseConnector getConnector() {
		return pool.getConnector();
	}
	
	public final void create() {
		final DatabaseConnector connector = getConnector();
		try {
			connector.executeUpdate(new SQLBuilder().BEGIN.toSQL().toString());
			try {
				for (final Table<?> table : getTables()) {
					table.createTable(connector);
				}
				connector.executeUpdate(new SQLBuilder().COMMIT.toSQL().toString());
			}
			catch (final SQLException e) {
				e.printStackTrace();
				connector.executeUpdate(new SQLBuilder().ROLLBACK.toSQL().toString());
			}
		}
		catch (final SQLException e) {
			FileSystem.Log.logging(e);
		}
		
	}
	
	public final void drop() {
		final DatabaseConnector connector = getConnector();
		try {
			connector.executeUpdate(new SQLBuilder().BEGIN.toSQL().toString());
			try {
				for (final Table<?> table : getTables()) {
					table.dropTable(connector);
				}
				connector.executeUpdate(new SQLBuilder().COMMIT.toSQL().toString());
			}
			catch (final SQLException e) {
				connector.executeUpdate(new SQLBuilder().ROLLBACK.toSQL().toString());
			}
		}
		catch (final SQLException e) {
			FileSystem.Log.logging(e);
		}
		
	}
}
