package frameWork.core.wrap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import frameWork.core.Authority;
import frameWork.databaseConnector.DatabaseConnector;
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
	
}
