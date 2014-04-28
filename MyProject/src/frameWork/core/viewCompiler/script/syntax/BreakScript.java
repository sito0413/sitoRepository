package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

public class BreakScript extends SyntaxScript<Bytecode> implements Bytecode {
	String breakLabel;
	
	public BreakScript() {
		super("");
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		scriptsBuffer.skip();
		if (scriptsBuffer.getChar() != ';') {
			breakLabel = scriptsBuffer.getToken();
			scriptsBuffer.skip();
			if (scriptsBuffer.getChar() != ';') {
				throw scriptsBuffer.illegalCharacterError();
			}
		}
		return scriptsBuffer.gotoNextChar();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		return this;
	}
	
	@Override
	public String toString() {
		return "break";
	}
	
	@Override
	public boolean isBreak() {
		return true;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
	
	@Override
	public Object get() {
		return breakLabel;
	}
}
