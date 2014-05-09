package frameWork.base.core.viewCompiler.script.syntax;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.SyntaxScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.InstanceBytecode;

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
