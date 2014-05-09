package frameWork.base.database.sql.delete;

import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.Expression;
import frameWork.base.database.sql.SQL;

public class FROM implements SQL<DeleteSQL> {
	private final String base;
	
	public FROM(final Table<?> table) {
		this.base = "DELETE FROM " + table.name + " ";
	}
	
	@Override
	public DeleteSQL toSQL() {
		return new DeleteSQL(base);
	}
	
	public SQL<DeleteSQL> WHERE(final Expression expression) {
		final String sql = base + "WHERE " + expression;
		return new SQL<DeleteSQL>() {
			@Override
			public DeleteSQL toSQL() {
				return new DeleteSQL(sql);
			}
		};
	}
}