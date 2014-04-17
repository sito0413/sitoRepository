package frameWork.core.viewCompiler;

import java.util.List;

public class Scriptlet extends Textlet {
	public Scriptlet(final String text) {
		super(text);
	}
	
	@Override
	public Textlet add(final List<Textlet> textlets, final String str) {
		final Textlet textlet = new Textlet(str);
		textlets.add(textlet);
		return textlet;
	}
	
	@Override
	public Scriptlet toScriptlet() {
		return this;
	}
	
}