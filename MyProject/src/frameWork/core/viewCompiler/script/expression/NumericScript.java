package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.DoubleBytecode;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.IntegerBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class NumericScript extends ExpressionScript {
	private final String expression;
	
	public NumericScript(final String expression) {
		this.expression = expression;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		if (expression.contains(".")) {
			return new DoubleBytecode(Double.parseDouble(expression));
		}
		return new IntegerBytecode(Integer.parseInt(expression));
	}
}
