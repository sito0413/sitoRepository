package frameWork.core.viewCompiler;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.Deque;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import frameWork.utility.ThrowableUtil;

class Parser {
	static Map<String, ParserResult> parse(final String page, final File viewerDir, final String charsetName) {
		final Deque<String> preCompilationContextStack = new ConcurrentLinkedDeque<>();
		final Map<String, ParserResult> parserResultMap = new ConcurrentHashMap<>();
		preCompilationContextStack.addLast(page);
		while (!preCompilationContextStack.isEmpty()) {
			final String jspFile = preCompilationContextStack.pollFirst();
			if (parserResultMap.get(jspFile) == null) {
				final File targetFile = new File(viewerDir, jspFile.substring(1));
				if (targetFile.exists()) {
					final ParserResult parserResult = new ParserResult(jspFile, preCompilationContextStack);
					try {
						Parser.parse(parserResult, getCharBuffer(targetFile, charsetName));
						parserResultMap.put(jspFile, parserResult);
					}
					catch (final ThreadDeath | VirtualMachineError t) {
						throw (ThreadDeath) t;
					}
					catch (final Throwable e) {
						ThrowableUtil.throwable(e);
					}
				}
			}
		}
		return parserResultMap;
	}
	
	private static ParserResult parse(final ParserResult result, final CharBuffer buffer) {
		while (buffer.hasRemaining()
		        && (parseDirective(buffer, result) || parseIncludeAction(buffer, result) || parseScriptlet(buffer,
		                result))) {
			//NOOP
		}
		return result;
	}
	
