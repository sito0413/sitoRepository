package frameWork.base.core.binder;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BinderTest {
	static class Test1 {
		private String p1;
		private Object p2;
	}
	
	@Test
	public void bind() {
		final String test = "ttttt";
		final Map<String, String> map = new HashMap<>();
		final Test1 test1 = new Test1();
		assertNull(test1.p1);
		new Binder().bind(map, test1);
		assertNull(test1.p1);
		map.put("p1", test);
		map.put("p2", test);
		new Binder().bind(map, test1);
		assertEquals(test1.p1, test);
		assertNull(test1.p2);
	}
	
	@Test
	public void getFieldValue() {
		assertEquals(new Binder().getFieldValue("ttttt", String.class), "ttttt");
		assertEquals(new Binder().getFieldValue("0", int.class), 0);
		assertEquals(new Binder().getFieldValue("1.23", double.class), 1.23);
		assertEquals(new Binder().getFieldValue("true", boolean.class), true);
		assertEquals(new Binder().getFieldValue("false", boolean.class), false);
		assertEquals(new Binder().getFieldValue("4", byte.class), (byte) 4);
		assertEquals(new Binder().getFieldValue("5", long.class), 5l);
		assertEquals(new Binder().getFieldValue("6", short.class), (short) 6);
		assertEquals(new Binder().getFieldValue("7.89", float.class), 7.89f);
		assertEquals(new Binder().getFieldValue("a", char.class), 'a');
		
		assertNull(new Binder().getFieldValue("a0", int.class));
		assertNull(new Binder().getFieldValue("a1.23", double.class));
		assertNull(new Binder().getFieldValue("atrue", boolean.class));
		assertNull(new Binder().getFieldValue("a4", byte.class));
		assertNull(new Binder().getFieldValue("a5", long.class));
		assertNull(new Binder().getFieldValue("a6", short.class));
		assertNull(new Binder().getFieldValue("a7.89", float.class));
		assertNull(new Binder().getFieldValue("aaa", char.class));
		assertNull(new Binder().getFieldValue("a", Object.class));
	}
}
