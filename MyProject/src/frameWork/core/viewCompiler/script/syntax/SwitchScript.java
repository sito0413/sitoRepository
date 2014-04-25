package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class SwitchScript extends SyntaxScript<Bytecode> {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		switch ( scriptsBuffer.getChar() ) {
			case '{' :
				return block(scriptsBuffer);
			default :
				throw new Exception("Error { at " + scriptsBuffer.getPosition());
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	protected final char block(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				if (scriptsBuffer.startToken("case")) {
					scriptsBuffer.skip();
					@SuppressWarnings("rawtypes")
					final SyntaxScript subScript = scriptsBuffer.getStatementToken();
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
				}
				else if (scriptsBuffer.startToken("default")) {
					scriptsBuffer.skip();
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
				}
				scriptsBuffer.skip();
				@SuppressWarnings("rawtypes")
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
			throw scriptsBuffer.illegalCharacterError();
		}
		@SuppressWarnings("rawtypes")
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
	
	@SuppressWarnings("unused")
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		final Bytecode label = statement.get(0).execute(scope);
		final boolean flag = false;
		scope.startScope();
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
		}
		scope.endScope();
		return null;
	}
	
	@Override
	public void print(final int index) {
		print(index, "Switch" + statement + "{");
		for (@SuppressWarnings("rawtypes")
		final Script script : block) {
			script.print(index + 1);
		}
		print(index, "}");
	}
}
