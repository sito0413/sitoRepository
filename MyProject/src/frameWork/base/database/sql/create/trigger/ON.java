package frameWork.base.database.sql.create.trigger;

import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.Expression;

public class ON {
	private final String base;
	
	public ON(final String sql, final Table<?> table) {
		this.base = "ON " + table.getName() + " ";
	}
	
	public WHEN WHEN(final Expression expression) {
		return new WHEN(base, expression);
	}
}