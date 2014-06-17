package frameWork.base.core.viewCompiler.script.expression;

import java.util.Deque;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.Script;
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public void callDefine(final Scope scope) throws ScriptException {
		super.callDefine(scope);
		for (final Script script : expressionScripts) {
			script.callDefine(scope);
		}
	}
}
