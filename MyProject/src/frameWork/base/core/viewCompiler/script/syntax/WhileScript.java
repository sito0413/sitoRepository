package frameWork.base.core.viewCompiler.script.syntax;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.Script;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.Bytecode;
import frameWork.base.core.viewCompiler.script.SyntaxScript;

@SuppressWarnings("rawtypes")
public class WhileScript extends SyntaxScript<Bytecode> {
	public WhileScript(final String label) {
		super(label);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		Bytecode bytecode = null;
		while (statement.get(0).execute(scope).getBoolean()) {
			scope.startScope();
			loop:
			for (final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode != null) {
					if (bytecode.isBreak()) {
						if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
							bytecode = null;
						}
						break loop;
					}
					if (bytecode.isContinue()) {
						if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
							bytecode = null;
							continue loop;
						}
						break loop;
					}
				}
			}
			scope.endScope();
		}
		return bytecode;
	}
}
