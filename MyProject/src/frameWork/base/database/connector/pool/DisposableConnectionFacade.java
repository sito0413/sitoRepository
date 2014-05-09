package frameWork.database.connector.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

import javax.sql.XAConnection;

class DisposableConnectionFacade implements InvocationHandler {
	private volatile Handler handler = null;
	
	DisposableConnectionFacade(final Handler handler) {
		this.handler = (handler);
	}
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		if ("equals".equals(method.getName())) {
			return Boolean.valueOf(this.equals(Proxy.getInvocationHandler(args[0])));
		}
		else if ("hashCode".equals(method.getName())) {
			return Integer.valueOf(this.hashCode());
		}
		else if (handler == null) {
			if ("isClosed".equals(method.getName())) {
				return Boolean.TRUE;
			}
			else if ("close".equals(method.getName())) {
				return null;
			}
			else if ("isValid".equals(method.getName())) {
				return Boolean.FALSE;
			}
		}
		try {
			if ("isClosed".equals(method.getName())) {
				return isClose();
			}
			if ("close".equals(method.getName())) {
				if (!isClose()) {
					handler.close();
				}
				return null;
			}
			else if ("toString".equals(method.getName())) {
				return "[" + (isClose() ? "null" : handler.toString()) + "]";
			}
			if (isClose()) {
				throw new SQLException("Connection has already been closed.");
			}
			if ("unwrap".equals(method.getName())) {
				if (args[0] == PooledConnection.class) {
					return handler.getPooledConnection();
				}
				else if (args[0] == XAConnection.class) {
					return null;
				}
				else if (((Class<?>) args[0]).isInstance(handler.getPooledConnection().innerConnection)) {
					return handler.getPooledConnection().innerConnection;
				}
				else {
					throw new SQLException("Not a wrapper of " + ((Class<?>) args[0]).getName());
				}
			}
			else if ("isWrapperFor".equals(method.getName())) {
				return Boolean
				        .valueOf((((Class<?>) args[0]).isInstance(handler.getPooledConnection().innerConnection)));
			}
			final PooledConnection pooledConnection = handler.getPooledConnection();
			if (pooledConnection != null) {
				return method.invoke(pooledConnection.innerConnection, args);
			}
			throw new SQLException("Connection has already been closed.");
		}
		finally {
			if ("close".equals(method.getName())) {
				handler = null;
			}
		}
	}
	
	private boolean isClose() {
		return (handler == null) || handler.isClose();
	}
	
}