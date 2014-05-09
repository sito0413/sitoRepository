package frameWork.base.database.sql.transaction;

public class TransactionSQL {
	public String sql;
	
	public TransactionSQL(final String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return sql;
	}
}
