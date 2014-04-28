package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ComplementScript extends ExpressionScript {
	private final ExpressionScript expressionScript;
	
	public ComplementScript(final ExpressionScript expressionScript) {
		this.expressionScript = expressionScript;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return expressionScript.execute(scope).complement();
	}
}
