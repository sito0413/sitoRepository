package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class PostfixIncrementScript extends ExpressionScript {
	private final ExpressionScript expressionScript;
	
	public PostfixIncrementScript(final ExpressionScript expressionScript) {
		this.expressionScript = expressionScript;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return expressionScript.execute(scope).postfixIncrement();
	}
	
	@Override
	public String printString() {
		return expressionScript.printString() + "++";
	}
}
