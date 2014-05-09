package frameWork.base.database.sql.create.index;


public class UNIQUE {
	public INDEX INDEX(final String index) {
		return new INDEX("CREATE UNIQUE ", index);
	}
}