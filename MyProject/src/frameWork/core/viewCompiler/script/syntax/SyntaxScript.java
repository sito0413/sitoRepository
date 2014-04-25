package frameWork.core.viewCompiler.script.syntax;

import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

@SuppressWarnings("rawtypes")
public abstract class SyntaxScript<T extends Bytecode> extends Script<T> {
	protected final List<ExpressionScript> statement = new ArrayList<>();
	protected final List<SyntaxScript> block = new ArrayList<>();
	
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		return block(scriptsBuffer);
	}
	
	protected final void add(final SyntaxScript subScript) {
		block.add(subScript);
	}
	
	protected char block(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				scriptsBuffer.skip();
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				add(subScript);
				switch ( subScript.create(scriptsBuffer) ) {
					case '}' :
						return scriptsBuffer.gotoNextChar();
					case ';' :
						if (scriptsBuffer.gotoNextChar() == '}') {
							return scriptsBuffer.gotoNextChar();
						}
						break;
					default :
						break;
				}
			}
			throw scriptsBuffer.illegalCharacterError();
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
	
	protected final void statement(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '(') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == ')') {
				scriptsBuffer.gotoNextChar();
				return;
			}
			while (scriptsBuffer.hasRemaining()) {
				statement.add(scriptsBuffer.getStatementToken());
				switch ( scriptsBuffer.getChar() ) {
					case ')' :
						scriptsBuffer.gotoNextChar();
						return;
					default :
						scriptsBuffer.gotoNextChar();
						break;
				}
			}
		}
		throw scriptsBuffer.illegalCharacterError();
	}
}
