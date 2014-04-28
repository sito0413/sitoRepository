package frameWork.core.viewCompiler.script.expression.callChain;

import java.util.List;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public abstract class CallChain extends ExpressionScript {
	protected final List<ExpressionScript> expressionScripts;
	protected final String expression;
	
	public CallChain(final String expression, final List<ExpressionScript> expressionScripts) {
		this.expression = expression;
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return null;
	}
	
	public abstract InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope)
	        throws ScriptException;
	
	public abstract String toNameString();
}
