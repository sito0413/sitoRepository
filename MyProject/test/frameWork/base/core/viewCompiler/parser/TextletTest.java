package frameWork.base.core.viewCompiler.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import frameWork.base.core.fileSystem.FileSystem;

public class TextletTest {
	@Test
	public void test1() {
		assertEquals(new Textlet("test").toString(), "test");
		assertEquals(new Textlet("\"\\\r\n\taa").toScript(), FileSystem.Config.VIEW_OUTPUT_METHOD + "(\""
		        + "\\\"\\\\\\r\\n\\taa" + "\");");
		assertNull(new Textlet("").toScript());
	}
	
	@Test
	public void test2() {
		final Textlet textlet = new Textlet(null);
		assertEquals(textlet.toString(), "");
		final List<Textlet> textlets = new ArrayList<>();
		textlet.add(textlets, "aaaaa");
		assertEquals(textlet.toString(), "aaaaa");
		assertEquals(textlets.size(), 0);
	}
	
	@Test
	public void test3() {
		final List<Textlet> textlets = new ArrayList<>();
		final Textlet textlet = new Textlet("");
		assertEquals(textlet.toString(), "");
		textlet.add(textlets, new Scriptlet(""));
		assertEquals(textlets.size(), 1);
	}
}
