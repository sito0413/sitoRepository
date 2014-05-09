package frameWork.base.database.sql.alter;

import frameWork.base.database.scheme.Table;

public class ALTER {
	public TABLE TABLE(final Table<?> table) {
		return new TABLE("ALTER ", table);
	}
}
