package frameWork.core.viewCompiler.script.expression.callChain;

import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
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
	public String printString() {
		return expression;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
	protected List<InstanceBytecode> getBytecodes(final Scope scope) throws Exception {
		final List<InstanceBytecode> objectScripts = new ArrayList<>();
		for (final ExpressionScript script : expressionScripts) {
			objectScripts.add(script.execute(scope));
		}
		return objectScripts;
	}
	
	public abstract InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope)
	        throws Exception;
	
	public abstract String toNameString();
}
