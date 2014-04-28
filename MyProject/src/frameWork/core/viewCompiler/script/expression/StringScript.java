package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class StringScript extends ExpressionScript {
	private final String value;
	
	public StringScript(final String value) {
		this.value = value;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return new ObjectBytecode(String.class, value);
	}
}
