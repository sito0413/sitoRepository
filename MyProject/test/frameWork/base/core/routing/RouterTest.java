package frameWork.base.core.routing;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class RouterTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void test() {
		{
			assertNull(Router.getClassName(null, '.'));
		}
		{
			assertNull(Router.getClassName("", '.'));
			assertNull(Router.getClassName("/test/t.t./aaa", '.'));
			assertNull(Router.getClassName("/test//aaa", '.'));
		}
		{
			final String[] v1 = Router.getClassName("test", '.');
			assertEquals(v1[0] + "." + v1[1], ".test");
		}
		{
			assertNotNull(Router.getClassName("/test/aaa.jpeg", '.'));
			final String[] v1 = Router.getClassName("/test/aaa", '.');
			final String[] v2 = Router.getClassName("/test/aaa.jpeg", '.');
			assertEquals(v1[0] + v1[1], v2[0] + v2[1]);
		}
		{
			final String[] v1 = Router.getClassName("/test/test/aaa", '.');
			assertEquals(v1[0] + "." + v1[1], ".test.test.aaa");
		}
		{
			final String[] v1 = Router.getClassName("/test/aaa", '.');
			assertEquals(v1[0] + "." + v1[1], ".test.aaa");
		}
	}
}
