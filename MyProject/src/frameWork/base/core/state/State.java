package frameWork.core.state;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import frameWork.ThrowableUtil;
import frameWork.core.authority.Role;
import frameWork.core.fileSystem.FileSystem;

public class State {
	
	private final Role[] auth;
	private final AttributeMap context;
	private final AttributeMap session;
	private final AttributeMap request;
	private final Map<String, List<String>> parameters;
	private final Map<String, File> fileMap;
	private String page;
	private boolean isViewCompiler;
	
	public State(final HttpServletRequest request) {
		this.auth = new Role[] {
			Role.ANONYMOUS
		};
		this.context = new ContextAttributeMap(request.getServletContext());
		this.session = new SessionAttributeMap(request.getSession(true));
		this.request = new RequestAttributeMap(request);
		this.parameters = new ConcurrentHashMap<>();
		this.fileMap = new ConcurrentHashMap<>();
		
		try {
			final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				final ServletFileUpload upload = new ServletFileUpload(
				        new DiskFileItemFactory(FileSystem.Config.getInteger("MaxUploadfileSize", 100 * 1024 * 1024),
				                FileSystem.Temp.UploadDir));
				final List<FileItem> items = upload.parseRequest(request);
				final Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					final FileItem item = iter.next();
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
			else {
				for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
					final List<String> list = new ArrayList<>();
					parameters.put(entry.getKey(), list);
					for (final String string : entry.getValue()) {
						list.add(string);
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
	
	public Role[] auth() {
		return auth;
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
	
	public void bind(final Object obj) {
		try {
			for (final Field field : obj.getClass().getFields()) {
				Object value = null;
				final String strValue = getParameter(field.getName());
				if (strValue != null) {
					try {
						if (field.getType().isAssignableFrom(String.class)) {
							value = strValue;
						}
						else if (field.getType().isAssignableFrom(int.class)) {
							value = Integer.parseInt(strValue);
						}
						else if (field.getType().isAssignableFrom(double.class)) {
							value = Double.parseDouble(strValue);
						}
						else if (field.getType().isAssignableFrom(boolean.class)) {
							value = Boolean.parseBoolean(strValue);
						}
						else if (field.getType().isAssignableFrom(byte.class)) {
							value = Byte.parseByte(strValue);
						}
						else if (field.getType().isAssignableFrom(long.class)) {
							value = Long.parseLong(strValue);
						}
						else if (field.getType().isAssignableFrom(short.class)) {
							value = Short.parseShort(strValue);
						}
						else if (field.getType().isAssignableFrom(float.class)) {
							value = Float.parseFloat(strValue);
						}
						else if (field.getType().isAssignableFrom(char.class)) {
							value = strValue.charAt(0);
						}
					}
					catch (final Exception e) {
						ThrowableUtil.throwable(e);
					}
					if (value != null) {
						field.setAccessible(true);
						field.set(obj, value);
					}
				}
				
			}
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
		}
	}
	
	public boolean isViewCompiler() {
		return isViewCompiler;
	}
	
	public void setViewCompiler(final boolean isViewCompiler) {
		this.isViewCompiler = isViewCompiler;
	}
}
