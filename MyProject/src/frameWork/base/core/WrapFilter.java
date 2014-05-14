package frameWork.base.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frameWork.base.HttpStatus;
import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.routing.Router;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;

@WebFilter("/*")
public class WrapFilter implements Filter {
	private boolean isMultipartContent;
	
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
	        final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		try {
			((HttpServletResponse) servletResponse).setStatus(Router.routing(httpServletRequest.getRequestURI(),
			        httpServletRequest.getMethod().toLowerCase(), new Response(servletResponse), new State(
			                (HttpServletRequest) servletRequest, isMultipartContent)));
		}
		catch (final Exception e) {
			FileSystem.Log.logging(e);
			((HttpServletResponse) servletResponse).setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			chain.doFilter(servletRequest, servletResponse);
		}
	}
	
	@Override
	public void destroy() {
		// NOOP
	}
	
	@Override
	public void init(final FilterConfig fConfig) throws ServletException {
		try {
			Class.forName("org.apache.commons.fileupload.FileItem");
			Class.forName("org.apache.commons.fileupload.disk.DiskFileItemFactory");
			Class.forName("org.apache.commons.fileupload.servlet.ServletFileUpload");
			isMultipartContent = true;
		}
		catch (final Exception e) {
			isMultipartContent = false;
		}
	}
}