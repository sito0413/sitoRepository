package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ConditionOperatorScript extends ExpressionScript {
	private final ExpressionScript logic;
	private final ExpressionScript expressionScript1;
	private final ExpressionScript expressionScript2;
	
	public ConditionOperatorScript(final ExpressionScript logic, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) {
		this.logic = logic;
		this.expressionScript1 = expressionScript1;
		this.expressionScript2 = expressionScript2;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return logic.execute(scope).condition(scope, expressionScript1, expressionScript2);
	}
}
