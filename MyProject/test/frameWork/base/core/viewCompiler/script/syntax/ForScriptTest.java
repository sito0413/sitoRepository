package frameWork.base.core.viewCompiler.script.syntax;

import static org.junit.Assert.*;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
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
public class ForScriptTest {
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
		int i;
		for (i = 0; i <= 10; i++) {
			i++;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i;for (i = 0; i <= 10; i++) {i ++;}%>")).toTextlets(scope,
			                response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test2() {
		int i;
		for (i = 0; i < 0; i++) {
			i++;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i;for (i = 0; i < 0; i++) {i++;}%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test3() {
		int i;
		for (i = 0; i <= 10; i++) {
			i++;
			if (i > 5) {
				break;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i;for (i = 0; i <= 10; i++) {i++;if (i > 5) {break;}}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test4() {
		int i;
		for (i = 0; i <= 10; i++) {
			i++;
			if (i > 5) {
				continue;
			}
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i;for (i = 0; i <= 10; i++) {i++;if (i > 5) {continue;}}%>")).toTextlets(
			        scope, response)).execute(scope);
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
		for (i = 0; i <= 10; i++) {
			i++;
			test2:
			for (final int j = 0; i <= 10; i++) {
				test3:
				{
					break test1;
					//					i++;
				}
				//				i++;
			}
			i++;
		}
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:for (i = 0; i <= 10; i++) {i++;test2:for (int j = 0; i <= 10; i++) {test3:{break test1;i++;}i++;}i++;}i++;%>"))
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
		test1:
		for (i = 0; i <= 10; i++) {
			i++;
			test2:
			for (final int j = 0; i <= 10; i++) {
				test3:
				{
					continue test1;
					//					i++;
				}
				//				i++;
			}
			i++;
		}
		i++;
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:for (i = 0; i <= 10; i++) {i++;test2:for (int j = 0; i <= 10; i++) {test3:{continue test1;i++;}i++;}i++;}i++;%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test7() {
		int i;
		for (i = 0; i <= 10;) {
			i++;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i;for (i = 0; i <= 10;) {i++;}%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test8() {
		int i = 0;
		for (; i <= 10; i++) {
			i++;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 0;for (; i <= 10; i++) {i ++;}%>")).toTextlets(scope,
			                response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test9() {
		int i = 0;
		for (; i <= 10;) {
			i++;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i = 0;for (; i <= 10;) {i ++;}%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test10() {
		int i = 0;
		for (i = 0; i <= 10;) {
			i++;
			continue;
		}
		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i = 0;for (i = 0; i <= 10;) {i++;continue;}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test11() {
		int i;
		for (i = 0; i <= 10; i++)
			i++;

		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i;for (i = 0; i <= 10; i++) i ++;%>")).toTextlets(scope,
			                response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test12() {
		int i;
		for (i = 0; i < 0; i++)
			i++;

		final Scope scope = new Scope();
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i;for (i = 0; i < 0; i++) i++;%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void test13() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		for (final int j : is) {
			i = i + j;
		}
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) {i = i + j;}%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test14() {
		final int[] is = {
		        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
		};
		int i = 0;
		for (final int j : is) {
			i = i + j;
		}
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) {i = i + j;}%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test15() {
		final List<Integer> is = new ArrayList<>();
		int i = 0;
		for (final int j : is) {
			i++;
		}
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) {i++;}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test16() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		for (final int j : is) {
			i++;
			if (i > 5) {
				break;
			}
		}
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) {i++;if (i > 5) {break;}}%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test17() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		for (final int j : is) {
			i++;
			if (i > 5) {
				continue;
			}
		}
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(
			        CharBuffer.wrap("<%int i=0;for ( int j:is) {i++;if (i > 5) {continue;}}%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test18() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		test1:
		for (final int j : is) {
			i++;
			test2:
			for (final int k : is) {
				test3:
				{
					break test1;
					//					i++;
				}
				//				i++;
			}
			i++;
		}
		i++;
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:for (int j : is) {i++;test2:for (int k : is) {test3:{break test1;i++;}i++;}i++;}i++;%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test19() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		test1:
		for (final int j : is) {
			i++;
			test2:
			for (final int k : is) {
				test3:
				{
					continue test1;
					//					i++;
				}
				//				i++;
			}
			i++;
		}
		i++;
		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(
			        new ParserBuffer(
			                CharBuffer
			                        .wrap("<%int i = 0;test1:for (int j : is) {i++;test2:for (int k : is) {test3:{continue test1;i++;}i++;}i++;}i++;%>"))
			                .toTextlets(scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void test20() {
		final List<Integer> is = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			is.add(i);
		}
		int i = 0;
		for (final int j : is)
			i = i + j;

		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) i = i + j;%>")).toTextlets(
			        scope, response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test21() {
		final List<Integer> is = new ArrayList<>();
		int i = 0;
		for (final int j : is)
			i++;

		final Scope scope = new Scope();
		scope.put("is", is);
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap("<%int i=0;for ( int j:is) i++;%>")).toTextlets(scope,
			        response)).execute(scope);
			assertEquals(scope.get(Integer.class, "i").get(), i);
		}
		catch (final ScriptException e) {
			fail(e.getMessage());
		}
	}
}
