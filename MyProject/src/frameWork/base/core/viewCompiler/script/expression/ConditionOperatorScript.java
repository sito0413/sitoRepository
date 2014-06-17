package frameWork.base.core.viewCompiler.script.expression;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

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
	
	@Override
	public String toNameString() {
		return logic.toNameString() + "?" + expressionScript1.toNameString() + ":" + expressionScript2.toNameString();
	}
	
	@Override
	public void callDefine(final Scope scope) throws ScriptException {
		super.callDefine(scope);
		if (logic != null) {
			logic.callDefine(scope);
		}
		if (expressionScript1 != null) {
			expressionScript1.callDefine(scope);
		}
		if (expressionScript2 != null) {
			expressionScript2.callDefine(scope);
		}
	}
}
