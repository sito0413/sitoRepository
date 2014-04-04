package frameWork.core.state;

import java.util.Enumeration;

import javax.servlet.ServletContext;


public class ContextAttributeMap implements AttributeMap {
	private final ServletContext context;
	
	public ContextAttributeMap(final ServletContext context) {
		this.context = context;
	}
	
	@Override
	public Object getAttribute(final String name) {
		return context.getAttribute(name);
	}
	
	@Override
	public Enumeration<String> getAttributeNames() {
		return context.getAttributeNames();
	}
	
	@Override
	public void setAttribute(final String name, final Object value) {
		context.setAttribute(name, value);
	}
}
