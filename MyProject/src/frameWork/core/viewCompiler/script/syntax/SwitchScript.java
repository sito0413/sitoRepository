package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class SwitchScript extends SyntaxScript {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		scriptsBuffer.statement(statement);
		switch ( scriptsBuffer.getChar() ) {
			case '{' :
				return block(scriptsBuffer);
			default :
				throw new Exception("Error { at " + scriptsBuffer.getPosition());
		}
	}
	
	@Override
	protected final char block(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				if (scriptsBuffer.startWith("case")) {
					scriptsBuffer.skip();
					final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
					if (scriptsBuffer.getChar() != ':') {
						throw new Exception("Error : at " + scriptsBuffer.getPosition());
					}
				}
				else if (scriptsBuffer.startWith("default")) {
					scriptsBuffer.skip();
					if (scriptsBuffer.getChar() != ':') {
						throw new Exception("Error : at " + scriptsBuffer.getPosition());
					}
				}
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				add(subScript);
				switch ( subScript.create(scriptsBuffer) ) {
					case '}' :
						return scriptsBuffer.gotoNextChar();
					case ';' :
						scriptsBuffer.gotoNextChar();
						break;
					default :
						break;
				}
			}
			throw new Exception("Error } at " + scriptsBuffer.getPosition());
		}
		final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
		add(subScript);
		final char c = subScript.create(scriptsBuffer);
		switch ( c ) {
			case ';' :
			case '}' :
				return scriptsBuffer.gotoNextChar();
			default :
				return c;
		}
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		final Bytecode label = statement.get(0).execute(scope);
		final boolean flag = false;
		scope.startScope();
		for (final Script script : block) {
		}
		scope.endScope();
		return null;
	}
}
