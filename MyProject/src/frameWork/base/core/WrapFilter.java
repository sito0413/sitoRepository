package frameWork.base.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
import frameWork.base.core.authority.Role;
import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.routing.Router;
import frameWork.base.core.state.ContextAttributeMap;
import frameWork.base.core.state.ImpOfState;
import frameWork.base.core.state.RequestAttributeMap;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.SessionAttributeMap;

@WebFilter("/*")
public class WrapFilter implements Filter {
	
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
	        final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		try {
			final Map<String, List<String>> parameters = new ConcurrentHashMap<>();
			final Map<String, File> fileMap = new ConcurrentHashMap<>();
			createParameters(parameters, fileMap, httpServletRequest);
			final ImpOfState state = new ImpOfState(new ContextAttributeMap(httpServletRequest.getServletContext()),
			        new SessionAttributeMap(httpServletRequest.getSession(true)), new RequestAttributeMap(
			                httpServletRequest), parameters, fileMap);
			state.setLogin((Role[]) httpServletRequest.getSession(true).getAttribute(FileSystem.Config.CALL_AUTH));
			((HttpServletResponse) servletResponse).setStatus(Router.routing(httpServletRequest.getRequestURI(),
			        httpServletRequest.getMethod().toLowerCase(),
			        new Response(servletResponse, servletResponse.getOutputStream()), state));
		}
		catch (final Exception e) {
			FileSystem.Log.logging(e);
			((HttpServletResponse) servletResponse).setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			chain.doFilter(servletRequest, servletResponse);
		}
	}
	
	private void createParameters(final Map<String, List<String>> parameters, final Map<String, File> fileMap,
	        final HttpServletRequest request) {
		try {
			Class.forName("org.apache.commons.fileupload.FileItem");
			Class.forName("org.apache.commons.fileupload.disk.DiskFileItemFactory");
			Class.forName("org.apache.commons.fileupload.servlet.ServletFileUpload");
			if (org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request)) {
				final org.apache.commons.fileupload.servlet.ServletFileUpload upload = new org.apache.commons.fileupload.servlet.ServletFileUpload(
				        new org.apache.commons.fileupload.disk.DiskFileItemFactory(
				                FileSystem.Config.MAX_UPLOADFILE_SIZE, FileSystem.Temp.UploadDir));
				final List<org.apache.commons.fileupload.FileItem> items = upload.parseRequest(request);
				final Iterator<org.apache.commons.fileupload.FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					final org.apache.commons.fileupload.FileItem item = iter.next();
					if (item.isFormField()) {
						final String name = item.getFieldName();
						final String value = item.getString();
						final List<String> list = new ArrayList<>();
						list.add(value);
						parameters.put(name, list);
					}
					else {
						final String fieldName = item.getFieldName();
						final File uploadedFile = FileSystem.Temp.UploadDir.getUploadfile(item.getName());
						item.write(uploadedFile);
						fileMap.put(fieldName, uploadedFile);
					}
				}
			}
		}
		catch (final Exception e) {
			for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				final List<String> list = new ArrayList<>();
				parameters.put(entry.getKey(), list);
				for (final String string : entry.getValue()) {
					list.add(string);
				}
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
	}
}