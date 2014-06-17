package frameWork.base.core.viewCompiler.script.expression;

import static org.junit.Assert.*;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.junit.Test;

import frameWork.base.core.state.Response;
import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.parser.ParserBuffer;

public class DeclarationScriptTest {
	final Response response = new Response(new ServletResponse() {
		@Override
		public void setLocale(final Locale arg0) {
		}
		
		@Override
		public void setContentType(final String arg0) {
		}
		
		@Override
		public void setContentLengthLong(final long arg0) {
		}
		
		@Override
		public void setContentLength(final int arg0) {
		}
		
		@Override
		public void setCharacterEncoding(final String arg0) {
		}
		
		@Override
		public void setBufferSize(final int arg0) {
		}
		
		@Override
		public void resetBuffer() {
		}
		
		@Override
		public void reset() {
		}
		
		@Override
		public boolean isCommitted() {
			return false;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			return null;
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return null;
		}
		
		@Override
		public Locale getLocale() {
			return null;
		}
		
		@Override
		public String getContentType() {
			return null;
		}
		
		@Override
		public String getCharacterEncoding() {
			return null;
		}
		
		@Override
		public int getBufferSize() {
			return 0;
		}
		
		@Override
		public void flushBuffer() throws IOException {
			
		}
	}, null);
	
	@Test
	public void test1() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%long i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(long.class, "i").get(), 0l);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(int.class, "i").get(), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%short i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(short.class, "i").get(), (short) 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%byte i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(byte.class, "i").get(), (byte) 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%char i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(char.class, "i").get(), (char) 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%double i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(double.class, "i").get(), 0d);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test7() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%float i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(float.class, "i").get(), 0f);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test8() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%boolean i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(scope.get(boolean.class, "i").get(), false);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test9() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%long[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(long[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test10() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(int[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test11() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%short[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(short[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test12() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%byte[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(byte[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test13() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%char[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(char[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test14() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%double[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(double[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test15() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%float[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(float[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test16() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%boolean[] i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(boolean[].class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test17() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String i;%>")).toTextlets(scope, response))
			        .execute(scope);
			assertNull(scope.get(String.class, "i").get());
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
}
