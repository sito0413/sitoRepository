package frameWork.base.database.scheme;

import java.sql.SQLException;

import frameWork.base.database.connector.DatabaseConnector;

public abstract class Row {
	public abstract Field<?>[] getFields();
	
	//	protected abstract Field<BigInteger> id();
	
	//	protected abstract Table<Row> table();
	
	public void update(final DatabaseConnector connector) throws SQLException {
		//		connector.executeUpdate(new SQLBuilder().UPDATE(table()).SET(getFields())
		//		        .WHERE(new Expression("id = " + id().get())).toSQL().toString());
		
	}
}