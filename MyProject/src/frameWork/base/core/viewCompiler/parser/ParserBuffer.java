package frameWork.base.core.viewCompiler.parser;

import java.io.CharArrayWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.viewCompiler.Scope;

public class ParserBuffer {
	private final CharBuffer charBuffer;
	
	public ParserBuffer(final CharBuffer charBuffer) {
		this.charBuffer = charBuffer;
	}
	
	public List<Textlet> toTextlets(final Scope scope, final Response response) {
		final Set<String> duplicateCheck = new HashSet<>();
		final List<Textlet> textlets = new ArrayList<>();
		Textlet oldTextlet = new Scriptlet("");
		while (charBuffer.hasRemaining()) {
			if (!parsePage(scope, response, duplicateCheck)) {
				final Scriptlet scriptlet = parseScriptlet(textlets);
				if (scriptlet == null) {
					oldTextlet = oldTextlet.add(textlets, parseText());
				}
				else {
					oldTextlet = oldTextlet.add(textlets, scriptlet);
				}
			}
		}
		return textlets;
	}
	
	String parseText() {
		final CharArrayWriter text = new CharArrayWriter();
		if (charBuffer.hasRemaining()) {
			text.write(charBuffer.get());
			while (charBuffer.hasRemaining()) {
				if (charBuffer.get(charBuffer.position()) == '<') {
					break;
				}
				text.write(charBuffer.get());
			}
		}
		return text.toString();
	}
	
	Scriptlet parseScriptlet(final List<Textlet> textlets) {
		charBuffer.mark();
		if (matches("<%")) {
			if (charBuffer.hasRemaining()) {
				final boolean isExpression = charBuffer.get(charBuffer.position()) == '=';
				if (isExpression) {
					charBuffer.get();
				}
				final CharArrayWriter caw = new CharArrayWriter();
				while (charBuffer.hasRemaining()) {
					final int ch = charBuffer.get();
					if (ch == '%') {
						if (charBuffer.hasRemaining()) {
							if (charBuffer.get(charBuffer.position()) == '>') {
								charBuffer.get();
								caw.close();
								String text = caw.toString();
								if (isExpression && !text.isEmpty()) {
									text = FileSystem.Config.VIEW_OUTPUT_METHOD + "(" + text + ");";
								}
								return new Scriptlet(text);
							}
						}
					}
					caw.write(ch);
					if ((ch == '"') && (charBuffer.get(charBuffer.position() - 2) != '\\')) {
						while (charBuffer.hasRemaining()) {
							final int c = charBuffer.get();
							caw.write(c);
							if ((c == '"') && (charBuffer.get(charBuffer.position() - 2) != '\\')) {
								break;
							}
						}
					}
					
				}
				caw.close();
			}
		}
		charBuffer.reset();
		return null;
	}
	
	boolean parsePage(final Scope scope, final Response response, final Set<String> duplicateCheck) {
		charBuffer.mark();
		if (matches("<%@") && matches("page")) {//<%@   pageを考慮
			String ct = null;
			String qName = null;
			while (((qName = parseName()) != null)) {
				if (matches("=") && matches("\"")) {
					skipSpaces();
					final CharArrayWriter caw = new CharArrayWriter();
					if (charBuffer.hasRemaining()) {
						int ch = charBuffer.get();
						while (charBuffer.hasRemaining() && (ch != '\"') && (ch != '%')) {
							caw.write(ch);
							ch = charBuffer.get();
						}
						if (ch == '%') {
							charBuffer.position(charBuffer.position() - 1);
						}
						else {
							skipSpaces();
						}
					}
					caw.close();
					if ("import".equals(qName)) {
						try {
							final Class<?> c = Class.forName(caw.toString().trim());
							scope.putImport(c.getCanonicalName(), c);
							if (duplicateCheck.add(c.getSimpleName())) {
								scope.putImport(c.getSimpleName(), c);
							}
							else {
								scope.removeImport(c.getSimpleName());
							}
						}
						catch (final Exception exception) {
							FileSystem.Log.logging(exception);
						}
					}
					else if ("contentType".equals(qName)) {
						ct = caw.toString().trim();
					}
				}
			}
			
			if (matches("%>")) {
				if (ct != null) {
					if (ct.indexOf("charset=") < 0) {
						response.setContentType(ct + ";charset=" + FileSystem.Config.VIEW_CHAREET);
					}
					else {
						response.setContentType(ct);
					}
				}
				return true;
			}
		}
		charBuffer.reset();
		return false;
	}
	
	private boolean matches(final String string) {
		final int len = string.length();
		skipSpaces();
		int position = charBuffer.position();
		if ((position + len) > charBuffer.limit()) {
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
	
	private String parseName() {
		skipSpaces();
		final StringBuilder buf = new StringBuilder();
		if (charBuffer.hasRemaining()) {
			char ch = charBuffer.get();
			if (Character.isLetter(ch)) {
				buf.append(ch);
				if (charBuffer.hasRemaining()) {
					ch = charBuffer.get();
					while (Character.isLetter(ch)) {
						buf.append(ch);
						if (charBuffer.hasRemaining()) {
							ch = charBuffer.get();
						}
						else {
							break;
						}
					}
					if (charBuffer.hasRemaining()) {
						charBuffer.position(charBuffer.position() - 1);
					}
				}
				return buf.toString();
				
			}
			charBuffer.position(charBuffer.position() - 1);
		}
		return null;
	}
	
	private void skipSpaces() {
		while (charBuffer.hasRemaining() && (charBuffer.get(charBuffer.position()) <= ' ')) {
			charBuffer.get();
		}
	}
	
}
