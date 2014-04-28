package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;

@SuppressWarnings("rawtypes")
public class ForScript extends SyntaxScript<Bytecode> {
	public ForScript(final String label) {
		super(label);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		Bytecode bytecode = null;
		if (statement.size() == 3) {
			scope.startScope();
			statement.get(0).execute(scope);
			while (statement.get(1).execute(scope).getBoolean()) {
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
				statement.get(2).execute(scope);
			}
			scope.endScope();
		}
		else {
			scope.startScope();
			final InstanceBytecode instanceBytecode0 = statement.get(0).execute(scope);
			final InstanceBytecode instanceBytecode1 = statement.get(1).execute(scope);
			
			final Iterable iterable = (Iterable) instanceBytecode1.get();
			for (final Object object : iterable) {
				scope.startScope();
				instanceBytecode0.set(scope, new ObjectBytecode(object.getClass(), object));
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
			scope.endScope();
		}
		return bytecode;
	}
}
