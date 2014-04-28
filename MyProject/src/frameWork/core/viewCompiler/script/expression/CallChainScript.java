package frameWork.core.viewCompiler.script.expression;

import java.util.Deque;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.expression.callChain.CallChain;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallChainScript extends ExpressionScript {
	private final Deque<CallChain> expressionScripts;
	
	public CallChainScript(final Deque<CallChain> expressionScripts) {
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		InstanceBytecode bytecode = null;
		for (final CallChain expressionScript : expressionScripts) {
			if (bytecode == null) {
				bytecode = expressionScript.execute(null, null, scope);
			}
			else {
				bytecode = expressionScript.execute(bytecode.type(), bytecode.get(), scope);
			}
		}
		return bytecode;
	}
	
	public String toNameString() {
		String s = "";
		for (final CallChain callChain : expressionScripts) {
			s += "." + callChain.toNameString();
		}
		if (!s.isEmpty()) {
			s = s.substring(1);
		}
		return s;
	}
}
