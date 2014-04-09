package frameWork.core.viewCompiler;

import java.util.List;

public class Textlet {
	protected String text;
	
	public Textlet(final String text) {
		this.text = (text == null ? "" : text);
	}
	
	@Override
	public final String toString() {
		return text;
	}
	
	public final Textlet add(final List<Textlet> textlets, final Scriptlet scriptlet) {
		textlets.add(scriptlet);
		return scriptlet;
	}
	
	public Textlet add(final List<Textlet> textlets, final String str) {
		this.text += str;
		return this;
	}
	
	public Scriptlet toScriptlet() {
		boolean isAllSpace = true;
		final StringBuilder sb = new StringBuilder("out.write(\"");
		final int initLength = sb.length();
		for (int j = 0; j < text.length(); j++) {
			char ch = text.charAt(j);
			isAllSpace &= Character.isWhitespace(ch);
			if (ch == '\t') {
				ch = ' ';
			}
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
				default :
					sb.append(ch);
					break;
			}
		}
		if (!isAllSpace && (sb.length() > initLength)) {
			return new Scriptlet(sb.append("\");\r\n").toString());
		}
		return null;
	}
}