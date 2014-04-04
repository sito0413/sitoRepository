package frameWork.core.state;

import java.util.Enumeration;

public interface AttributeMap {
	public Object getAttribute(final String name);
	
	public Enumeration<String> getAttributeNames();
	
	public void setAttribute(final String name, final Object value);
}
