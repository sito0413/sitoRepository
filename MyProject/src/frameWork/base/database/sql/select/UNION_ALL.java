package frameWork.base.database.sql.select;

public class UNION_ALL {
	public final String base;
	
	public UNION_ALL(final String sql) {
		base = sql;
	}
	
	public ALL ALL(final SelectSQL selectSQL) {
		return new ALL(base, selectSQL);
	}
	
}