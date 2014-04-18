package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class BreakScript extends SyntaxScript implements Bytecode {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		scriptsBuffer.skip();
		if (scriptsBuffer.getChar() == ';') {
			return scriptsBuffer.gotoNextChar();
		}
		throw new Exception("Error " + scriptsBuffer.gotoNextChar() + " at " + scriptsBuffer.getPosition());
	}
	
	@Override
	public String toString() {
		return "break";
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return this;
	}
	
}
