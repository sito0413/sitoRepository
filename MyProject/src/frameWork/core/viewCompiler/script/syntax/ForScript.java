package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class ForScript extends SyntaxScript<Bytecode> {
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode bytecode = null;
		if (statement.size() == 3) {
			scope.startScope();
			statement.get(0).execute(scope);
			while (Boolean.parseBoolean(statement.get(1).execute(scope).toString())) {
				scope.startScope();
				loop:
				for (@SuppressWarnings("rawtypes")
				final Script script : block) {
					bytecode = script.execute(scope);
					if (bytecode.isBreak()) {
						bytecode = null;
						break loop;
					}
					if (bytecode.isContinue()) {
						bytecode = null;
						continue loop;
					}
				}
				scope.endScope();
				statement.get(2).execute(scope);
			}
			scope.endScope();
		}
		else {
			scope.startScope();
			statement.get(0).execute(scope);
			statement.get(1).execute(scope);
			
			@SuppressWarnings("rawtypes")
			final Iterable iterable = (Iterable) scope.get(Iterable.class, statement.get(1).toString()).get();
			for (final Object object : iterable) {
				scope.startScope();
				scope.put(statement.get(0).toString(), object);
				loop:
				for (@SuppressWarnings("rawtypes")
				final Script script : block) {
					bytecode = script.execute(scope);
					if (bytecode.isBreak()) {
						bytecode = null;
						break loop;
					}
					if (bytecode.isContinue()) {
						bytecode = null;
						continue loop;
					}
				}
				scope.endScope();
			}
			scope.endScope();
		}
		return bytecode;
	}
	
	@Override
	public void print(final int index) {
		print(index, "for" + statement + "{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}");
		
	}
	
}
