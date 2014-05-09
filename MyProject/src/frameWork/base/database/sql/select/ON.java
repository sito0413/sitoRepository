package frameWork.base.database.sql.select;

import frameWork.base.database.sql.ColumnSort;
import frameWork.base.database.sql.Expression;
import frameWork.base.database.sql.SQL;

public class ON implements SQL<SelectSQL> {
	
	private final String base;
	public final UNION_ALL UNION;
	public final LEFT LEFT;
	public final RIGHT RIGHT;
	public final FULL FULL;
	
	public ON(final String sql, final Expression expression) {
		base = sql + "ON " + expression + " ";
		UNION = new UNION_ALL(base);
		LEFT = new LEFT(base);
		RIGHT = new RIGHT(base);
		FULL = new FULL(base);
	}
	
	public WHERE WHERE(final Expression expression) {
		return new WHERE(base, expression);
	}
	
	public GROUP_BY GROUP_BY(final Expression expression1, final Expression... expressions) {
		return new GROUP_BY(base, expression1, expressions);
	}
	
	public HAVING HAVING(final Expression expression) {
		return new HAVING(base, expression);
	}
	
	public _UNION UNION(final SelectSQL selectSQL) {
		return new _UNION(base, selectSQL);
	}
	
	public _INTERSECT INTERSECT(final SelectSQL selectSQL) {
		return new _INTERSECT(base, selectSQL);
	}
	
	public _EXCEPT EXCEPT(final SelectSQL selectSQL) {
		return new _EXCEPT(base, selectSQL);
	}
	
	public ORDER_BY ORDER_BY(final ColumnSort column1, final ColumnSort... columns) {
		return new ORDER_BY(base, column1, columns);
	}
	
	public LIMIT LIMIT(final int index) {
		return new LIMIT(base, index);
	}
	
	@Override
	public SelectSQL toSQL() {
		return new SelectSQL(base);
	}
}