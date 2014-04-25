package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class ContineScript extends SyntaxScript<Bytecode> implements Bytecode {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		scriptsBuffer.skip();
		if (scriptsBuffer.getChar() == ';') {
			return scriptsBuffer.gotoNextChar();
		}
		throw scriptsBuffer.illegalCharacterError();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return this;
	}
	
	@Override
	public String toString() {
		return "contine";
	}
	
	@Override
	public void print(final int index) {
		print(index, toString());
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return true;
	}
}
