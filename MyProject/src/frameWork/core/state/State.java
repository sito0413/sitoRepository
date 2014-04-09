package frameWork.core.state;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import frameWork.ThrowableUtil;
import frameWork.core.authority.Role;
import frameWork.databaseConnector.DatabaseConnector;

public class State {
	
	private final Role[] auth;
	private final AttributeMap context;
	private final AttributeMap session;
	private final AttributeMap request;
	private DatabaseConnector connector;
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
		for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			final List<String> list = new ArrayList<>();
			parameters.put(entry.getKey(), list);
			for (final String string : entry.getValue()) {
				list.add(string);
			}
		}
		try {
			//			for (final javax.servlet.http.Part part : request.getParts()) {
			// FIXME
			//				final part.set
			//				for (final String cd : part.getHeader("Content-Disposition").split(";")) {
			//					if (cd.trim().startsWith("filename")) {
			//						part.write(cd.substring(cd.indexOf('=') + 1).trim().replace("\"", ""));
			//						break;
			//					}
			//				}
			//	part.
			//			}
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
		}
	}
	
	public void setConnector(final DatabaseConnector connector) {
		this.connector = connector;
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
	
	public DatabaseConnector getConnector() {
		return connector;
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
						if (field.getType().equals(String.class)) {
							value = strValue;
						}
						else if (field.getType().equals(int.class)) {
							value = Integer.parseInt(strValue);
						}
						else if (field.getType().equals(double.class)) {
							value = Double.parseDouble(strValue);
						}
						else if (field.getType().equals(Boolean.class)) {
							value = Boolean.parseBoolean(strValue);
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
