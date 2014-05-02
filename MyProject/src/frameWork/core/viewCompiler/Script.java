package frameWork.core.viewCompiler;

import frameWork.core.viewCompiler.script.Bytecode;


public interface Script<T extends Bytecode> {
	public T execute(Scope scope) throws ScriptException;
}
