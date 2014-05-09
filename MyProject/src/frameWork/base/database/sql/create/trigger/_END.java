package frameWork.base.database.sql.create.trigger;

import frameWork.base.database.sql.SQL;
import frameWork.base.database.sql.create.CreateSQL;
import frameWork.base.database.sql.delete.DeleteSQL;
import frameWork.base.database.sql.insert.InsertSQL;
import frameWork.base.database.sql.update.UpdateSQL;

public class _END {
	private final String base;
	
	public _END(final String base) {
		this.base = base;
	}
	
	public _END UPDATE(final UpdateSQL updateSQL) {
		return new _END(base + updateSQL + "; ");
	}
	
	public _END INSERT(final InsertSQL insertSQL) {
		return new _END(base + insertSQL + "; ");
	}
	
	public _END DELETE(final DeleteSQL deleteSQL) {
		return new _END(base + deleteSQL + "; ");
	}
	
	public SQL<CreateSQL> END() {
		return new SQL<CreateSQL>() {
			@Override
			public CreateSQL toSQL() {
				return new CreateSQL(base);
			}
		};
	}
	
}
