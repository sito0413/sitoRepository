package frameWork.base.core.viewCompiler.script.syntax;

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

@SuppressWarnings("unused")
public class DoScriptTest {
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
		int i = 0;
		do {
			i++;
		}
		while (i <= 10);
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;do{i++;}while(i<=10);%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		int i = 0;
		do {
			i++;
		}
		while (i < 0);
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;do{i++;}while(i<0);%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		int i = 0;
		do {
			i++;
			if (i > 5) {
				break;
			}
		}
		while (i <= 10);
		final Scope scope = new Scope();
		scope.put("i", i);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 0;do{i++;if(i>5){break;}}while(i<=10);%>")).toTextlets(
			                scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		int i = 0;
		do {
			i++;
			if (i > 5) {
				continue;
			}
		}
		while (i <= 10);
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 0;do{i++;if(i>5){continue;}}while(i<=10);%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void test5() {
		int i = 0;
		test1:
		do {
			test2:
			do {
				test3:
				{
					break test1;
					//i++;
				}
				//i++;
			}
			while (i <= 10);
			//i++;
		}
		while (false);
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:do{test2:do{test3:{break test1;i++;}i++;}while(i<=10);i++;}while(i<=10);i++;%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		int i = 0;
		test1:
		do {
			test2:
			do {
				test3:
				{
					continue test1;
					//					i++;
				}
				//				i++;
			}
			while (i <= 10);
			//			i++;
		}
		while (false);
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:do{test2:do{test3:{continue test1;i++;}i++;}while(i<=10);i++;}while(false);i++;%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test7() {
		int i = 0;
		test:
		do {
			i++;
			if (i > 5) {
				break;
			}
		}
		while (i <= 10);
		final Scope scope = new Scope();
		scope.put("i", i);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i = 0;test:do{i++;if(i>5){break;}}while(i<=10);%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test8() {
		int i = 0;
		test:
		do {
			i++;
			if (i > 5) {
				continue;
			}
		}
		while (i <= 10);
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i = 0;test:do{i++;if(i>5){continue;}}while(i<=10);%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void error1() {
		final Integer i = 0;
		final Scope scope = new Scope();
		scope.put("i", i);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int j = 0;do i++;j++; while(j<=10);%>")).toTextlets(
			        scope, response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
		}
	}
	
	@Test
	public void error2() {
		final Integer i = 0;
		final Scope scope = new Scope();
		scope.put("i", i);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int j = 0;do{i++;j++;};%>")).toTextlets(scope,
			        response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
		}
	}
	
	@Test
	public void error3() {
		final Integer i = 0;
		final Scope scope = new Scope();
		scope.put("i", i);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int j = 0;do{i++;j++;}while(j<=10)%>")).toTextlets(
			        scope, response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
		}
	}
}
