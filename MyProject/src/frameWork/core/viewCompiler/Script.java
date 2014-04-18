package frameWork.core.viewCompiler;

import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public abstract class Script {
	
	public abstract Bytecode execute(Scope scope) throws Exception;
	
}
