package frameWork.core.viewCompiler.script.scriptVar;

import frameWork.core.viewCompiler.script.functions.JsCallback;

public class ScriptNativeFunction extends ScriptFunction {
	public ScriptNativeFunction(final JsCallback jsCallback) {
		this.flags = SCRIPTVAR_NATIVE | SCRIPTVAR_FUNCTION;
		this.jsCallback = jsCallback;
	}
}
