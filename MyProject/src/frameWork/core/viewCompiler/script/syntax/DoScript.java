package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

@SuppressWarnings("rawtypes")
public class DoScript extends SyntaxScript<Bytecode> {
	public DoScript(final String label) {
		super(label);
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
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
	public Bytecode execute(final Scope scope) throws ScriptException {
		Bytecode bytecode = null;
		do {
			scope.startScope();
			loop:
			for (final Script script : block) {
				bytecode = script.execute(scope);
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
			scope.endScope();
		}
		while (statement.get(0).execute(scope).getBoolean());
		return bytecode;
	}
}