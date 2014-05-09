package frameWork.base.core.viewCompiler;

import frameWork.base.core.viewCompiler.script.Bytecode;


public interface Script<T extends Bytecode> {
	public T execute(Scope scope) throws ScriptException;
}
