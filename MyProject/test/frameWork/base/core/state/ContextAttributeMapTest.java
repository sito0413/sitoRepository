package frameWork.base.core.state;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.junit.Test;

public class ContextAttributeMapTest {
	
	@Test
	public void test() {
		final ContextAttributeMap s = new ContextAttributeMap(new ServletContext() {
			HashMap<String, Object> map = new HashMap<>();
			
			@Override
			public void setSessionTrackingModes(final Set<SessionTrackingMode> arg0) {
			}
			
			@Override
			public boolean setInitParameter(final String arg0, final String arg1) {
				return false;
			}
			
			@Override
			public void setAttribute(final String arg0, final Object arg1) {
				map.put(arg0, arg1);
			}
			
			@Override
			public void removeAttribute(final String arg0) {
			}
			
			@Override
			public void log(final String arg0, final Throwable arg1) {
			}
			
			@Override
			public void log(final Exception arg0, final String arg1) {
			}
			
			@Override
			public void log(final String arg0) {
			}
			
			@Override
			public String getVirtualServerName() {
				return null;
			}
			
			@Override
			public SessionCookieConfig getSessionCookieConfig() {
				return null;
			}
			
			@Override
			public Enumeration<Servlet> getServlets() {
				return null;
			}
			
			@Override
			public Map<String, ? extends ServletRegistration> getServletRegistrations() {
				return null;
			}
			
			@Override
			public ServletRegistration getServletRegistration(final String arg0) {
				return null;
			}
			
			@Override
			public Enumeration<String> getServletNames() {
				return null;
			}
			
			@Override
			public String getServletContextName() {
				return null;
			}
			
			@Override
			public Servlet getServlet(final String arg0) throws ServletException {
				return null;
			}
			
			@Override
			public String getServerInfo() {
				return null;
			}
			
			@Override
			public Set<String> getResourcePaths(final String arg0) {
				return null;
			}
			
			@Override
			public InputStream getResourceAsStream(final String arg0) {
				return null;
			}
			
			@Override
			public URL getResource(final String arg0) throws MalformedURLException {
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(final String arg0) {
				return null;
			}
			
			@Override
			public String getRealPath(final String arg0) {
				return null;
			}
			
			@Override
			public RequestDispatcher getNamedDispatcher(final String arg0) {
				return null;
			}
			
			@Override
			public int getMinorVersion() {
				return 0;
			}
			
			@Override
			public String getMimeType(final String arg0) {
				return null;
			}
			
			@Override
			public int getMajorVersion() {
				return 0;
			}
			
			@Override
			public JspConfigDescriptor getJspConfigDescriptor() {
				return null;
			}
			
			@Override
			public Enumeration<String> getInitParameterNames() {
				return null;
			}
			
			@Override
			public String getInitParameter(final String arg0) {
				return null;
			}
			
			@Override
			public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
				return null;
			}
			
			@Override
			public FilterRegistration getFilterRegistration(final String arg0) {
				return null;
			}
			
			@Override
			public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
				return null;
			}
			
			@Override
			public int getEffectiveMinorVersion() {
				return 0;
			}
			
			@Override
			public int getEffectiveMajorVersion() {
				return 0;
			}
			
			@Override
			public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
				return null;
			}
			
			@Override
			public String getContextPath() {
				return null;
			}
			
			@Override
			public ServletContext getContext(final String arg0) {
				return null;
			}
			
			@Override
			public ClassLoader getClassLoader() {
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
			public void declareRoles(final String... arg0) {
			}
			
			@Override
			public <T extends Servlet> T createServlet(final Class<T> arg0) throws ServletException {
				return null;
			}
			
			@Override
			public <T extends EventListener> T createListener(final Class<T> arg0) throws ServletException {
				return null;
			}
			
			@Override
			public <T extends Filter> T createFilter(final Class<T> arg0) throws ServletException {
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(final String arg0,
			        final Class<? extends Servlet> arg1) {
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(final String arg0, final Servlet arg1) {
				return null;
			}
			
			@Override
			public javax.servlet.ServletRegistration.Dynamic addServlet(final String arg0, final String arg1) {
				return null;
			}
			
			@Override
			public void addListener(final Class<? extends EventListener> arg0) {
			}
			
			@Override
			public <T extends EventListener> void addListener(final T arg0) {
			}
			
			@Override
			public void addListener(final String arg0) {
				
			}
			
			@Override
			public Dynamic addFilter(final String arg0, final Class<? extends Filter> arg1) {
				return null;
			}
			
			@Override
			public Dynamic addFilter(final String arg0, final Filter arg1) {
				return null;
			}
			
			@Override
			public Dynamic addFilter(final String arg0, final String arg1) {
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
