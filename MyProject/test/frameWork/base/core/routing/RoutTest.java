package frameWork.base.core.routing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import frameWork.base.core.authority.Role;
import frameWork.base.core.state.AttributeMap;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;
import frameWork.base.core.viewCompiler.ScriptException;

public class RoutTest {
	static String TEXT = "testgogo";
	
	static class RoutTestRout1 extends Rout {
		boolean flag = false;
		
		public RoutTestRout1() throws NoSuchMethodException, SecurityException {
			super(RoutTestRout1.class, RoutTestRout1.class.getDeclaredMethod("test", State.class), TEXT);
		}
		
		@SuppressWarnings("unused")
		private void test(final State state) {
			assertTrue(state.isViewCompiler());
			assertTrue(TEXT.equals(state.getPage()));
		}
		
		@Override
		void invokeResource(final State state, final Response response) throws NoSuchMethodException,
		        SecurityException, Exception {
			fail("invokeResource");
		}
		
		@Override
		void viewCompile(final State state, final Response response) throws IOException, ScriptException {
			flag = true;
		}
	}
	
	static class RoutTestRout2 extends Rout {
		boolean flag = false;
		
		public RoutTestRout2() throws NoSuchMethodException, SecurityException {
			super(RoutTestRout2.class, RoutTestRout2.class.getDeclaredMethod("test", State.class), TEXT);
		}
		
		void test(final State state) {
			state.setViewCompiler(false);
		}
		
		@Override
		void invokeResource(final State state, final Response response) throws NoSuchMethodException,
		        SecurityException, Exception {
			flag = true;
		}
		
		@Override
		void viewCompile(final State state, final Response response) throws IOException, ScriptException {
			fail("invokeResource");
		}
	}
	
	@Test
	public void test1() {
		try {
			final State state = new State() {
				boolean isViewCompiler;
				String page;
				
				@Override
				public boolean isViewCompiler() {
					return isViewCompiler;
				}
				
				@Override
				public void setViewCompiler(final boolean isViewCompiler) {
					this.isViewCompiler = isViewCompiler;
				}
				
				@Override
				public String getPage() {
					return page;
				}
				
				@Override
				public void setPage(final String page) {
					this.page = page;
				}
				
				@Override
				public void setAuth(final Role[] auth) {
				}
				
				@Override
				public boolean isLogin() {
					return false;
				}
				
				@Override
				public AttributeMap getSession() {
					return null;
				}
				
				@Override
				public AttributeMap getRequest() {
					return null;
				}
				
				@Override
				public String getParameter(final String name) {
					return null;
				}
				
				@Override
				public File getFile(final String name) {
					return null;
				}
				
				@Override
				public AttributeMap getContext() {
					return null;
				}
				
				@Override
				public Role[] getAuth() {
					return null;
				}
			};
			state.setViewCompiler(false);
			state.setPage("aaaaaaaaaaaaaaaaaaaaaa" + TEXT);
			final RoutTestRout1 rout1 = new RoutTestRout1();
			rout1.invoke(state, null);
			assertTrue(rout1.flag);
			
			state.setViewCompiler(true);
			state.setPage("aaaaaaaaaaaaaaaaaaaaaa" + TEXT);
			final RoutTestRout2 rout2 = new RoutTestRout2();
			rout2.invoke(state, null);
			assertTrue(rout2.flag);
		}
		catch (final Exception e) {
			fail(e.getMessage());
		}
	}
}
