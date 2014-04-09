package frameWork.core.viewCompiler.script.functions;

import frameWork.core.viewCompiler.script.scriptVar.ScriptFunction;

public interface JsCallback {
	void run(ScriptFunction functionRoot) throws Exception;
}
