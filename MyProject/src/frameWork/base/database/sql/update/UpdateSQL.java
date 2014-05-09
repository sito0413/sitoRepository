package frameWork.base.database.sql.update;

public class UpdateSQL {
	public String sql;
	
	public UpdateSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
