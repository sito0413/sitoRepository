package frameWork.base.core.viewCompiler.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ScriptletTest {
	@Test
	public void test1() {
		assertEquals(new Scriptlet("test").toString(), "test");
		assertEquals(new Scriptlet("\"\\\r\n\taa").toScript(), "\"\\\r\n\taa");
		assertEquals(new Scriptlet("").toScript(), "");
	}
	
	@Test
	public void test2() {
		final Textlet textlet = new Scriptlet(null);
		assertEquals(textlet.toString(), "");
		final List<Textlet> textlets = new ArrayList<>();
		textlet.add(textlets, "aaaaa");
		assertEquals(textlet.toString(), "");
		assertEquals(textlets.size(), 1);
	}
	
	@Test
	public void test3() {
		final List<Textlet> textlets = new ArrayList<>();
		final Textlet textlet = new Scriptlet("");
		assertEquals(textlet.toString(), "");
		textlet.add(textlets, new Scriptlet(""));
		assertEquals(textlets.size(), 1);
	}
}
