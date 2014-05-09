package frameWork.base.core.viewCompiler.parser;

import java.util.List;

public class Textlet {
	String text;
	
	Textlet(final String text) {
		this.text = (text == null ? "" : text);
	}
	
	@Override
	public final String toString() {
		return text;
	}
	
	final Textlet add(final List<Textlet> textlets, final Scriptlet scriptlet) {
		textlets.add(scriptlet);
		return scriptlet;
	}
	
	Textlet add(final List<Textlet> textlets, final String str) {
		this.text += str;
		return this;
	}
	
	public String toScript() {
		boolean isAllSpace = true;
		final StringBuilder sb = new StringBuilder("out.write(\"");
		final int initLength = sb.length();
		for (int j = 0; j < text.length(); j++) {
			final char ch = text.charAt(j);
			isAllSpace &= Character.isWhitespace(ch);
			switch ( ch ) {
				case '"' :
					sb.append("\\\"");
					break;
				case '\\' :
					sb.append("\\\\");
					break;
				case '\r' :
					sb.append("\\r");
					break;
				case '\n' :
					sb.append("\\n");
					break;
				case '\t' :
					sb.append("\\t");
					break;
				default :
					sb.append(ch);
					break;
			}
		}
		if (!isAllSpace && (sb.length() > initLength)) {
			return sb.append("\");").toString();
		}
		return null;
	}
}