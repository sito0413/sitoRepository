package frameWork.core.wrap;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import frameWork.utility.state.attributeMap.AttributeMap;

public class WrapSessionAttributeMap implements AttributeMap {
	
	private final HttpSession session;
	
	public WrapSessionAttributeMap(final HttpSession session) {
		this.session = session;
	}
	
	@Override
	public Object getAttribute(final String name) {
		return session.getAttribute(name);
	}
	
	@Override
	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}
	
	@Override
	public void setAttribute(final String name, final Object value) {
		session.setAttribute(name, value);
	}
	
}
