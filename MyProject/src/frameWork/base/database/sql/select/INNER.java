package frameWork.base.database.sql.select;

import frameWork.base.database.scheme.Table;

public class INNER {
	public final String base;
	
	public INNER(final String sql) {
		base = sql + "INNER ";
	}
	
	public JOIN JOIN(final String sql, final String view) {
		return new JOIN(sql, view);
	}
	
	public JOIN JOIN(final String sql, final Table<?> table) {
		return new JOIN(sql, table);
	}
	
	public JOIN JOIN(final String sql, final SelectSQL selectSQL) {
		return new JOIN(sql, selectSQL);
	}
}