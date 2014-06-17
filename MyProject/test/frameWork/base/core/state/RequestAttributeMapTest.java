package frameWork.base.core.state;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;

public class RequestAttributeMapTest {
	
	@Test
	public void test() {
		final RequestAttributeMap s = new RequestAttributeMap(new ServletRequest() {
			HashMap<String, Object> map = new HashMap<>();
			
			@Override
			public AsyncContext startAsync(final ServletRequest arg0, final ServletResponse arg1)
			        throws IllegalStateException {
				return null;
			}
			
			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				return null;
			}
			
			@Override
			public void setCharacterEncoding(final String arg0) throws UnsupportedEncodingException {
			}
			
			@Override
			public void setAttribute(final String arg0, final Object arg1) {
				map.put(arg0, arg1);
			}
			
			@Override
			public void removeAttribute(final String arg0) {
			}
			
			@Override
			public boolean isSecure() {
				return false;
			}
			
			@Override
			public boolean isAsyncSupported() {
				return false;
			}
			
			@Override
			public boolean isAsyncStarted() {
				return false;
			}
			
			@Override
			public ServletContext getServletContext() {
				return null;
			}
			
			@Override
			public int getServerPort() {
				return 0;
			}
			
			@Override
			public String getServerName() {
				return null;
			}
			
			@Override
			public String getScheme() {
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(final String arg0) {
				return null;
			}
			
			@Override
			public int getRemotePort() {
				return 0;
			}
			
			@Override
			public String getRemoteHost() {
				return null;
			}
			
			@Override
			public String getRemoteAddr() {
				return null;
			}
			
			@Override
			public String getRealPath(final String arg0) {
				return null;
			}
			
			@Override
			public BufferedReader getReader() throws IOException {
				return null;
			}
			
			@Override
			public String getProtocol() {
				return null;
			}
			
			@Override
			public String[] getParameterValues(final String arg0) {
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				return null;
			}
			
			@Override
			public String getParameter(final String arg0) {
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				return null;
			}
			
			@Override
			public Locale getLocale() {
				return null;
			}
			
			@Override
			public int getLocalPort() {
				return 0;
			}
			
			@Override
			public String getLocalName() {
				return null;
			}
			
			@Override
			public String getLocalAddr() {
				return null;
			}
			
			@Override
			public ServletInputStream getInputStream() throws IOException {
				return null;
			}
			
			@Override
			public DispatcherType getDispatcherType() {
				return null;
			}
			
			@Override
			public String getContentType() {
				return null;
			}
			
			@Override
			public long getContentLengthLong() {
				return 0;
			}
			
			@Override
			public int getContentLength() {
				return 0;
			}
			
			@Override
			public String getCharacterEncoding() {
				return null;
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
			
			@Override
			public AsyncContext getAsyncContext() {
				return null;
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
