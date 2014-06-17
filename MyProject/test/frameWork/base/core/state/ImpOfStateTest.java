package frameWork.base.core.state;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import frameWork.base.core.authority.Role;

public class ImpOfStateTest {
	@Test
	public void test1() {
		final AttributeMap attributeMap = new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
				
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		};
		final ImpOfState state = new ImpOfState(null, attributeMap, null, null, null);
		assertNull(state.getAuth());
		final Role[] rs = new Role[] {
			Role.ANONYMOUS
		};
		state.setAuth(rs);
		assertTrue(state.getAuth() == rs);
	}
	
	@Test
	public void test2() {
		final AttributeMap attributeMap1 = new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
				
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		};
		final AttributeMap attributeMap2 = new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		};
		final AttributeMap attributeMap3 = new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		};
		final ImpOfState state = new ImpOfState(attributeMap1, attributeMap2, attributeMap3, null, null);
		assertTrue(state.getContext() == attributeMap1);
		assertTrue(state.getSession() == attributeMap2);
		assertTrue(state.getRequest() == attributeMap3);
	}
	
	@Test
	public void test3() {
		final Map<String, List<String>> parameters = new ConcurrentHashMap<>();
		final Map<String, File> fileMap = new ConcurrentHashMap<>();
		final List<String> value = new ArrayList<>();
		final String val1 = "1";
		final File file = new File("");
		value.add(val1);
		value.add("2");
		parameters.put("test", value);
		fileMap.put("test", file);
		final ImpOfState state = new ImpOfState(null, null, null, parameters, fileMap);
		
		assertNull(state.getParameter("a"));
		assertNull(state.getFile("a"));
		assertTrue(state.getParameter("test") == val1);
		assertTrue(state.getFile("test") == file);
	}
	
	@Test
	public void test4() {
		final ImpOfState state = new ImpOfState(null, null, null, null, null);
		assertNull(state.getPage());
		state.setPage("test");
		assertTrue(state.getPage().equals("test"));
	}
	
	@Test
	public void test5() {
		final ImpOfState state = new ImpOfState(null, null, null, null, null);
		state.setViewCompiler(true);
		assertTrue(state.isViewCompiler());
		state.setViewCompiler(false);
		assertFalse(state.isViewCompiler());
		state.setViewCompiler(true);
		assertTrue(state.isViewCompiler());
	}
	
	@Test
	public void test6() {
		final AttributeMap attributeMap = new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
				
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		};
		final ImpOfState state = new ImpOfState(null, attributeMap, null, null, null);
		state.setLogin(null);
		assertTrue(state.getAuth().length == 1);
		assertFalse(state.isLogin());
		
		state.setLogin(state.getAuth());
		assertTrue(state.getAuth().length == 1);
		assertTrue(state.isLogin());
		
		state.setLogin(null);
		assertTrue(state.getAuth().length == 1);
		assertFalse(state.isLogin());
		
		state.setLogin(state.getAuth());
		assertTrue(state.getAuth().length == 1);
		assertTrue(state.isLogin());
		
		state.setLogin(new Role[] {});
		assertTrue(state.getAuth().length == 1);
		assertFalse(state.isLogin());
	}
}
