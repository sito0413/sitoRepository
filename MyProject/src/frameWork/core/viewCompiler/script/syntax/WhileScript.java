package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class WhileScript extends SyntaxScript {
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode value = null;
		while (Boolean.parseBoolean(statement.get(0).execute(scope).toString())) {
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
		}
		return value;
	}
}
