package frameWork.base.database.sql.insert;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.SQL;
import frameWork.base.database.sql.select.SelectSQL;

public class INTO {
	private final String base;
	
	public INTO(final String sql, final Table<?> table, final Field<?>... fields) {
		final StringBuilder builder = new StringBuilder();
		for (final Field<?> field : fields) {
			builder.append(", ").append(field.getName());
		}
		this.base = sql + "INTO " + table.getName() + " ( " + builder.substring(1) + ") ";
	}
	
	public SQL<InsertSQL> VALUES(final Field<?>... fields) {
		final StringBuilder builder = new StringBuilder();
		for (final Field<?> field : fields) {
			builder.append(", ").append(field.getSqlValue());
		}
		final String sql = base + "VALUES (" + builder.substring(1) + ") ";
		return new SQL<InsertSQL>() {
			@Override
			public InsertSQL toSQL() {
				return new InsertSQL(sql);
			}
		};
	}
	
	public SQL<InsertSQL> SELECT(final SelectSQL selectSQL) {
		final String sql = base + selectSQL + " ";
		return new SQL<InsertSQL>() {
			@Override
			public InsertSQL toSQL() {
				return new InsertSQL(sql);
			}
		};
	}
}