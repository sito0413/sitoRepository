package frameWork.base.core.viewCompiler.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.junit.Test;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;

public class ParserBufferTest {
	String contentType;
	
	@Test
	public void test1() {
		final Response response = new Response(new ServletResponse() {
			@Override
			public void setLocale(final Locale arg0) {
			}
			
			@Override
			public void setContentType(final String arg0) {
				contentType = arg0;
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
		final Scope scope = new Scope();
		final HashSet<String> set = new HashSet<>();
		
		assertFalse(new ParserBuffer(CharBuffer.wrap("")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@page")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  test")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  t")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"t")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"test")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"test\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"t\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"t%")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"test%")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  =\"%")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"t")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"test")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"test\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"t\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"\"")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"t%")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"test%")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   page  r=\"%")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@page%>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page     %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test test  %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test=   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test =   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test =test   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test = test   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test = \"test   %>")).parsePage(scope, response, set));
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@   page  test = \"test\"   %>"))
		        .parsePage(scope, response, set));
		contentType = null;
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@ page contentType=\"text/html\"  %>")).parsePage(scope,
		        response, set));
		assertEquals(contentType, "text/html;charset=" + FileSystem.Config.VIEW_CHAREET);
		assertTrue(new ParserBuffer(CharBuffer.wrap("<%@ page contentType=\"text/html;charset=sjis\"  %>")).parsePage(
		        scope, response, set));
		assertEquals(contentType, "text/html;charset=sjis");
		
		try {
			try {
				assertNull(scope.getImport("SystemColor"));
				fail();
			}
			catch (final ScriptException e1) {
				assertTrue(new ParserBuffer(CharBuffer.wrap("<%@page import=\"java.awt.SystemColor\"%>")).parsePage(
				        scope, response, set));
				assertNotNull(scope.getImport("SystemColor"));
				assertTrue(new ParserBuffer(CharBuffer.wrap("<%@page import=\"javaSystemColor\"%>")).parsePage(scope,
				        response, set));
				assertTrue(new ParserBuffer(
				        CharBuffer.wrap("<%@page import=\"frameWork.base.core.viewCompiler.parser.SystemColor\"%>"))
				        .parsePage(scope, response, set));
				try {
					assertNull(scope.getImport("SystemColor"));
					fail();
				}
				catch (final ScriptException e4) {
				}
			}
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
		
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@   test     %>")).parsePage(scope, response, set));
		assertFalse(new ParserBuffer(CharBuffer.wrap("<%@test     %>")).parsePage(scope, response, set));
	}
	
	@Test
	public void test2() {
		final List<Textlet> list = new ArrayList<>();
		assertNull(new ParserBuffer(CharBuffer.wrap("")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%%")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%=")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%=%")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%\"te%>")).parseScriptlet(list));
		assertNull(new ParserBuffer(CharBuffer.wrap("<%=\"te%>")).parseScriptlet(list));
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%%>")).parseScriptlet(list).text, "");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=%>")).parseScriptlet(list).text, "");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"test\"%>")).parseScriptlet(list).text, "\"test\"");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"test\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"test\");");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"test\"%4%>")).parseScriptlet(list).text, "\"test\"%4");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"test\"%4%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"test\"%4);");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"test\"%>")).parseScriptlet(list).text, "\"test\"");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"test\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"test\");");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"test%\"%>")).parseScriptlet(list).text, "\"test%\"");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"test%\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"test%\");");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"te%>st%\"%>")).parseScriptlet(list).text, "\"te%>st%\"");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"te%>st%\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"te%>st%\");");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\"te%>st%\\\"\"%>")).parseScriptlet(list).text,
		        "\"te%>st%\\\"\"");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\"te%>st%\\\"\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\"te%>st%\\\"\");");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%\\\"te%>st%\"%>")).parseScriptlet(list).text, "\\\"te");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<%=\\\"te%>st%\"%>")).parseScriptlet(list).text,
		        FileSystem.Config.VIEW_OUTPUT_METHOD + "(\\\"te);");
	}
	
	@Test
	public void test3() {
		assertEquals(new ParserBuffer(CharBuffer.wrap("")).parseText(), "");
		assertEquals(new ParserBuffer(CharBuffer.wrap("<test")).parseText(), "<test");
		assertEquals(new ParserBuffer(CharBuffer.wrap("test")).parseText(), "test");
		assertEquals(new ParserBuffer(CharBuffer.wrap("te<st")).parseText(), "te");
	}
	
	@Test
	public void test4() {
		final Response response = new Response(new ServletResponse() {
			@Override
			public void setLocale(final Locale arg0) {
			}
			
			@Override
			public void setContentType(final String arg0) {
				contentType = arg0;
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
		final Scope scope = new Scope();
		new ParserBuffer(CharBuffer.wrap("")).toTextlets(scope, response);
		new ParserBuffer(CharBuffer.wrap("<%@page%><%%>test")).toTextlets(scope, response);
	}
	
}
