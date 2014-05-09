package frameWork.base.database.sql.delete;

public class DeleteSQL {
	public String sql;
	
	public DeleteSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
