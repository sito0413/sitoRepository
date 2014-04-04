package frameWork.core.targetFilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class TargetFilterTest {
	
	@Test
	public void isNull() {
		assertNull(TargetFilter.parse(null));
	}
	
	@Test
	public void isIllegal() {
		assertNull(TargetFilter.parse("/test/t.t./aaa"));
	}
	
	@Test
	public void isEmpty() {
		assertEquals(TargetFilter.parse(""), new TargetFilter("Index", "index"));
		assertEquals(TargetFilter.parse("test"), new TargetFilter("Test", "index"));
		assertEquals(TargetFilter.parse("test").view, new TargetFilter("Test", "index").view);
	}
	
	@Test
	public void hasExtend() {
		assertNotNull(TargetFilter.parse("/test/aaa.jpeg"));
		assertEquals(TargetFilter.parse("/test/aaa"), TargetFilter.parse("/test/aaa.jpeg"));
		assertEquals(TargetFilter.parse("/test/aaa").view, TargetFilter.parse("/test/aaa.jpeg").view);
	}
	
	@Test
	public void hasPackage() {
		assertEquals(TargetFilter.parse("/test/test/aaa"), new TargetFilter("test.Test", "aaa"));
		assertEquals(TargetFilter.parse("/test/test/aaa"), TargetFilter.parse("test/test/aaa"));
		assertEquals(TargetFilter.parse("/test/test/aaa").view, TargetFilter.parse("test/test/aaa").view);
	}
	
	@Test
	public void isNormal() {
		assertEquals(TargetFilter.parse("/test/aaa"), new TargetFilter("Test", "aaa"));
		assertEquals(TargetFilter.parse("/test/aaa"), TargetFilter.parse("test/aaa"));
		assertEquals(TargetFilter.parse("/test/aaa").view, TargetFilter.parse("test/aaa").view);
	}
	
	@Test
	public void view() {
		assertEquals(new TargetFilter("Test", "index").view, "/Test/index.jsp");
	}
}
