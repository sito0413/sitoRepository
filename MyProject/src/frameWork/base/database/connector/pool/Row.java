package frameWork.base.database.connector.pool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

class Row implements Iterable<String>, Iterator<String> {
	private final ResultSet resultSet;
	private int index;
	
	Row(final ResultSet resultSet) {
		this.resultSet = resultSet;
		this.index = 1;
	}
	
	@Override
	public boolean hasNext() {
		try {
			return !((resultSet == null) || (resultSet.getMetaData().getColumnCount() < index));
		}
		catch (final SQLException e) {
			return false;
		}
	}
	
	@Override
	public String next() {
		try {
			if (!((resultSet == null) || (resultSet.getMetaData().getColumnCount() < index))) {
				return resultSet.getString(index++);
			}
			return null;
		}
		catch (final SQLException e) {
			return null;
		}
	}
	
	@Override
	public void remove() {
		// NOOP
	}
	
	@Override
	public Iterator<String> iterator() {
		return this;
	}
}
