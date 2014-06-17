package frameWork.base.core.viewCompiler.script;

import java.util.ArrayList;
import java.util.List;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

@SuppressWarnings("rawtypes")
public abstract class Script<T extends Bytecode> {
	public abstract T execute(Scope scope) throws ScriptException;
	
	protected final List<ExpressionScript> statement = new ArrayList<>();
	protected final List<Script> block = new ArrayList<>();
	protected String label;
	
	public Script(final String label) {
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
				final Script subScript = scriptsBuffer.getSyntaxToken();
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
		final Script subScript = scriptsBuffer.getSyntaxToken();
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
	
	public void callDefine(final Scope scope) throws ScriptException {
		for (final Script script : block) {
			script.callDefine(scope);
		}
	}
}
