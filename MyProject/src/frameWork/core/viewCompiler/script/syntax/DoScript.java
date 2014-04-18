package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class DoScript extends SyntaxScript {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			block(scriptsBuffer);
			if (scriptsBuffer.startWith("while")) {
				scriptsBuffer.statement(statement);
				switch ( scriptsBuffer.getChar() ) {
					case ';' :
						return scriptsBuffer.gotoNextChar();
					default :
						throw new Exception("Error ; at " + scriptsBuffer.getPosition());
				}
			}
			throw new Exception("Error while at " + scriptsBuffer.getPosition());
		}
		throw new Exception("Error { at " + scriptsBuffer.getPosition());
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		Bytecode value = null;
		do {
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
		while (Boolean.parseBoolean(statement.get(0).execute(scope).toString()));
		return value;
	}
}
