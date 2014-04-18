package frameWork.core.viewCompiler.script;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;

public class StringScript extends Script {
	final String token;
	
	public StringScript(final String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return new ObjectScript(String.class, token.substring(1, token.length() - 1));
	}
}
