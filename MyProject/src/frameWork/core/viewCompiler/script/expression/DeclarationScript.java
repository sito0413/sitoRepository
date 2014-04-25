package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class DeclarationScript extends ExpressionScript {
	private final String type;
	private final String expressionScript;
	
	public DeclarationScript(final CallChainScript type, final CallChainScript expressionScript) {
		this.type = type.toNameString();
		this.expressionScript = expressionScript.toNameString();
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return scope.declaration(type, expressionScript);
	}
	
	@Override
	public String printString() {
		return type + " " + expressionScript;
	}
}
