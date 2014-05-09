package frameWork.base.core.viewCompiler.script.syntax.expression;

import java.util.Deque;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

public class ArrayScript extends ExpressionScript {
	private final Deque<ExpressionScript> expressionScripts;
	
	public ArrayScript(final Deque<ExpressionScript> expressionScripts) {
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		// TODO 自動生成されたメソッド・スタブ
		
		return super.execute(scope);
	}
	
	@Override
	public String toNameString() {
		return expressionScripts.toString();
	}
	
}
