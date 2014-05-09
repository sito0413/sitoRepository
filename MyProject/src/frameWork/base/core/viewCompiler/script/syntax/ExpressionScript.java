package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.SyntaxScript;
import frameWork.core.viewCompiler.script.syntax.expression.InstanceBytecode;

public abstract class ExpressionScript extends SyntaxScript<InstanceBytecode> {
	
	public ExpressionScript() {
		super("");
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		return scriptsBuffer.getChar();
	}
	
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws ScriptException {
		return execute(scope);
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return execute(null, null, scope);
	}
	
	public abstract String toNameString();
}
