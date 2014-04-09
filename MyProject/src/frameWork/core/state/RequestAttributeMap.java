package frameWork.core.state;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestAttributeMap implements AttributeMap {
	private final HttpServletRequest request;
	
	public RequestAttributeMap(final HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public Object getAttribute(final String name) {
		return request.getAttribute(name);
	}
	
	@Override
	public Enumeration<String> getAttributeNames() {
		return request.getAttributeNames();
	}
	
	@Override
	public void setAttribute(final String name, final Object value) {
		request.setAttribute(name, value);
	}
	
}
