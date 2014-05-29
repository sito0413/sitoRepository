package frameWork.base.core.authority;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.architect.Literal;

public class AuthorityTest {
	@Authority
	static class Test1 {
		
	}
	
	@Test
	public void test() {
		assertEquals(Literal.DefaultRole, Test1.class.getAnnotation(Authority.class).allowRole()[0]);
	}
}
