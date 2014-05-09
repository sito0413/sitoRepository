package frameWork.base.database.sql.alter;

public class AlterSQL {
	public String sql;
	
	public AlterSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
