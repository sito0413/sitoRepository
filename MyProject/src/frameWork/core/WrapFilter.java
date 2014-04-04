package frameWork.core;

import java.io.File;
import java.io.IOException;
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

import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.databaseConnector.DatabaseConnectorKey;
import frameWork.databaseConnector.pool.ConnectorPool;
import frameWork.utility.ThrowableUtil;

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
		final OutputStream outputStream = response.getOutputStream();
		try {
			((CoreHandler) request.getServletContext().getAttribute("FRAMEWORK")).handle(target, respons, method,
			        state, outputStream);
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
			chain.doFilter(request, response);
		}
		if (!response.isCommitted()) {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		event.getServletContext().setAttribute("FRAMEWORK",
		        new CoreHandler(new File(""), new ConnectorPool(new DatabaseConnectorKey(null, null, "", ""))));
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
