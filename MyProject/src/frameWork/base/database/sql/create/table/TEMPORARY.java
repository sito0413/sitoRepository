package frameWork.base.database.sql.create.table;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.create.view.VIEW;

public class TEMPORARY {
	public TABLE TABLE(final Table<?> table, final Field<?>... fields) {
		return new TABLE("CREATE TEMPORARY ", table, fields);
	}
	
	public TABLE_AS TABLE(final Table<?> table) {
		return new TABLE_AS("CREATE TEMPORARY ", table);
	}
	
	public VIEW VIEW(final String view) {
		return new VIEW("CREATE TEMPORARY ", view);
	}
	
}