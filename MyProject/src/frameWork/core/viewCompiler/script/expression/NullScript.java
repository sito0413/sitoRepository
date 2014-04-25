package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class NullScript extends ExpressionScript {
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return new ObjectBytecode(Object.class, null);
	}
	
	@Override
	public String printString() {
		return "null";
	}
}
