package frameWork.base.core.viewCompiler.script.syntax;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.Script;
import frameWork.base.core.viewCompiler.script.bytecode.Bytecode;

@SuppressWarnings("rawtypes")
public class WhileScript extends Script<Bytecode> {
	public WhileScript(final String label) {
		super(label);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		Bytecode bytecode = null;
		loop:
		while (statement.get(0).execute(scope).getBoolean()) {
			scope.startScope();
			for (final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode != null) {
					if (bytecode.isBreak()) {
						if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
							bytecode = null;
						}
						scope.endScope();
						break loop;
					}
					if (bytecode.isContinue()) {
						if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
							bytecode = null;
							scope.endScope();
							continue loop;
						}
						scope.endScope();
						break loop;
					}
				}
			}
			scope.endScope();
		}
		return bytecode;
	}
}
