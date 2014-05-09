package frameWork.base.database.sql.create.table;

import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.create.AS;

public class TABLE_AS {
	public final AS AS;
	
	public TABLE_AS(final String sql, final Table<?> table) {
		AS = new AS(sql + "TABLE " + table.getName() + " ");
	}
	
}
