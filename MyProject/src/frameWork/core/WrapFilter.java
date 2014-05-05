package frameWork.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import frameWork.ThrowableUtil;
import frameWork.core.authority.AuthorityChecker;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.core.targetFilter.TargetFilter;
import frameWork.core.viewCompiler.ViewCompiler;

@WebFilter("/*")
@WebListener
public class WrapFilter implements Filter, ServletContextListener {
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
	        throws IOException, ServletException {
		final String target = ((HttpServletRequest) request).getRequestURI();
		final Response respons = new Response(response);
		final String method = ((HttpServletRequest) request).getMethod();
		final State state = new State((HttpServletRequest) request);
		try {
			final TargetFilter targetFilter = TargetFilter.parse(target, method.toLowerCase());
			if (targetFilter == null) {
				response(FileSystem.Resource.getResource(target), respons.getOutputStream());
			}
			else if (AuthorityChecker.check(targetFilter.className, targetFilter.methodName, state.auth())) {
				if (targetFilter.invoke(state)) {
					ViewCompiler.compile(respons, state);
				}
				else {
					response(FileSystem.Resource.getResource(state.getPage()), respons.getOutputStream());
				}
			}
			else {
				response(FileSystem.Resource.getResource(state.getPage()), respons.getOutputStream());
			}
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
			chain.doFilter(request, response);
		}
		if (!response.isCommitted()) {
			chain.doFilter(request, response);
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
	
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		// NOOP
	}
	
	@Override
	public void destroy() {
		// NOOP
	}
	
	@Override
	public void init(final FilterConfig fConfig) throws ServletException {
		// NOOP
	}
	
	@Override
	public void contextDestroyed(final ServletContextEvent Filter) {
		// NOOP
	}
}
