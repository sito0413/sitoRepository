package frameWork.base.database.sql.drop;

import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.SQL;

public class DROP {
	public SQL<DropSQL> INDEX(final String index) {
		return new SQL<DropSQL>() {
			@Override
			public DropSQL toSQL() {
				return new DropSQL("DROP INDEX " + index + " ");
			}
		};
	}
	
	public SQL<DropSQL> TABLE(final Table<?> table) {
		return new SQL<DropSQL>() {
			@Override
			public DropSQL toSQL() {
				return new DropSQL("DROP TABLE " + table.name + " ");
			}
		};
	}
	
	public SQL<DropSQL> TRIGGER(final String name) {
		return new SQL<DropSQL>() {
			@Override
			public DropSQL toSQL() {
				return new DropSQL("DROP TRIGGER " + name + " ");
			}
		};
	}
	
	public SQL<DropSQL> VIEW(final String view) {
		return new SQL<DropSQL>() {
			@Override
			public DropSQL toSQL() {
				return new DropSQL("DROP VIEW " + view + " ");
			}
		};
	}
}