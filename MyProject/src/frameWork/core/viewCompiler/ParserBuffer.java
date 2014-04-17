package frameWork.core.viewCompiler;

import java.io.CharArrayWriter;
import java.io.File;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.Response;

class ParserBuffer {
	private final CharBuffer charBuffer;
	
	ParserBuffer(final CharBuffer charBuffer) {
		this.charBuffer = charBuffer;
	}
	
	private boolean matches(final String string) {
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
	
	private String parseValue() {
		skipSpaces(charBuffer);
		if ('=' == charBuffer.get(charBuffer.position())) {
			charBuffer.get();
		}
		skipSpaces(charBuffer);
		return parseAttributeValue(charBuffer.get());
	}
	
	private String parseAttributeValue(final char quote) {
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
	
	private String parseName() {
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
	
	private static void skipSpaces(final CharBuffer charBuffer) {
		while (charBuffer.hasRemaining() && (charBuffer.get(charBuffer.position()) <= ' ')) {
			charBuffer.get();
		}
	}
	
	public List<Textlet> toTextlets(final File targetFile, final Map<String, Class<?>> classMap, final Response response) {
		final Set<String> duplicateCheck = new HashSet<>();
		final List<Textlet> textlets = new ArrayList<>();
		Textlet oldTextlet = new Scriptlet("");
		Loop:
		while (charBuffer.hasRemaining()) {
			charBuffer.mark();
			if (matches("<%@") && matches("page")) {//<%@   pageを考慮
				String ct = null;
				String qName = parseName();
				while (qName != null) {
					final String value = parseValue();
					if ("import".equals(qName)) {
						int s = 0;
						int index = -1;
						while ((index = value.indexOf(',', s)) != -1) {
							add(classMap, value.substring(s, index).trim(), duplicateCheck);
							s = index + 1;
						}
						if (s == 0) {
							add(classMap, value.trim(), duplicateCheck);
						}
						else {
							add(classMap, value.substring(s).trim(), duplicateCheck);
						}
					}
					else if ("contentType".equals(qName)) {
						if ((value != null)) {
							ct = value;
						}
					}
					qName = parseName();
				}
				if (matches("%>")) {
					if ((response != null) && (ct != null)) {
						if (ct.indexOf("charset=") < 0) {
							response.setContentType(ct + ";charset="
							        + FileSystem.Config.getString("ViewChareet", "UTF-8"));
						}
						else {
							response.setContentType(ct);
						}
					}
					continue Loop;
				}
			}
			charBuffer.reset();
			
			charBuffer.mark();
			if (matches("<%")) {
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
							String text = caw.toString();
							text = ((text == null ? "" : text));
							if (isExpression) {
								text = "out.write(" + text + ");";
							}
							oldTextlet = oldTextlet.add(textlets, new Scriptlet(text));
							continue Loop;
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
			oldTextlet = oldTextlet.add(textlets, text.toString());
		}
		return textlets;
	}
	
	private static void add(final Map<String, Class<?>> classMap, final String importText,
	        final Set<String> duplicateCheck) {
		try {
			final Class<?> c = Class.forName(importText);
			classMap.put(c.getCanonicalName(), c);
			if (duplicateCheck.add(c.getSimpleName())) {
				classMap.put(c.getSimpleName(), c);
			}
			else {
				classMap.remove(c.getSimpleName());
			}
		}
		catch (final Exception exception) {
			//NOP
		}
	}
}