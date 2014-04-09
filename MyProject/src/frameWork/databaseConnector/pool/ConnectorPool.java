package frameWork.databaseConnector.pool;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import frameWork.ThrowableUtil;
import frameWork.databaseConnector.DatabaseConnector;
import frameWork.databaseConnector.DatabaseConnectorKey;

public class ConnectorPool {
	private static int CAPACITY = 100;
	private final DatabaseConnectorKey key;
	private final ConcurrentLinkedQueue<PooledConnection> busy;
	private final ArrayBlockingQueue<PooledConnection> idle;
	private Constructor<?> proxyClassConstructor;
	
	public ConnectorPool(final DatabaseConnectorKey key) {
		if (key != null) {
			this.key = key;
			this.busy = new ConcurrentLinkedQueue<>();
			this.idle = new ArrayBlockingQueue<>(CAPACITY, true);
			try {
				this.proxyClassConstructor = Proxy
				        .getProxyClass(ConnectorPool.class.getClassLoader(), Connection.class).getConstructor(
				                InvocationHandler.class);
			}
			catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
				ThrowableUtil.throwable(e);
			}
		}
		else {
			this.key = null;
			this.busy = null;
			this.idle = null;
			this.proxyClassConstructor = null;
		}
	}
	
	public void close() {
		while (idle.size() > 0) {
			final PooledConnection con = idle.poll();
			if (con != null) {
				con.release();
			}
		}
	}
	
	public DatabaseConnector getConnector() {
		if (key != null) {
			try {
				final PooledConnection con = borrowConnection();
				return new ConnectorImp((Connection) proxyClassConstructor.newInstance(new DisposableConnectionFacade(
				        con.getHandler(this))));
			}
			catch (final Exception e) {
				ThrowableUtil.throwable(e);
				return null;
			}
		}
		return null;
	}
	
	private PooledConnection borrowConnection() {
		try {
			PooledConnection con = idle.poll();
			if (((con == null) || con.released) || !con.reuseValidate()) {
				if (con != null) {
					con.release();
				}
				con = new PooledConnection(key);
			}
			busy.offer(con);
			return con;
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
			return null;
		}
	}
	
	void returnConnection(final PooledConnection con) {
		if (busy.remove(con) && !con.isDiscarded) {
			if (idle.offer(con)) {
				return;
			}
		}
		con.release();
	}
	
}