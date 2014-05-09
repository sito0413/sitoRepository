package frameWork.base.database.sql.transaction;

import frameWork.base.database.sql.SQL;

public class COMMIT implements SQL<TransactionSQL> {
	@Override
	public TransactionSQL toSQL() {
		return new TransactionSQL("COMMIT");
	}
}