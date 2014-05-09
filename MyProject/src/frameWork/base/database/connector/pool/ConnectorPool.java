package frameWork.base.database.connector.pool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.database.connector.DatabaseConnector;
import frameWork.base.util.ThrowableUtil;

public class ConnectorPool {
	private final String name;
	private final ConcurrentLinkedQueue<PooledConnection> busy;
	private final ArrayBlockingQueue<PooledConnection> idle;
	private Constructor<?> proxyClassConstructor;
	
	public ConnectorPool(final String name) {
		this.name = name;
		this.busy = new ConcurrentLinkedQueue<>();
		this.idle = new ArrayBlockingQueue<>(FileSystem.Config.DATABASE_CONNECTOR_POOL_CAPACITY, true);
		try {
			this.proxyClassConstructor = Proxy.getProxyClass(ConnectorPool.class.getClassLoader(), Connection.class)
			        .getConstructor(InvocationHandler.class);
		}
		catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			ThrowableUtil.throwable(e);
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
		try {
			final PooledConnection con = borrowConnection();
			return new ConnectorImp((Connection) proxyClassConstructor.newInstance(new DisposableConnectionFacade(con
			        .getHandler(this))));
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
			return null;
		}
	}
	
	private PooledConnection borrowConnection() {
		try {
			PooledConnection con = idle.poll();
			if (((con == null) || con.released) || !con.reuseValidate()) {
				if (con != null) {
					con.release();
				}
				con = new PooledConnection(name);
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