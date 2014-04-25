package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class IfScript extends SyntaxScript<Bytecode> {
	private ElseScript elseScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		char c = block(scriptsBuffer);
		if (scriptsBuffer.startToken("else")) {
			elseScript = new ElseScript();
			c = elseScript.create(scriptsBuffer);
		}
		return c;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (Boolean.parseBoolean(statement.get(0).execute(scope).toString())) {
			scope.startScope();
			Bytecode bytecode = null;
			loop:
			for (@SuppressWarnings("rawtypes")
			final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode.isBreak() || bytecode.isContinue()) {
					break loop;
				}
			}
			scope.endScope();
			return bytecode;
		}
		else if (elseScript != null) {
			return elseScript.execute(scope);
		}
		return null;
	}
	
	@Override
	public void print(final int index) {
		print(index, "if" + statement + "{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}");
		if (elseScript != null) {
			elseScript.print(index);
		}
	}
}
