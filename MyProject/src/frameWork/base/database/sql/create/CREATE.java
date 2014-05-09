package frameWork.base.database.sql.create;

import frameWork.base.database.scheme.Field;
import frameWork.base.database.scheme.Table;
import frameWork.base.database.sql.create.index.INDEX;
import frameWork.base.database.sql.create.index.UNIQUE;
import frameWork.base.database.sql.create.table.TABLE;
import frameWork.base.database.sql.create.table.TABLE_AS;
import frameWork.base.database.sql.create.table.TEMPORARY;
import frameWork.base.database.sql.create.trigger.TRIGGER;
import frameWork.base.database.sql.create.view.VIEW;

public class CREATE {
	public UNIQUE UNIQUE = new UNIQUE();
	public TEMPORARY TEMPORARY = new TEMPORARY();
	
	public INDEX INDEX(final String index) {
		return new INDEX("CREATE ", index);
	}
	
	public TABLE TABLE(final Table<?> table, final Field<?>... fields) {
		return new TABLE("CREATE ", table, fields);
	}
	
	public TABLE_AS TABLE(final Table<?> table) {
		return new TABLE_AS("CREATE ", table);
	}
	
	public TRIGGER TRIGGER(final String name) {
		return new TRIGGER(name);
	}
	
	public VIEW VIEW(final String view) {
		return new VIEW("CREATE ", view);
	}
}