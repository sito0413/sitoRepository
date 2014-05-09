package frameWork.base.database.sql.select;

import frameWork.base.database.sql.ColumnSort;
import frameWork.base.database.sql.SQL;

public class ORDER_BY implements SQL<SelectSQL> {
	private final String base;
	
	public ORDER_BY(final String sql, final ColumnSort column1, final ColumnSort... columns) {
		final StringBuilder builder = new StringBuilder().append(column1);
		for (final ColumnSort column : columns) {
			builder.append(", ").append(column);
		}
		base = (sql + "ORDER BY " + builder + " ");
	}
	
	public LIMIT LIMIT(final int index) {
		return new LIMIT(base, index);
	}
	
	@Override
	public SelectSQL toSQL() {
		return new SelectSQL(base);
	}
}