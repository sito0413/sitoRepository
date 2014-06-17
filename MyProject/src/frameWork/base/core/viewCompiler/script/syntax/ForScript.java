package frameWork.base.core.viewCompiler.script.syntax;

import java.lang.reflect.Array;
import java.util.ArrayList;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.Script;
import frameWork.base.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.base.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.expression.OperatorScript;

@SuppressWarnings("rawtypes")
public class ForScript extends Script<Bytecode> {
	public ForScript(final String label) {
		super(label);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		Bytecode bytecode = null;
		if (statement.size() == 3) {
			scope.startScope();
			if (statement.get(0) != null) {
				statement.get(0).execute(scope);
			}
			loop:
			while (statement.get(1).execute(scope).getBoolean()) {
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
								if (statement.get(2) != null) {
									statement.get(2).execute(scope);
								}
								scope.endScope();
								continue loop;
							}
							scope.endScope();
							break loop;
						}
					}
				}
				scope.endScope();
				if (statement.get(2) != null) {
					statement.get(2).execute(scope);
				}
			}
			scope.endScope();
		}
		else {
			scope.startScope();
			final InstanceBytecode instanceBytecode0 = statement.get(0).execute(scope);
			final InstanceBytecode instanceBytecode1 = statement.get(1).execute(scope);
			
			final Iterable iterable;
			if (instanceBytecode1.type().isArray()) {
				final ArrayList list = new ArrayList<>();
				final Object object = instanceBytecode1.get();
				final int length = Array.getLength(object);
				for (int i = 0; i < length; i++) {
					list.add(Array.get(object, i));
				}
				iterable = list;
			}
			else {
				iterable = (Iterable) instanceBytecode1.get();
			}
			loop:
			for (final Object object : iterable) {
				scope.startScope();
				new OperatorScript("=", instanceBytecode0, new InstanceBytecode(object.getClass(), object))
				        .execute(scope);
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
			scope.endScope();
		}
		return bytecode;
	}
}
