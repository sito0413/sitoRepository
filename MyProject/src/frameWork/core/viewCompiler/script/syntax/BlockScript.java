package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.SyntaxScript;

@SuppressWarnings("rawtypes")
public class BlockScript extends SyntaxScript<Bytecode> {
	public BlockScript(final String label) {
		super(label);
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		return block(scriptsBuffer);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		scope.startScope();
		Bytecode bytecode = null;
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
}
