package frameWork.core.state;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;


public class SessionAttributeMap implements AttributeMap {
	
	private final HttpSession session;
	
	public SessionAttributeMap(final HttpSession session) {
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
