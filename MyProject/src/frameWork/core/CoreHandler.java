package frameWork.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import frameWork.core.authority.AuthorityChecker;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.core.targetFilter.TargetFilter;
import frameWork.core.viewCompiler.ViewCompiler;
import frameWork.databaseConnector.DatabaseConnector;
import frameWork.databaseConnector.pool.ConnectorPool;
import frameWork.utility.ThrowableUtil;

public class CoreHandler {
	private final ConnectorPool connectorPool;
	
	public CoreHandler(final File baseResource, final ConnectorPool connectorPool) {
		super();
		this.connectorPool = connectorPool;
	}
	
	public void handle(final String target, final Response respons, final String method, final State state,
	        final OutputStream outputStream) throws Exception {
		final String charsetName = "utf8";
		final TargetFilter targetFilter = TargetFilter.parse(target);
		if (targetFilter == null) {
			response(FileSystem.Resource.getResource(target), outputStream);
		}
		else {
			try {
				final Class<?> c = Class.forName(targetFilter.className);
				Method m = null;
				try {
					m = c.getMethod(targetFilter.methodName + "_" + method.toLowerCase(), State.class);
				}
				catch (final NoSuchMethodException t) {
					m = c.getMethod(targetFilter.methodName, State.class);
				}
				if (AuthorityChecker.check(c, m, state.auth())) {
					try (DatabaseConnector connector = connectorPool.getConnector()) {
						state.setConnector(connector);
						state.setPage(targetFilter.view);
						state.setViewCompiler(true);
						m.invoke(c.newInstance(), state);
						if (state.isViewCompiler()) {
							ViewCompiler.compile(respons, state, charsetName, outputStream);
						}
						else {
							response(FileSystem.Resource.getResource(state.getPage()), outputStream);
						}
					}
					catch (final NullPointerException e) {
						state.setConnector(null);
						state.setPage(targetFilter.view);
						state.setViewCompiler(true);
						m.invoke(c.newInstance(), state);
						if (state.isViewCompiler()) {
							ViewCompiler.compile(respons, state, charsetName, outputStream);
						}
						else {
							response(FileSystem.Resource.getResource(state.getPage()), outputStream);
						}
					}
				}
			}
			catch (final ClassNotFoundException | NoSuchMethodException e) {
				response(FileSystem.Resource.getResource(target), outputStream);
			}
		}
	}
	
	private void response(final File resource, final OutputStream outputStream) {
		if (resource != null) {
			try (InputStream inputStream = new FileInputStream(resource)) {
				int i = -1;
				final byte[] b = new byte[256];
				while ((i = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, i);
				}
				outputStream.flush();
			}
			catch (final IOException e) {
				ThrowableUtil.throwable(e);
			}
		}
	}
}