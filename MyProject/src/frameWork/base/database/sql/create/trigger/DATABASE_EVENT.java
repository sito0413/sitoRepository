package frameWork.base.database.sql.create.trigger;

import frameWork.base.database.scheme.Table;

public class DATABASE_EVENT {
	private final String base;
	
	public DATABASE_EVENT(final String sql) {
		this.base = sql;
	}
	
	public ON ON(final Table<?> table) {
		return new ON(base, table);
	}
}