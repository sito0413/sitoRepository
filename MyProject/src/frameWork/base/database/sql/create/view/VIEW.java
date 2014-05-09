package frameWork.base.database.sql.create.view;

import frameWork.base.database.sql.create.AS;

public class VIEW {
	public final AS AS;
	
	public VIEW(final String sql, final String view) {
		this.AS = new AS(sql + "VIEW " + view + " ");
	}
}