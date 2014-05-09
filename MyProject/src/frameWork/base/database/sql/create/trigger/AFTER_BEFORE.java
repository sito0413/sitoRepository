package frameWork.base.database.sql.create.trigger;

public class AFTER_BEFORE {
	public final DATABASE_EVENT DELETE;
	public final DATABASE_EVENT INSERT;
	public final DATABASE_EVENT UPDATE;
	
	public AFTER_BEFORE(final String sql) {
		this.DELETE = new DATABASE_EVENT(sql + "DELETE ");
		this.INSERT = new DATABASE_EVENT(sql + "INSERT ");
		this.UPDATE = new DATABASE_EVENT(sql + "UPDATE ");
	}
}