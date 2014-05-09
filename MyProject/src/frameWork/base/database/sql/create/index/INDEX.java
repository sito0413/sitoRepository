package frameWork.base.database.sql.create.index;

import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.ColumnSort;
import frameWork.base.database.sql.SQL;
import frameWork.base.database.sql.create.CreateSQL;

public class INDEX {
	private final String base;
	
	public INDEX(final String sql, final String index) {
		this.base = sql + "INDEX " + index + " ";
	}
	
	public SQL<CreateSQL> ON(final Table<?> table, final ColumnSort column1, final ColumnSort... columns) {
		final StringBuilder builder = new StringBuilder().append(column1);
		for (final ColumnSort column : columns) {
			builder.append(", ").append(column);
		}
		final String sql = (base + "ON " + table.name + " (" + builder + ") ");
		return new SQL<CreateSQL>() {
			@Override
			public CreateSQL toSQL() {
				return new CreateSQL(sql);
			}
		};
	}
}