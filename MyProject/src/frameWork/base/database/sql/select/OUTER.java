package frameWork.base.database.sql.select;

import frameWork.base.database.scheme.Table;

public class OUTER {
	public final String base;
	
	public OUTER(final String sql) {
		base = sql + "OUTER ";
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