package frameWork.base.database.sql;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.alter.ALTER;
import frameWork.base.database.sql.create.CREATE;
import frameWork.base.database.sql.delete.DELETE;
import frameWork.base.database.sql.drop.DROP;
import frameWork.base.database.sql.insert.INSERT;
import frameWork.base.database.sql.insert.REPLACE;
import frameWork.base.database.sql.select.SELECT;
import frameWork.base.database.sql.select._DISTINCT;
import frameWork.base.database.sql.transaction.BEGIN;
import frameWork.base.database.sql.transaction.COMMIT;
import frameWork.base.database.sql.transaction.ROLLBACK;
import frameWork.base.database.sql.update.UPDATE;

public final class SQLBuilder {
	public BEGIN BEGIN = new BEGIN();
	public ROLLBACK ROLLBACK = new ROLLBACK();
	public COMMIT COMMIT = new COMMIT();
	public DROP DROP = new DROP();
	public CREATE CREATE = new CREATE();
	public DELETE DELETE = new DELETE();
	public INSERT INSERT = new INSERT();
	public REPLACE REPLACE = new REPLACE();
	public _DISTINCT SELECT = new _DISTINCT();
	@Deprecated
	public ALTER ALTER = new ALTER();
	
	public UPDATE UPDATE(final Table<?> table) {
		return new UPDATE(table);
	}
	
	public SELECT SELECT(final Field<?>... fields) {
		return new SELECT("SELECT", fields);
	}
}