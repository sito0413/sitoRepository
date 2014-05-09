package frameWork.base.database.sql.create.trigger;

import frameWork.base.database.sql.Expression;

public class WHEN {
	public final BEGIN BEGIN;
	
	public WHEN(final String sql, final Expression expression) {
		this.BEGIN = new BEGIN(sql + "WHEN " + expression + " ");
	}
}