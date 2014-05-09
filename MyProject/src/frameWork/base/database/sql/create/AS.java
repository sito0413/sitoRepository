package frameWork.base.database.sql.create;

import frameWork.base.database.sql.SQL;
import frameWork.base.database.sql.select.SelectSQL;

public class AS {
	private final String base;
	
	public AS(final String sql) {
		this.base = sql + "AS ";
	}
	
	public SQL<CreateSQL> SELECT(final SelectSQL selectSQL) {
		return new SQL<CreateSQL>() {
			@Override
			public CreateSQL toSQL() {
				return new CreateSQL(base + selectSQL + " ");
			}
		};
	}
}