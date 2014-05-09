package frameWork.base.core;

import java.io.File;
import java.io.FileInputStream;
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
import frameWork.base.core.viewCompiler.ViewCompiler;
import frameWork.base.util.ThrowableUtil;

@WebFilter("/*")
public class WrapFilter implements Filter {
	private boolean isMultipartContent;
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
	        throws IOException, ServletException {
		final String requestURI = ((HttpServletRequest) request).getRequestURI();
		final Response respons = new Response(response);
		final String method = ((HttpServletRequest) request).getMethod();
		final State state = new State((HttpServletRequest) request, isMultipartContent);
		try {
			final Target target = TargetFilter.parse(requestURI, method.toLowerCase());
			if (target == null) {
				response(FileSystem.Resource.getResource(requestURI), respons.getOutputStream());
			}
			else if (AuthorityChecker.check(target.className, target.methodName, state.getAuth())) {
				if (target.invoke(state)) {
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
	public void destroy() {
		// NOOP
	}
	
	@Override
	public void init(final FilterConfig fConfig) throws ServletException {
		// NOOP
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