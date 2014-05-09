package frameWork.base.database.sql.create;


public class CreateSQL   {
	public String sql;

	public CreateSQL(final String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return sql;
	}
}
