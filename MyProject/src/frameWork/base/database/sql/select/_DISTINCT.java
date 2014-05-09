package frameWork.base.database.sql.select;

import frameWork.base.database.scheme.Field;

public class _DISTINCT {
	public SELECT DISTINCT(final Field<?>... fields) {
		return new SELECT("SELECT DISTINCT ", fields);
	}
}
