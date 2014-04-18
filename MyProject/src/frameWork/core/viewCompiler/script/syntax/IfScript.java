package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class IfScript extends SyntaxScript {
	private ElseScript elseScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		scriptsBuffer.statement(statement);
		char c = block(scriptsBuffer);
		if (scriptsBuffer.startWith("else")) {
			elseScript = new ElseScript();
			c = elseScript.create(scriptsBuffer);
		}
		return c;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (Boolean.parseBoolean(statement.get(0).execute(scope).toString())) {
			scope.startScope();
			Bytecode value = null;
			loop:
			for (final Script script : block) {
				value = script.execute(scope);
				switch ( value.toString() ) {
					case "break" :
					case "continue" :
						break loop;
				}
			}
			scope.endScope();
			return value;
		}
		else if (elseScript != null) {
			return elseScript.execute(scope);
		}
		return null;
	}
}
