package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.SyntaxScript;

@SuppressWarnings("rawtypes")
public class ElseScript extends SyntaxScript<Bytecode> {
	private IfScript ifScript;
	
	public ElseScript(final String label) {
		super(label);
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		if (scriptsBuffer.startToken("if")) {
			ifScript = new IfScript(label);
			return ifScript.create(scriptsBuffer);
		}
		return block(scriptsBuffer);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		if (ifScript == null) {
			Bytecode bytecode = null;
			scope.startScope();
			loop:
			for (final Script script : block) {
				bytecode = script.execute(scope);
				if ((bytecode != null) && (bytecode.isBreak() || bytecode.isContinue())) {
					if (!label.isEmpty() && bytecode.get().equals(label)) {
						bytecode = null;
					}
					break loop;
				}
			}
			scope.endScope();
			return bytecode;
		}
		return ifScript.execute(scope);
	}
}
