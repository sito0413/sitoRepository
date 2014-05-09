package frameWork.base.database.sql.drop;

public class DropSQL {
	public String sql;
	
	public DropSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
