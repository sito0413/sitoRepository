package frameWork.core.wrap;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import frameWork.core.authority.Authority;
import frameWork.databaseConnector.DatabaseConnector;
import frameWork.utility.ThrowableUtil;
import frameWork.utility.state.State;
import frameWork.utility.state.attributeMap.AttributeMap;

public class WrapState implements State {
	
	private final String[] auth;
	private final AttributeMap context;
	private final AttributeMap session;
	private final AttributeMap request;
	private DatabaseConnector connector;
	private final Map<String, List<String>> parameters;
	private final Map<String, File> fileMap;
	private String page;
	
	WrapState(final HttpServletRequest request) {
		this.auth = new String[] {
			Authority.ANONYMOUS
		};
		this.context = new WrapContextAttributeMap(request.getServletContext());
		this.session = new WrapSessionAttributeMap(request.getSession(true));
		this.request = new WrapRequestAttributeMap(request);
		this.parameters = new ConcurrentHashMap<>();
		this.fileMap = new ConcurrentHashMap<>();
		for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			final List<String> list = new ArrayList<>();
			parameters.put(entry.getKey(), list);
			for (final String string : entry.getValue()) {
				list.add(string);
			}
		}
		//FIXME
		//		try {
		//			for (final Part part : request.getParts()) {
		//			}
		//		}
		//		catch (IOException | ServletException e) {
		//			e.printStackTrace();
		//		}
	}
	
	@Override
	public void setConnector(final DatabaseConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public String[] auth() {
		return auth;
	}
	
	@Override
	public AttributeMap getContext() {
		return context;
	}
	
	@Override
	public AttributeMap getSession() {
		return session;
	}
	
	@Override
	public AttributeMap getRequest() {
		return request;
	}
	
	@Override
	public final String getParameter(final String name) {
		if (parameters.get(name) == null) {
			return null;
		}
		return parameters.get(name).get(0);
	}
	
	@Override
	public final File getFile(final String name) {
		return fileMap.get(name);
	}
	
	@Override
	public DatabaseConnector getConnector() {
		return connector;
	}
	
	@Override
	public String getPage() {
		return page;
	}
	
	@Override
	public void setPage(final String page) {
		this.page = page;
	}
	
	@Override
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
	
	@Override
	public boolean isViewCompiler() {
		return true;
	}
	
	@Override
	public void setViewCompiler(final boolean isViewCompiler) {
		
	}
}
