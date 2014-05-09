package frameWork.base.core.targetFilter;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.base.core.targetFilter.TargetFilter;

public class TargetFilterTest {
	
	@Test
	public void isNull() {
		assertNull(TargetFilter.getClassName(null));
	}
	
	@Test
	public void isIllegal() {
		assertNull(TargetFilter.getClassName(""));
		assertNull(TargetFilter.getClassName("/test/t.t./aaa"));
		assertNull(TargetFilter.getClassName("/test//aaa"));
	}
	
	@Test
	public void isEmpty() {
		assertEquals(TargetFilter.getClassName("test"), ".test");
	}
	
	@Test
	public void hasExtend() {
		assertNotNull(TargetFilter.getClassName("/test/aaa.jpeg"));
		assertEquals(TargetFilter.getClassName("/test/aaa"), TargetFilter.getClassName("/test/aaa.jpeg"));
	}
	
	@Test
	public void hasPackage() {
		assertEquals(TargetFilter.getClassName("/test/test/aaa"), ".test.test.aaa");
	}
	
	@Test
	public void isNormal() {
		assertEquals(TargetFilter.getClassName("/test/aaa"), ".test.aaa");
	}
}
