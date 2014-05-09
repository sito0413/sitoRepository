package frameWork.base.core.viewCompiler.script.syntax.expression;

import java.util.Deque;

import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

public class ArrayScript extends ExpressionScript {
	private final Deque<ExpressionScript> expressionScripts;
	
	public ArrayScript(final Deque<ExpressionScript> expressionScripts) {
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public String toNameString() {
		return expressionScripts.toString();
	}
	
}
