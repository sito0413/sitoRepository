package frameWork.base.core.viewCompiler.script.expression;

import static org.junit.Assert.*;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
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

public class ArrayConstructorScriptTest {
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
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[] s = new String[100];%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 100);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[] s = new String[]{};%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%String[] s = new String[]{new String(), new String()};%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 2);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[] s = {};%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%String[] s = {new String(), new String()};%>")).toTextlets(
			                scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 2);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[][]s = new String[100][50];%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[][].class, "s").get()), 100);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test7() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[][]s = new String[100][];%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[][].class, "s").get()), 100);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test8() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[][] s = new String[][]{};%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test9() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%String[][] s = new String[][]{ new String[]{}, new String[]{}};%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test10() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%String[][] s = new String[][]{ {}, {}};%>")).toTextlets(scope,
			                response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test11() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[][] s = {};%>")).toTextlets(scope, response))
			        .execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 0);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test12() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%String[][] s = {new String[]{}, new String[]{}};%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 2);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test13() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String[][] s = {{}, {}};%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(Array.getLength(scope.get(String[].class, "s").get()), 2);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
}
