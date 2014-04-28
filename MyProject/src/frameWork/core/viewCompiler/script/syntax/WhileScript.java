package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;

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
			scope.endScope();
		}
		return bytecode;
	}
}
