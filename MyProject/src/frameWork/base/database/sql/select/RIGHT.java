package frameWork.base.database.sql.select;


public class RIGHT {
	public final OUTER OUTER;
	public final INNER INNER;
	public final CROSS CROSS;
	
	public RIGHT(final String sql) {
		final String base = sql + "RIGHT ";
		OUTER = new OUTER(base);
		INNER = new INNER(base);
		CROSS = new CROSS(base);
	}
	
}