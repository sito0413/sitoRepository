package frameWork.base.database.sql.select;

import frameWork.base.database.sql.SQL;

public class LIMIT implements SQL<SelectSQL> {
	private final String base;
	
	public LIMIT(final String sql, final int index) {
		base = sql + "LIMIT " + index;
	}
	
	@Override
	public SelectSQL toSQL() {
		return new SelectSQL(base);
	}
	
	public SQL<SelectSQL> OFFSET(final int index) {
		final String sql = base + "OFFSET " + index;
		return new SQL<SelectSQL>() {
			@Override
			public SelectSQL toSQL() {
				return new SelectSQL(sql);
			}
		};
	}
	
}