package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

@SuppressWarnings("rawtypes")
public class IfScript extends SyntaxScript<Bytecode> {
	public IfScript(final String label) {
		super(label);
	}
	
	private ElseScript elseScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		statement(scriptsBuffer);
		char c = block(scriptsBuffer);
		if (scriptsBuffer.startToken("else")) {
			elseScript = new ElseScript(label);
			c = elseScript.create(scriptsBuffer);
		}
		return c;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		System.out.println(statement.get(0).execute(scope).getBoolean());
		if (statement.get(0).execute(scope).getBoolean()) {
			scope.startScope();
			Bytecode bytecode = null;
			loop:
			for (final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode.isBreak() || bytecode.isContinue()) {
					if (!label.isEmpty() && bytecode.get().equals(label)) {
						bytecode = null;
					}
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
}
