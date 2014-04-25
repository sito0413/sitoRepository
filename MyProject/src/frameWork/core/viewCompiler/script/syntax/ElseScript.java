package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class ElseScript extends SyntaxScript<Bytecode> {
	private IfScript ifScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.startToken("if")) {
			ifScript = new IfScript();
			return ifScript.create(scriptsBuffer);
		}
		return block(scriptsBuffer);
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (ifScript == null) {
			Bytecode bytecode = null;
			scope.startScope();
			loop:
			for (@SuppressWarnings("rawtypes")
			final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode.isBreak() || bytecode.isContinue()) {
					break loop;
				}
			}
			scope.endScope();
			return bytecode;
		}
		return ifScript.execute(scope);
	}
	
	@Override
	public void print(final int index) {
		print(index, "else {");
		if (ifScript == null) {
			for (@SuppressWarnings("rawtypes")
			final Script script : block) {
				script.print(index + 1);
			}
		}
		else {
			ifScript.print(index);
		}
		print(index, "}");
		
	}
}
