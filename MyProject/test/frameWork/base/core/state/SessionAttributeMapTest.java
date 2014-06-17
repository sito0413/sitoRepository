package frameWork.base.core.state;

import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Test;

@SuppressWarnings("deprecation")
public class SessionAttributeMapTest {
	@Test
	public void test() {
		final SessionAttributeMap s = new SessionAttributeMap(new HttpSession() {
			HashMap<String, Object> map = new HashMap<>();
			
			@Override
			public void setMaxInactiveInterval(final int arg0) {
			}
			
			@Override
			public void setAttribute(final String arg0, final Object arg1) {
				map.put(arg0, arg1);
			}
			
			@Override
			public void removeValue(final String arg0) {
			}
			
			@Override
			public void removeAttribute(final String arg0) {
			}
			
			@Override
			public void putValue(final String arg0, final Object arg1) {
			}
			
			@Override
			public boolean isNew() {
				return false;
			}
			
			@Override
			public void invalidate() {
			}
			
			@Override
			public String[] getValueNames() {
				return null;
			}
			
			@Override
			public Object getValue(final String arg0) {
				return null;
			}
			
			@Override
			public HttpSessionContext getSessionContext() {
				return null;
			}
			
			@Override
			public ServletContext getServletContext() {
				return null;
			}
			
			@Override
			public int getMaxInactiveInterval() {
				return 0;
			}
			
			@Override
			public long getLastAccessedTime() {
				return 0;
			}
			
			@Override
			public String getId() {
				return null;
			}
			
			@Override
			public long getCreationTime() {
				return 0;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return new Enumeration<String>() {
					Iterator<String> iterator = map.keySet().iterator();
					
					@Override
					public boolean hasMoreElements() {
						return iterator.hasNext();
					}
					
					@Override
					public String nextElement() {
						return iterator.next();
					}
				};
			}
			
			@Override
			public Object getAttribute(final String arg0) {
				return map.get(arg0);
			}
		});
		
		s.setAttribute("1", "1");
		s.setAttribute("2", "2");
		s.setAttribute("2", "3");
		assertEquals(s.getAttribute("1"), "1");
		assertEquals(s.getAttribute("2"), "3");
		final Enumeration<String> enumeration = s.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			final String key = enumeration.nextElement();
			if (!key.equals("1") && !key.equals("2")) {
				fail("error " + key);
			}
		}
		
	}
}
