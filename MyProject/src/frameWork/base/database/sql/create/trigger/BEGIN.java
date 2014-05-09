package frameWork.base.database.sql.create.trigger;

import frameWork.base.database.sql.delete.DeleteSQL;
import frameWork.base.database.sql.insert.InsertSQL;
import frameWork.base.database.sql.update.UpdateSQL;

public class BEGIN {
	private final String base;
	
	public BEGIN(final String sql) {
		this.base = sql + "BEGIN ";
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
}