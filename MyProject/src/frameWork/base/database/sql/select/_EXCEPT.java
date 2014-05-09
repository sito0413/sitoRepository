package frameWork.base.database.sql.select;

import frameWork.base.database.sql.ColumnSort;
import frameWork.base.database.sql.SQL;

public class _EXCEPT implements SQL<SelectSQL> {
	private final String base;
	public final UNION_ALL UNION;
	
	public _EXCEPT(final String sql, final SelectSQL selectSQL) {
		base = sql + "EXCEPT " + selectSQL + " ";
		UNION = new UNION_ALL(base);
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