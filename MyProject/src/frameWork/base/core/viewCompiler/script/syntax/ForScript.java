package frameWork.base.core.viewCompiler.script.syntax;

import java.util.Arrays;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.Script;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.Bytecode;
import frameWork.base.core.viewCompiler.script.SyntaxScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.syntax.expression.OperatorScript;

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
				statement.get(2).execute(scope);
			}
			scope.endScope();
		}
		else {
			scope.startScope();
			final InstanceBytecode instanceBytecode0 = statement.get(0).execute(scope);
			final InstanceBytecode instanceBytecode1 = statement.get(1).execute(scope);
			
			final Iterable iterable;
			if (instanceBytecode1.type().isArray()) {
				iterable = Arrays.asList(instanceBytecode1.get());
			}
			else {
				iterable = (Iterable) instanceBytecode1.get();
			}
			for (final Object object : iterable) {
				scope.startScope();
				new OperatorScript("=", instanceBytecode0, new InstanceBytecode(object.getClass(), object))
				        .execute(scope);
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
			scope.endScope();
		}
		return bytecode;
	}
}
