package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;

public class DefaultScript extends Script implements Bytecode {
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return this;
	}
	
	@Override
	public String toString() {
		return "default";
	}
}
