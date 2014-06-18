package frameWork.base.database.sql.update;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.sql.Expression;
import frameWork.base.database.sql.SQL;

public class SET implements SQL<UpdateSQL> {
	private final String base;
	
	public SET(final String sql, final Field<?>... fields) {
		final StringBuilder builder = new StringBuilder();
		for (final Field<?> field : fields) {
			builder.append(", ").append(field.toAssignment());
		}
		base = sql + "SET " + builder.substring(1) + " ";
	}
	
	@Override
	public UpdateSQL toSQL() {
		return new UpdateSQL(base);
	}
	
	public SQL<UpdateSQL> WHERE(final Expression expression) {
		final String sql = base + "WHERE " + expression + " ";
		return new SQL<UpdateSQL>() {
			@Override
			public UpdateSQL toSQL() {
				return new UpdateSQL(sql);
			}
		};
	}
}
