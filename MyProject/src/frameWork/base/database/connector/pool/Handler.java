package frameWork.base.database.connector.pool;

class Handler {
	private PooledConnection pooledConnection = null;
	private ConnectorPool pool = null;
	
	void close() {
		if (!isClose()) {
			pool.returnConnection(pooledConnection);
		}
	}
	
	boolean isClose() {
		return (pool == null) || (pooledConnection == null) || pooledConnection.isDiscarded;
	}
	
	void set(final PooledConnection pc, final ConnectorPool cp) {
		pooledConnection = pc;
		pool = cp;
	}
	
	@Override
	public String toString() {
		return pooledConnection.toString();
	}
	
	PooledConnection getPooledConnection() {
		return pooledConnection;
	}
}