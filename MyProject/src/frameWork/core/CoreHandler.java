package frameWork.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import frameWork.ThrowableUtil;
import frameWork.core.authority.AuthorityChecker;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.core.targetFilter.TargetFilter;
import frameWork.core.viewCompiler.ViewCompiler;

public class CoreHandler {
	
	public CoreHandler() {
		super();
	}
	
	public void handle(final String target, final Response respons, final String method, final State state)
	        throws Exception {
		final TargetFilter targetFilter = TargetFilter.parse(target);
		if (targetFilter == null) {
			response(FileSystem.Resource.getResource(target), respons.getOutputStream());
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
					state.setPage(targetFilter.view);
					state.setViewCompiler(true);
					m.invoke(c.newInstance(), state);
					if (state.isViewCompiler()) {
						ViewCompiler.compile(respons, state);
					}
					else {
						response(FileSystem.Resource.getResource(state.getPage()), respons.getOutputStream());
					}
				}
			}
			catch (final ClassNotFoundException | NoSuchMethodException e) {
				response(FileSystem.Resource.getResource(target), respons.getOutputStream());
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
				outputStream.close();
			}
			catch (final IOException e) {
				ThrowableUtil.throwable(e);
			}
		}
	}
}