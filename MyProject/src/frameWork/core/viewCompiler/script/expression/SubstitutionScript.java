package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class SubstitutionScript extends ExpressionScript {
	private final ExpressionScript expressionScript1;
	private final ExpressionScript expressionScript2;
	
	public SubstitutionScript(final ExpressionScript expressionScript1, final ExpressionScript expressionScript2) {
		this.expressionScript1 = expressionScript1;
		this.expressionScript2 = expressionScript2;
	}
	
	@Override
	public String printString() {
		return expressionScript1.printString() + " = " + expressionScript2.printString();
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return expressionScript1.execute(scope).set(scope, expressionScript2.execute(scope));
	}
}
