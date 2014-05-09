package frameWork.base.database.sql.select;


public class FULL {
	public final OUTER OUTER;
	public final INNER INNER;
	public final CROSS CROSS;
	
	public FULL(final String sql) {
		final String base = sql + "FULL ";
		OUTER = new OUTER(base);
		INNER = new INNER(base);
		CROSS = new CROSS(base);
	}
}