package frameWork.core.viewCompiler.script.functions;

import frameWork.core.viewCompiler.script.scriptVar.ScriptFunction;

public class JsPrint implements JsCallback {
	@Override
	public void run(final ScriptFunction functionRoot) {
		System.out.println("> " + functionRoot.getParameter("text").getString());
	}
}
