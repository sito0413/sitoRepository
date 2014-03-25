package frameWork.script.functions;

import frameWork.script.scriptVar.ScriptFunction;

public class JsPrint implements JsCallback {
	@Override
	public void run(final ScriptFunction functionRoot) {
		System.out.println("> " + functionRoot.getParameter("text").getString());
	}
}
