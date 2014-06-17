package frameWork.base.core.viewCompiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScopeTest {
	
	@Test
	public void test1() {
		final Scope scope = new Scope();
		assertEquals(scope.depth(), 0);
		scope.startScope();
		assertEquals(scope.depth(), 1);
		scope.startScope();
		assertEquals(scope.depth(), 2);
		scope.endScope();
		assertEquals(scope.depth(), 1);
		scope.endScope();
		assertEquals(scope.depth(), 0);
		scope.endScope();
		assertEquals(scope.depth(), 0);
		scope.startScope();
		assertEquals(scope.depth(), 1);
	}
	
	@Test
	public void test2() {
		final Scope scope = new Scope();
		try {
			assertTrue(scope.getImport("String").getCanonicalName().equals(String.class.getCanonicalName()));
			assertTrue(scope.getImport(StringBuffer.class.getCanonicalName()).getCanonicalName()
			        .equals(StringBuffer.class.getCanonicalName()));
			assertTrue(scope.getImport("var").getCanonicalName().equals(Object.class.getCanonicalName()));
			
			assertTrue(scope.getImport("String[]").getCanonicalName().equals(String[].class.getCanonicalName()));
			assertTrue(scope.getImport(StringBuffer[].class.getCanonicalName()).getCanonicalName()
			        .equals(StringBuffer[].class.getCanonicalName()));
			assertTrue(scope.getImport("java.lang.StringBuilder[]").getCanonicalName()
			        .equals(StringBuilder[].class.getCanonicalName()));
			
			assertTrue(scope.getImport("String[][]").getCanonicalName().equals(String[][].class.getCanonicalName()));
			assertTrue(scope.getImport(StringBuffer[][].class.getCanonicalName()).getCanonicalName()
			        .equals(StringBuffer[][].class.getCanonicalName()));
			assertTrue(scope.getImport("java.lang.StringBuilder[][]").getCanonicalName()
			        .equals(StringBuilder[][].class.getCanonicalName()));
			
			try {
				scope.getImport("TEST");
				fail("illegal");
			}
			catch (final ScriptException e) {
			}
			try {
				scope.getImport("T.E.S.T");
				fail("illegal");
			}
			catch (final ScriptException e) {
			}
			try {
				scope.getImport("TEST[]");
				fail("illegal");
			}
			catch (final ScriptException e) {
			}
			try {
				scope.getImport("T.E.S.T[]");
				fail("illegal");
			}
			catch (final ScriptException e) {
			}
			
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		final Scope scope = new Scope();
		try {
			try {
				scope.getImport("ScopeTest");
				fail("illegal");
			}
			catch (final ScriptException e1) {
				scope.getImport(ScopeTest.class.getCanonicalName());
				try {
					scope.getImport("ScopeTest");
					fail("illegal");
				}
				catch (final ScriptException e2) {
					scope.putImport("ScopeTest", ScopeTest.class);
					scope.getImport("ScopeTest");
					scope.getImport("ScopeTest[]");
					scope.removeImport("ScopeTest");
					try {
						scope.getImport("ScopeTest");
						fail("illegal");
					}
					catch (final ScriptException e3) {
						try {
							scope.getImport("ScopeTest[]");
							fail("illegal");
						}
						catch (final ScriptException e4) {
						}
					}
				}
			}
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		final Scope scope = new Scope();
		try {
			try {
				scope.get(null, "ScopeTest");
				fail("illegal");
			}
			catch (final ScriptException e) {
				scope.putImport("ScopeTest", ScopeTest.class);
				assertNotNull(scope.get(null, "ScopeTest"));
				assertNotNull(scope.get(ScopeTest.class, "test"));
				assertNull(scope.get(ScopeTest.class, "test").get());
				assertFalse(scope.objectMap.containsKey("test"));
				{
					scope.startScope();
					scope.put("test", new ScopeTest());
					assertNotNull(scope.get(ScopeTest.class, "test"));
					assertNotNull(scope.get(ScopeTest.class, "test").get());
					assertTrue(scope.objectMap.containsKey("test"));
					{
						scope.startScope();
						assertNotNull(scope.get(ScopeTest.class, "test"));
						assertNotNull(scope.get(ScopeTest.class, "test").get());
						assertFalse(scope.objectMap.containsKey("test"));
						{
							scope.startScope();
							assertNotNull(scope.get(ScopeTest.class, "test"));
							assertNotNull(scope.get(ScopeTest.class, "test").get());
							
							assertFalse(scope.objectMap.containsKey("test"));
							scope.put("test", new ScopeTest());
							assertFalse(scope.objectMap.containsKey("test"));
							
							assertNotNull(scope.get(ScopeTest.class, "test2"));
							assertNull(scope.get(ScopeTest.class, "test2").get());
							scope.endScope();
						}
						assertFalse(scope.objectMap.containsKey("test"));
						assertNotNull(scope.get(ScopeTest.class, "test"));
						assertNotNull(scope.get(ScopeTest.class, "test").get());
						scope.endScope();
					}
					assertTrue(scope.objectMap.containsKey("test"));
					assertNotNull(scope.get(ScopeTest.class, "test"));
					assertNotNull(scope.get(ScopeTest.class, "test").get());
					scope.endScope();
				}
				assertFalse(scope.objectMap.containsKey("test"));
				
				assertNotNull(scope.get(ScopeTest.class, "test"));
				assertNull(scope.get(ScopeTest.class, "test").get());
			}
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		final Scope scope = new Scope();
		try {
			scope.put("test", "test1");
			assertEquals(scope.get(String.class, "test").get(), "test1");
			scope.put("test", "test2");
			assertEquals(scope.get(String.class, "test").get(), "test2");
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
}
