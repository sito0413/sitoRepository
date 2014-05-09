package frameWork.base.core.viewCompiler.parser;

import java.util.List;

public class Scriptlet extends Textlet {
	Scriptlet(final String text) {
		super(text);
	}
	
	@Override
	Textlet add(final List<Textlet> textlets, final String str) {
		final Textlet textlet = new Textlet(str);
		textlets.add(textlet);
		return textlet;
	}
	
	@Override
	public String toScript() {
		return text;
	}
}