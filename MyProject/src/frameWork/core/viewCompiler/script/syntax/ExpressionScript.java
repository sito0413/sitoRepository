package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;

public abstract class ExpressionScript extends SyntaxScript<InstanceBytecode> {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public void print(final int index) {
		print(index, printString());
	}
	
	@Override
	public String toString() {
		return printString();
	}
	
	public abstract String printString();
}
