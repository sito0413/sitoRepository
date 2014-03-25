package frameWork.script.scriptVar;

import frameWork.script.functions.JsCallback;

public class ScriptNativeFunction extends ScriptFunction {
	public ScriptNativeFunction(final JsCallback jsCallback) {
		this.flags = SCRIPTVAR_NATIVE | SCRIPTVAR_FUNCTION;
		this.jsCallback = jsCallback;
	}
}
