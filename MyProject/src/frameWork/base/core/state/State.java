package frameWork.base.core.state;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import frameWork.base.core.authority.Role;
import frameWork.base.core.fileSystem.FileSystem;

public class State {
	private Role[] auth;
	private final AttributeMap context;
	private final AttributeMap session;
	private final AttributeMap request;
	private final Map<String, List<String>> parameters;
	private final Map<String, File> fileMap;
	private String page;
	private boolean isViewCompiler;
	private boolean isLogin;
	
	public State(final HttpServletRequest request, final boolean isMultipartContent) {
		this.context = new ContextAttributeMap(request.getServletContext());
		this.session = new SessionAttributeMap(request.getSession(true));
		this.request = new RequestAttributeMap(request);
		this.parameters = new ConcurrentHashMap<>();
		this.fileMap = new ConcurrentHashMap<>();
		
		this.auth = (Role[]) session.getAttribute(FileSystem.Config.CALL_AUTH);
		if (auth == null) {
			isLogin = false;
			setAuth(new Role[] {
				FileSystem.Config.DEFAULT_ROLE
			});
		}
		else {
			isLogin = true;
		}
		if (isMultipartContent && org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request)) {
			try {
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
			catch (final Exception e) {
				createParameters(request);
			}
		}
		else {
			createParameters(request);
		}
	}
	
	private void createParameters(@SuppressWarnings("hiding") final HttpServletRequest request) {
		for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			final List<String> list = new ArrayList<>();
			parameters.put(entry.getKey(), list);
			for (final String string : entry.getValue()) {
				list.add(string);
			}
		}
	}
	
	public Role[] getAuth() {
		return auth;
	}
	
	public void setAuth(final Role[] auth) {
		this.auth = auth;
		this.session.setAttribute(FileSystem.Config.CALL_AUTH, auth);
	}
	
	public AttributeMap getContext() {
		return context;
	}
	
	public AttributeMap getSession() {
		return session;
	}
	
	public AttributeMap getRequest() {
		return request;
	}
	
	public final String getParameter(final String name) {
		if (parameters.get(name) == null) {
			return null;
		}
		return parameters.get(name).get(0);
	}
	
	public final File getFile(final String name) {
		return fileMap.get(name);
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(final String page) {
		this.page = page;
	}
	
	public boolean isViewCompiler() {
		return isViewCompiler;
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	
	public void setViewCompiler(final boolean isViewCompiler) {
		this.isViewCompiler = isViewCompiler;
	}
}