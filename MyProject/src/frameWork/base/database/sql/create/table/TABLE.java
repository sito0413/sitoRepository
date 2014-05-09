package frameWork.base.database.sql.create.table;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.SQL;
import frameWork.base.database.sql.create.CreateSQL;

public class TABLE implements SQL<CreateSQL> {
	private final String base;
	
	public TABLE(final String sql, final Table<?> table, final Field<?>... fields) {
		final StringBuilder builder = new StringBuilder();
		for (final Field<?> field : fields) {
			builder.append(",").append(field.toColumnDefine());
		}
		this.base = (sql + "TABLE " + table.getName() + " (" + builder.substring(1) + ") ");
	}
	
	@Override
	public CreateSQL toSQL() {
		return new CreateSQL(base);
	}
}