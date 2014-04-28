package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

public class ContineScript extends SyntaxScript<Bytecode> implements Bytecode {
	String contineLabel;
	
	public ContineScript() {
		super("");
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		scriptsBuffer.skip();
		if (scriptsBuffer.getChar() != ';') {
			contineLabel = scriptsBuffer.getToken();
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
		return "contine";
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return true;
	}
	
	@Override
	public Object get() {
		return contineLabel;
	}
}
