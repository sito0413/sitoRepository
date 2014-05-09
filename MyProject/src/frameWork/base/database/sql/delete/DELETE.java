package frameWork.base.database.sql.delete;

import frameWork.base.database.scheme.Table;

public class DELETE {
	public FROM FROM(final Table<?> table) {
		return new FROM(table);
	}
}