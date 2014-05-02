package frameWork.core.viewCompiler.script.syntax.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class OperatorScript extends ExpressionScript {
	private final String op;
	private final ExpressionScript expressionScript1;
	private final ExpressionScript expressionScript2;
	
	public OperatorScript(final String op, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) {
		this.op = op;
		this.expressionScript1 = expressionScript1;
		this.expressionScript2 = expressionScript2;
	}
	
	public OperatorScript(final String op, final ExpressionScript expressionScript1) {
		this.op = op;
		this.expressionScript1 = expressionScript1;
		this.expressionScript2 = null;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		if (expressionScript2 == null) {
			return expressionScript1.execute(scope).operation(op);
		}
		return expressionScript1.execute(scope).operation(op, expressionScript2, scope);
	}
	
	@Override
	public String toNameString() {
		return expressionScript1.toNameString() + " " + op
		        + ((expressionScript2 == null) ? "" : (" " + expressionScript2.toNameString()));
	}
}
