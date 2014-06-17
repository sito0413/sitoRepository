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

public class ConstructorScriptTest {
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
	public void test() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String s = new String(\"\");%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(String.class, "s").get(), "");
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void error() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%String s = new String(123);%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(String.class, "s").get(), "");
			fail();
		}
		catch (final ScriptException e) {
			//NOP
		}
	}
	
}
