package frameWork.base.database.sql.alter;

import frameWork.base.database.scheme.Table;

public class TABLE {
	public final ADD ADD;
	
	public TABLE(final String sql, final Table<?> table) {
		this.ADD = new ADD(sql + "TABLE " + table.name + " ");
	}
}
