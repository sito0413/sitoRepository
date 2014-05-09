package frameWork.base.database.scheme;

import java.sql.SQLException;

import frameWork.base.database.connector.DatabaseConnector;
import frameWork.base.database.sql.SQLBuilder;

public abstract class Table<T extends Row> {
	
	private final String name;
	
	public Table(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract T createRow();
	
	public abstract Field<?>[] getFields();
	
	public final void insert(final DatabaseConnector connector, final T row) throws SQLException {
		connector.executeUpdate(new SQLBuilder().INSERT.INTO(this, getFields()).VALUES(row.getFields()).toSQL()
		        .toString());
	}
	
	public final void update(final DatabaseConnector connector, final T row) throws SQLException {
		connector.executeUpdate(new SQLBuilder().UPDATE(this).SET(row.getFields()).toSQL().toString());
	}
	
	public final void createTable(final DatabaseConnector connector) throws SQLException {
		connector.executeUpdate(new SQLBuilder().CREATE.TABLE(this, getFields()).toSQL().toString());
	}
	
	public final void dropTable(final DatabaseConnector connector) throws SQLException {
		connector.executeUpdate(new SQLBuilder().DROP.TABLE(this).toSQL().toString());
	}
}
