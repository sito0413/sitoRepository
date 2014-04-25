package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
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
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return expressionScript1.execute(scope).operation(op, expressionScript2, scope);
	}
	
	@Override
	public String printString() {
		return expressionScript1.printString() + " " + op + " " + expressionScript2.printString();
	}
}
