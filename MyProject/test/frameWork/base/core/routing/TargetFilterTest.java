package frameWork.base.core.routing;

import static org.junit.Assert.*;

import org.junit.Test;

public class TargetFilterTest {

	@Test
	public void isNull() {
		assertNull(TargetFilter.getClassName(null, '.'));
	}

	@Test
	public void isIllegal() {
		assertNull(TargetFilter.getClassName("", '.'));
		assertNull(TargetFilter.getClassName("/test/t.t./aaa", '.'));
		assertNull(TargetFilter.getClassName("/test//aaa", '.'));
	}

	@Test
	public void isEmpty() {
		final String[] v1 = TargetFilter.getClassName("test", '.');
			assertEquals(v1[0] + "." + v1[1],
		        ".test");
	}

	@Test
	public void hasExtend() {
		assertNotNull(TargetFilter.getClassName("/test/aaa.jpeg", '.'));
		final String[] v1 = TargetFilter.getClassName("/test/aaa", '.');
		final String[] v2 = TargetFilter.getClassName("/test/aaa.jpeg", '.');
		assertEquals(v1[0] + v1[1], v2[0] + v2[1]);
	}

	@Test
	public void hasPackage() {
		final String[] v1 = TargetFilter.getClassName("/test/test/aaa", '.');
		assertEquals(v1[0] + "." + v1[1], ".test.test.aaa");
	}

	@Test
	public void isNormal() {
		final String[] v1 = TargetFilter.getClassName("/test/aaa", '.');
		assertEquals(v1[0] + "." + v1[1], ".test.aaa");
	}
}
