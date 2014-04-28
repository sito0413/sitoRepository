package frameWork.core.viewCompiler.script.syntax;

import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

@SuppressWarnings("rawtypes")
public abstract class SyntaxScript<T extends Bytecode> implements Script<T> {
	protected final List<ExpressionScript> statement = new ArrayList<>();
	protected final List<SyntaxScript> block = new ArrayList<>();
	protected String label;
	
	public SyntaxScript(final String label) {
		this.label = label;
	}
	
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		statement(scriptsBuffer);
		return block(scriptsBuffer);
	}
	
	protected char block(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				scriptsBuffer.skip();
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				block.add(subScript);
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
		block.add(subScript);
		final char c = subScript.create(scriptsBuffer);
		switch ( c ) {
			case ';' :
			case '}' :
				return scriptsBuffer.gotoNextChar();
			default :
				return c;
		}
	}
	
	protected final void statement(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		if (scriptsBuffer.getChar() == '(') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == ')') {
				scriptsBuffer.gotoNextChar();
				return;
			}
			while (scriptsBuffer.hasRemaining()) {
				statement.add(scriptsBuffer.getStatementToken());
				if (scriptsBuffer.getChar() == ')') {
					scriptsBuffer.gotoNextChar();
					return;
				}
				scriptsBuffer.gotoNextChar();
			}
		}
		throw scriptsBuffer.illegalCharacterError();
	}
}
