package frameWork.base.core.viewCompiler.script.syntax;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.Script;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.Bytecode;
import frameWork.base.core.viewCompiler.script.SyntaxScript;

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
