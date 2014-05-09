package frameWork.base.database.sql.select;

public class SelectSQL {
	public String sql;
	
	public SelectSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
