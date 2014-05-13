package frameWork.base.core.authority;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.base.core.authority.Authority;
import frameWork.base.core.authority.AuthorityChecker;

public class AuthorityCheckerTest {
	static class TestClass1 {
		public void test() {
		}
	}
	
	@Test
	public void checkTest1() {
		try {
			assertFalse(AuthorityChecker.check(TestClass1.class, TestClass1.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority
	static class TestClass2 {
		public void test() {
		}
	}
	
	@Test
	public void checkTest2() {
		try {
			assertFalse(AuthorityChecker.check(TestClass2.class, TestClass2.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	static class TestClass3 {
		@Authority
		public void test() {
		}
	}
	
	@Test
	public void checkTest3() {
		try {
			assertFalse(AuthorityChecker.check(TestClass3.class, TestClass3.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority
	static class TestClass4 {
		@Authority
		public void test() {
		}
	}
	
	@Test
	public void checkTest4() {
		try {
			assertTrue(AuthorityChecker.check(TestClass4.class, TestClass4.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority
	static class TestClass5 {
		@Authority(allowRole = Role.ANONYMOUS)
		public void test() {
		}
	}
	
	@Test
	public void checkTest5() {
		try {
			assertFalse(AuthorityChecker.check(TestClass5.class, TestClass5.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority(allowRole = Role.ANONYMOUS)
	static class TestClass6 {
		@Authority
		public void test() {
		}
	}
	
	@Test
	public void checkTest6() {
		try {
			assertFalse(AuthorityChecker.check(TestClass6.class, TestClass6.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority(allowRole = Role.ANONYMOUS)
	static class TestClass7 {
		@Authority(allowRole = Role.ANONYMOUS)
		public void test() {
		}
	}
	
	@Test
	public void checkTest7() {
		try {
			assertFalse(AuthorityChecker.check(TestClass7.class, TestClass7.class.getMethod("test"), Role.USER));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
	
	@Authority(allowRole = Role.ANONYMOUS)
	static class TestClass8 {
		@Authority(allowRole = Role.ANONYMOUS)
		public void test() {
		}
	}
	
	@Test
	public void checkTest() {
		try {
			assertTrue(AuthorityChecker.check(TestClass8.class, TestClass8.class.getMethod("test"), Role.ANONYMOUS));
		}
		catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}
	}
}
