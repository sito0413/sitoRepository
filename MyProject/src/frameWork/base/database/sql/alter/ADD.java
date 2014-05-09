package frameWork.base.database.sql.alter;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.sql.SQL;

public class ADD {
	private final String base;
	
	public ADD(final String sql) {
		this.base = (sql + "ADD ");
	}
	
	public SQL<AlterSQL> COLUMN(final Field<?> field) {
		final String sql = (base + "COLUMN " + field.toColumnDefine() + " ");
		return new SQL<AlterSQL>() {
			@Override
			public AlterSQL toSQL() {
				return new AlterSQL(sql);
			}
		};
	}
	
}
