package frameWork.script.functions;

import frameWork.script.scriptVar.ScriptFunction;

public interface JsCallback {
	void run(ScriptFunction functionRoot) throws Exception;
}
