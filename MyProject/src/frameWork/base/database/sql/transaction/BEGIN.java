package frameWork.base.database.sql.transaction;

import frameWork.base.database.sql.SQL;

public class BEGIN implements SQL<TransactionSQL> {
	@Override
	public TransactionSQL toSQL() {
		return new TransactionSQL("BEGIN");
	}
}
