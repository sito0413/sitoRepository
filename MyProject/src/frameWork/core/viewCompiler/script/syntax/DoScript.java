package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class DoScript extends SyntaxScript<Bytecode> {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			block(scriptsBuffer);
			if (scriptsBuffer.startToken("while")) {
				statement(scriptsBuffer);
				switch ( scriptsBuffer.getChar() ) {
					case ';' :
						return scriptsBuffer.gotoNextChar();
					default :
						break;
				}
			}
		}
		throw scriptsBuffer.illegalCharacterError();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode bytecode = null;
		do {
			scope.startScope();
			loop:
			for (@SuppressWarnings("rawtypes")
			final Script script : block) {
				bytecode = script.execute(scope);
				if (bytecode.isBreak()) {
					break loop;
				}
				if (bytecode.isContinue()) {
					continue loop;
				}
			}
			scope.endScope();
		}
		while (Boolean.parseBoolean(statement.get(0).execute(scope).toString()));
		return bytecode;
	}
	
	@Override
	public void print(final int index) {
		print(index, "do{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}while" + statement);
		
	}
}