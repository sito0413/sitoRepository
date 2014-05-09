package frameWork.base.database.sql;

import frameWork.base.database.scheme.Order;


public class ColumnSort {
	private final Exception exception;
	private final Order order;
	
	public ColumnSort(final Exception exception, final Order order) {
		this.exception = exception;
		this.order = order;
	}
	
	@Override
	public String toString() {
		return exception.toString() + " " + order;
	}
}