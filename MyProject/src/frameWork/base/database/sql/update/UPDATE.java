package frameWork.base.database.sql.update;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;

public class UPDATE {
	private final String base;
	
	public UPDATE(final Table<?> table) {
		this.base = "UPDATE " + table.name + " ";
	}
	
	public SET SET(final Field<?>... fields) {
		return new SET(base, fields);
	}
}