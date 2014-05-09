package frameWork.base.database.sql.select;


public class LEFT {
	public final OUTER OUTER;
	public final INNER INNER;
	public final CROSS CROSS;
	
	public LEFT(final String sql) {
		final String base = sql + "LEFT ";
		OUTER = new OUTER(base);
		INNER = new INNER(base);
		CROSS = new CROSS(base);
	}
}