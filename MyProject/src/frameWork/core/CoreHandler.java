package frameWork.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import frameWork.core.authority.AuthorityChecker;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.filter.TargetFilter;
import frameWork.core.viewCompiler.ViewCompiler;
import frameWork.databaseConnector.DatabaseConnector;
import frameWork.databaseConnector.pool.ConnectorPool;
import frameWork.utility.Response;
import frameWork.utility.ThrowableUtil;
import frameWork.utility.state.State;

public class CoreHandler {
	private final File tempDir;
	private final File viewerDir;
	private final ConnectorPool connectorPool;
	
	public CoreHandler(final File baseResource, final ConnectorPool connectorPool) {
		super();
		this.tempDir = new File(baseResource, "temp");
		this.viewerDir = new File(baseResource, "viewer");
		this.tempDir.mkdirs();
		this.viewerDir.mkdirs();
		this.connectorPool = connectorPool;
	}
	
	public void handle(final String target, final Response respons, final String charsetName, final String method,
	        final State state, final OutputStream outputStream) throws Exception {
		final TargetFilter targetFilter = TargetFilter.parse(target);
		if (targetFilter == null) {
			response(FileSystem.Resource.getResource(target), outputStream);
			return;
		}
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
					state.setResultType(null);
					m.invoke(c.newInstance(), state);
					if (state.getResultType() == null) {
						ViewCompiler.compile(respons, state, charsetName, outputStream);
					}
					else {
						response(FileSystem.Resource.getResource(state.getResultType()), outputStream);
					}
				}
			}
		}
		catch (final ClassNotFoundException | NoSuchMethodException e) {
			//NOOP
		}
	}
	
	private void response(final File resource, final OutputStream outputStream) {
		if (resource != null) {
			try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
				int i = -1;
				while ((i = reader.read()) != -1) {
					outputStream.write(i);
				}
				outputStream.flush();
			}
			catch (final IOException e) {
				ThrowableUtil.throwable(e);
			}
		}
	}
}