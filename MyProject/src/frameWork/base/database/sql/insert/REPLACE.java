package frameWork.base.database.sql.insert;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;

public class REPLACE {
	public INTO INTO(final Table<?> table, final Field<?>... columns) {
		return new INTO("REPLACE ", table, columns);
	}
}