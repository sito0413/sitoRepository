package frameWork.core.viewCompiler.script;


public interface Script<T extends Bytecode> {
	public T execute(Scope scope) throws ScriptException;
}
