package frameWork.base.database.sql.transaction;

import frameWork.base.database.sql.SQL;

public class ROLLBACK implements SQL<TransactionSQL> {
	@Override
	public TransactionSQL toSQL() {
		return new TransactionSQL("ROLLBACK");
	}
}