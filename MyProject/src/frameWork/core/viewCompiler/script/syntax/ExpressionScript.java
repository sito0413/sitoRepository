package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;

public abstract class ExpressionScript extends SyntaxScript<InstanceBytecode> {
	public ExpressionScript() {
		super("");
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		return scriptsBuffer.getChar();
	}
}
