package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class ElseScript extends SyntaxScript {
	private IfScript ifScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.startWith("if")) {
			ifScript = new IfScript();
			return ifScript.create(scriptsBuffer);
		}
		return block(scriptsBuffer);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (ifScript == null) {
			Bytecode value = null;
			scope.startScope();
			loop:
			for (final Script script : block) {
				value = script.execute(scope);
				switch ( value.toString() ) {
					case "break;" :
					case "continue" :
						break loop;
				}
			}
			scope.endScope();
			return value;
		}
		return ifScript.execute(scope);
	}
}
