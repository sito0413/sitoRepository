package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;

public class ForScript extends SyntaxScript {
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode value = null;
		if (statement.size() == 3) {
			scope.startScope();
			statement.get(0).execute(scope);
			while (Boolean.parseBoolean(statement.get(1).execute(scope).toString())) {
				scope.startScope();
				loop:
				for (final Script script : block) {
					value = script.execute(scope);
					switch ( value.toString() ) {
						case "break" :
							break loop;
						case "continue" :
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
			final Iterable iterable = (Iterable) scope.get(statement.get(1).toString()).object;
			for (final Object object : iterable) {
				scope.startScope();
				scope.put(statement.get(0).toString(), new ObjectScript(object.getClass(), object));
				loop:
				for (final Script script : block) {
					value = script.execute(scope);
					switch ( value.toString() ) {
						case "break" :
							break loop;
						case "continue" :
							continue loop;
							
					}
				}
				scope.endScope();
			}
			scope.endScope();
		}
		return value;
	}
}
