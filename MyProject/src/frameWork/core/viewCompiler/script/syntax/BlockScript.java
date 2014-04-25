package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class BlockScript extends SyntaxScript<Bytecode> {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return block(scriptsBuffer);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		scope.startScope();
		Bytecode value = null;
		loop:
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			value = script.execute(scope);
			switch ( value.toString() ) {
				case "break" :
				case "continue" :
					break loop;
			}
		}
		scope.endScope();
		return value;
	}
	
	@Override
	public void print(final int index) {
		print(index, "{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}");
	}
}
