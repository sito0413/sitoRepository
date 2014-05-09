package frameWork.base.core.viewCompiler.script.syntax;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.Script;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.Bytecode;
import frameWork.base.core.viewCompiler.script.SyntaxScript;

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
