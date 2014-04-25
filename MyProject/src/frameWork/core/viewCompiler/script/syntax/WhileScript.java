package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class WhileScript extends SyntaxScript<Bytecode> {
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode bytecode = null;
		while (Boolean.parseBoolean(statement.get(0).execute(scope).toString())) {
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
		}
		return bytecode;
	}
	
	@Override
	public void print(final int index) {
		print(index, "while" + statement + "{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}");
		
	}
}
