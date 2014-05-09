package frameWork.base.database.sql.create.trigger;

public class TRIGGER {
	public final AFTER_BEFORE BEFORE;
	public final AFTER_BEFORE AFTER;
	
	public TRIGGER(final String name) {
		final String base = "CREATE TRIGGER " + name + " ";
		this.BEFORE = new AFTER_BEFORE(base + " BEFORE ");
		this.AFTER = new AFTER_BEFORE(base + " AFTER ");
	}
}