	private static CharBuffer getCharBuffer(final File targetFile, final String charsetName) throws IOException {
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			try (InputStreamReader reader = new InputStreamReader(new FileInputStream(targetFile), charsetName)) {
				final char buf[] = new char[5 * 1024];
				for (int i = 0; (i = reader.read(buf)) != -1;) {
					writer.write(buf, 0, i);
				}
			}
			return CharBuffer.wrap(writer.toString());
		}
	}
	
	private static boolean parseDirective(final CharBuffer buffer, final ParserResult result) {
		buffer.mark();
		if (matches(buffer, "<%@") && matches(buffer, "page")) {//<%@   pageを考慮
			final Vector<String> subImports = new Vector<>();
			String contentType = null;
			String qName = parseName(buffer);
			while (qName != null) {
				final String value = parseValue(buffer);
				if ("import".equals(qName)) {
					int s = 0;
					int index = -1;
					while ((index = value.indexOf(',', s)) != -1) {
						subImports.add(value.substring(s, index).trim());
						s = index + 1;
					}
					if (s == 0) {
						subImports.add(value.trim());
					}
					else {
						subImports.add(value.substring(s).trim());
					}
				}
				else if ("contentType".equals(qName)) {
					if ((value != null)) {
						contentType = value;
					}
				}
				qName = parseName(buffer);
			}
			if (matches(buffer, "%>")) {
				result.imports.addAll(subImports);
				if (contentType != null) {
					if (result.contentType.indexOf("charset=") < 0) {
						result.contentType = (contentType + ";charset=UTF-8");
					}
					else {
						result.contentType = (contentType);
					}
				}
				return true;
			}
		}
		buffer.reset();
		return false;
	}
	
	private static boolean parseIncludeAction(final CharBuffer charBuffer, final ParserResult result) {
		charBuffer.mark();
		if (matches(charBuffer, "<jsp:include")) {
			String page = null;
			String qName = parseName(charBuffer);
			while (qName != null) {
				final String value = parseValue(charBuffer);
				if ("page".equals(qName)) {
					page = value;
				}
				qName = parseName(charBuffer);
			}
			if (page != null) {
				skipSpaces(charBuffer);
				if (((charBuffer.get(charBuffer.position()) == '/') && matches(charBuffer, "/>"))
				        || ((charBuffer.get(charBuffer.position()) == '>') && (matches(charBuffer, ">")
				                && matches(charBuffer, "</jsp:include") && matches(charBuffer, ">")))) {
					result.preCompilationContextStack.addLast(page);
					result.textlets.add(new Textlet(TextletType.IncludeAction, page));
					return true;
				}
			}
		}
		charBuffer.reset();
		return false;
	}
	
	private static boolean parseScriptlet(final CharBuffer charBuffer, final ParserResult result) {
		charBuffer.mark();
		if (matches(charBuffer, "<%")) {
			final boolean isExpression = charBuffer.get(charBuffer.position()) == '=';
			if (isExpression) {
				charBuffer.get();
			}
			final CharArrayWriter caw = new CharArrayWriter();
			while (charBuffer.hasRemaining()) {
				final int ch = charBuffer.get();
				if (ch == '%') {
					if (charBuffer.get() == '>') {
						caw.close();
						result.textlets.add(new Textlet(isExpression ? TextletType.Expression : TextletType.Scriptlet,
						        caw.toString()));
						return true;
					}
				}
				else {
					caw.write(ch);
				}
			}
			caw.close();
		}
		charBuffer.reset();
		
		final CharArrayWriter text = new CharArrayWriter();
		text.write(charBuffer.get());
		while (charBuffer.hasRemaining()) {
			if (charBuffer.get(charBuffer.position()) == '<') {
				break;
			}
			text.write(charBuffer.get());
		}
		result.textlets.add(new Textlet(TextletType.Text, text.toString()));
		return true;
	}
	
	private static String parseValue(final CharBuffer charBuffer) {
		skipSpaces(charBuffer);
		if ('=' == charBuffer.get(charBuffer.position())) {
			charBuffer.get();
		}
		skipSpaces(charBuffer);
		return parseAttributeValue(charBuffer, charBuffer.get());
	}
	
	private static String parseName(final CharBuffer charBuffer) {
		skipSpaces(charBuffer);
		char ch = charBuffer.get(charBuffer.position());
		if (Character.isLetter(ch) || (ch == '_') || (ch == ':')) {
			final StringBuilder buf = new StringBuilder();
			buf.append(ch);
			charBuffer.get();
			ch = charBuffer.get(charBuffer.position());
			while (Character.isLetter(ch) || Character.isDigit(ch) || (ch == '.') || (ch == '_') || (ch == '-')
			        || (ch == ':')) {
				buf.append(ch);
				charBuffer.get();
				ch = charBuffer.get(charBuffer.position());
			}
			return buf.toString();
		}
		return null;
	}
	
	private static String parseAttributeValue(final CharBuffer charBuffer, final char quote) {
		skipSpaces(charBuffer);
		int prev = ' ';
		final CharArrayWriter caw = new CharArrayWriter();
		for (int ch = charBuffer.get(); ch != -1; prev = ch, ch = charBuffer.get()) {
			caw.write(ch);
			if ((ch == '\\') && (prev == '\\')) {
				ch = 0;
			}
			else if ((ch == quote) && (prev != '\\')) {
				break;
			}
		}
		caw.close();
		final String input = caw.toString();
		final int size = input.length();
		final StringBuilder result = new StringBuilder(size);
		int i = 0;
		while (i < size) {
			char ch = input.charAt(i);
			if (ch == '&') {
				if (((i + 5) < size) && (input.charAt(i + 1) == 'a') && (input.charAt(i + 2) == 'p')
				        && (input.charAt(i + 3) == 'o') && (input.charAt(i + 4) == 's') && (input.charAt(i + 5) == ';')) {
					ch = '\'';
					i += 6;
				}
				else if (((i + 5) < size) && (input.charAt(i + 1) == 'q') && (input.charAt(i + 2) == 'u')
				        && (input.charAt(i + 3) == 'o') && (input.charAt(i + 4) == 't') && (input.charAt(i + 5) == ';')) {
					ch = '\"';
					i += 6;
				}
				else {
					++i;
				}
			}
			else if ((ch == '\\') && ((i + 1) < size)) {
				ch = input.charAt(i + 1);
				if ((ch == '\\') || (ch == '\"') || (ch == '\'')) {
					i += 2;
				}
				else {
					ch = '\\';
					++i;
				}
			}
			else if ((ch == quote)) {
				if ((i + 1) < size) {
					result.append('\\');
					++i;
				}
				++i;
				break;
			}
			else {
				++i;
			}
			result.append(ch);
		}
		skipSpaces(charBuffer);
		return result.toString();
	}
	
	private static boolean matches(final CharBuffer charBuffer, final String string) {
		final int len = string.length();
		skipSpaces(charBuffer);
		int position = charBuffer.position();
		if ((position + len) >= charBuffer.limit()) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			if (string.charAt(i) != charBuffer.get(position++)) {
				return false;
			}
		}
		charBuffer.position(position);
		return true;
	}
	
	private static void skipSpaces(final CharBuffer charBuffer) {
		while (charBuffer.hasRemaining() && (charBuffer.get(charBuffer.position()) <= ' ')) {
			charBuffer.get();
		}
	}
	
}