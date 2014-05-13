package frameWork.base.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import frameWork.base.core.authority.AuthorityChecker;
import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;
import frameWork.base.core.targetFilter.Target;
import frameWork.base.core.targetFilter.TargetFilter;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ViewCompiler;

@WebFilter("/*")
public class WrapFilter implements Filter {
	private boolean isMultipartContent;
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse res, final FilterChain chain)
	        throws IOException, ServletException {
		final Response response = new Response(res);
		final State state = new State((HttpServletRequest) request, isMultipartContent);
		try {
			if (!response(request, res, chain, ((HttpServletRequest) request).getRequestURI(),
			        ((HttpServletRequest) request).getMethod().toLowerCase(), response, state)) {
				if (!response(request, res, chain, FileSystem.Config.LOGIN_PATH, "get", response, state)) {
					chain.doFilter(request, res);
				}
			}
		}
		catch (final Exception e) {
			FileSystem.Log.logging(e);
			chain.doFilter(request, res);
		}
	}
	
	private boolean response(final ServletRequest request, final ServletResponse res, final FilterChain chain,
	        final String requestURI, final String method, final Response response, final State state)
	        throws FileNotFoundException, IOException, ServletException, ScriptException {
		
		final Target target = TargetFilter.parse(requestURI, method);
		if (target == null) {
			if (!response(FileSystem.Resource.getResource(requestURI), response.getOutputStream())) {
				chain.doFilter(request, res);
			}
			return true;
		}
		else if (AuthorityChecker.check(target.className, target.methodName, state.getAuth())) {
			if (target.invoke(state)) {
				ViewCompiler.compile(response, state);
			}
			else if (!response(FileSystem.Data.getResource(state.getPage()), response.getOutputStream())) {
				chain.doFilter(request, res);
			}
			return true;
		}
		else if (state.getRequest().getAttribute(FileSystem.Config.CALL_REQUEST_URI) == null) {
			state.getRequest().setAttribute(FileSystem.Config.CALL_REQUEST_URI, target);
			return false;
		}
		else {
			return false;
		}
	}
	
	private boolean response(final File resource, final OutputStream outputStream) throws FileNotFoundException,
	        IOException {
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
			return true;
		}
		return false;
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