package frameWork.base;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class UtilityUrlTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void test() {
		UtilityUrl url = new UtilityUrl("test");
		assertTrue(url.equals(new UtilityUrl("test")));
		assertTrue(url.equals("test"));
		assertTrue(url.toString().equals("test"));
		url = url.addParameter("a", "b");
		assertFalse(url.equals(new UtilityUrl("test")));
		System.out.println(url);
		assertTrue(url.equals("test?a=b"));
		url = url.addParameter("c", "d");
		assertTrue(url.equals("test?a=b&c=d"));
	}
}
