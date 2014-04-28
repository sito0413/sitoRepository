package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class NullScript extends ExpressionScript {
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return new ObjectBytecode(Object.class, null);
	}
}
