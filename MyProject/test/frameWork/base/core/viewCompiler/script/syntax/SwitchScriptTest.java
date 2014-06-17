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

public class SwitchScriptTest {
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
		switch ( 1 ) {
			case 1 :
				i = 1;
				break;
			
			case 2 :
				i = 2;
				break;
			
			default :
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 1 ) {case 1 :i = 1;break;case 2 :i = 2;break;default :break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		int i = 0;
		switch ( 2 ) {
			case 1 :
				i = 1;
				break;
			case 2 :
				i = 2;
				break;
			default :
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 2 ) {case 1 :i = 1;break;case 2 :i = 2;break;default :break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		int i = 0;
		switch ( 3 ) {
			case 1 :
				i = 1;
				break;
			
			case 2 :
				i = 2;
				break;
			
			default :
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 3 ) {case 1 :i = 1;break;case 2 :i = 2;break;default :break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		final int i = 0;
		switch ( 1 ) {
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;switch ( 1 ) {}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		int i = 0;
		switch ( 1 ) {
			case 1 :
				i = 1;
			case 2 :
				i = 2;
				break;
			default :
				i = 3;
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 1 ) {case 1 :i = 1;case 2 :i = 2;break;default :i = 3;break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		int i = 0;
		switch ( 1 ) {
			case 1 :
				i = 1;
			case 2 :
				i = 2;
			default :
				i = 3;
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 0;switch ( 1 ) {case 1 :i = 1;case 2 :i = 2;default :i = 3;break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test7() {
		int i = 0;
		switch ( 2 ) {
			case 1 :
				i = 1;
			case 2 :
				i = 2;
			default :
				i = 3;
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 0;switch ( 2 ) {case 1 :i = 1;case 2 :i = 2;default :i = 3;break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test8() {
		int i = 0;
		switch ( 2 ) {
			case 1 :
				i = 1;
			case 2 :
				i = 2;
			default :
				i = 3;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 0;switch ( 1 ) {case 1 :i = 1;case 2 :i = 2;default :i = 3;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test9() {
		final int i = 0;
		switch ( 2 ) {
			case 1 :
			case 2 :
			default :
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i = 0;switch ( 1 ) {case 1 :case 2 :default :}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test10() {
		int i = 0;
		switch ( 1 ) {
			case 1 :
				int j = 10;
			case 2 :
				j = 20;
			default :
				j = 30;
				i = j;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 1 ) {case 1 :int j = 10;case 2 :j = 20;default :j = 30;i = j}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test11() {
		int i = 0;
		switch ( 2 ) {
			case 1 :
				int j = 10;
			case 2 :
				j = 20;
			default :
				j = 30;
				i = j;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( 2 ) {case 1 :int j = 10;case 2 :j = 20;default :j = 30;i = j}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test12() {
		int i = 0;
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					break;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 0;for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;break;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test13() {
		int i = 0;
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					continue;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 0;for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;continue;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test14() {
		int i = 0;
		Test:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					break Test;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;Test:for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;break Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test15() {
		int i = 0;
		Test:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					continue Test;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;Test:for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;continue Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test16() {
		int i = 0;
		Test1:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					Test2:
					switch ( i ) {
						default :
							i++;
							break Test1;
					}
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;Test1:for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;Test2:switch ( i ) {default :i++;break Test1;}}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test17() {
		int i = 0;
		Test1:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				default :
					i++;
					Test2:
					switch ( i ) {
						default :
							i++;
							continue Test1;
					}
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;Test1:for (int j = 0; j < 10; j++) {switch ( i ) {default :i++;Test2:switch ( i ) {default :i++;continue Test1;}}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test18() {
		int i = 0;
		Test:
		switch ( i ) {
			default :
				i++;
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 0;Test:switch ( i ) {default :i++;break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test19() {
		int i = 0;
		Test:
		switch ( i ) {
			default :
				i++;
				break Test;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i = 0;Test:switch ( i ) {default :i++;break Test;}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test20() {
		int i = 0;
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;switch ( i ) {default :Test:switch ( i ) {default :i++;continue Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test21() {
		int i = 1;
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					i++;
					break;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 1;for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :i++;break;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test22() {
		int i = 1;
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					i++;
					continue;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer
			                .wrap("<%int i = 1;for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :i++;continue;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test23() {
		int i = 1;
		Test:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					i++;
					break Test;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 1;Test:for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :i++;break Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test24() {
		int i = 1;
		Test:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					i++;
					continue Test;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 1;Test:for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :i++;continue Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test25() {
		int i = 1;
		Test1:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					Test2:
					switch ( i ) {
						case 1 :
							i++;
							break Test1;
					}
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 1;Test1:for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :Test2:switch ( i ) {case 1  :i++;break Test1;}}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test26() {
		int i = 1;
		Test1:
		for (int j = 0; j < 10; j++) {
			switch ( i ) {
				case 1 :
					Test2:
					switch ( i ) {
						case 1 :
							i++;
							continue Test1;
					}
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 1;Test1:for (int j = 0; j < 10; j++) {switch ( i ) {case 1  :Test2:switch ( i ) {case 1 :i++;continue Test1;}}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test27() {
		int i = 1;
		Test:
		switch ( i ) {
			case 1 :
				i++;
				break;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 1;Test:switch ( i ) {case 1  :i++;break;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test28() {
		int i = 1;
		Test:
		switch ( i ) {
			case 1 :
				i++;
				break Test;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i = 1;Test:switch ( i ) {case 1 :i++;break Test;}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test29() {
		int i = 1;
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 1;switch ( i ) {case 1  :Test:switch ( i ) {case 1  :i++;continue Test;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void error1() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;switch ( 2 );%>")).toTextlets(scope,
			        response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
			//NOP
		}
	}
	
	@Test
	public void error2() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;switch ( 2 ){case 2;}%>")).toTextlets(
			        scope, response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
			//NOP
		}
	}
	
	@Test
	public void error3() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;switch ( 2 ){default;}%>")).toTextlets(
			        scope, response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
			//NOP
		}
	}
	
	@Test
	public void error4() {
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;switch ( 2 ){default:%>")).toTextlets(
			        scope, response)).execute(scope);
			fail();
		}
		catch (final ScriptException e) {
			//NOP
		}
	}
}