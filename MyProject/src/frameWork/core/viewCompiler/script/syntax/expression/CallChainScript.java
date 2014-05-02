package frameWork.core.viewCompiler.script.syntax.expression;

import java.util.Deque;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallChainScript extends ExpressionScript {
	private final Deque<ExpressionScript> expressionScripts;
	
	public CallChainScript(final Deque<ExpressionScript> expressionScripts) {
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		InstanceBytecode bytecode = null;
		for (final ExpressionScript expressionScript : expressionScripts) {
			if (bytecode == null) {
				bytecode = expressionScript.execute(null, null, scope);
			}
			else {
				bytecode = expressionScript.execute(bytecode.type(), bytecode.get(), scope);
			}
		}
		return bytecode;
	}
	
	@Override
	public String toNameString() {
		String s = "";
		for (final ExpressionScript callChain : expressionScripts) {
			s += "." + callChain.toNameString();
		}
		if (!s.isEmpty()) {
			s = s.substring(1);
		}
		return s;
	}
}