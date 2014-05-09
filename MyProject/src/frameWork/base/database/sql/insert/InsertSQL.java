package frameWork.base.database.sql.insert;

public class InsertSQL {
	public String sql;
	
	public InsertSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
