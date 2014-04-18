package frameWork.core.viewCompiler.script;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;

public class NumericScript extends Script {
	private final String expression;
	
	public NumericScript(final String expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return expression;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (expression.contains(".")) {
			return new ObjectScript(Double.class, Double.parseDouble(expression));
		}
		return new ObjectScript(Long.class, Long.parseLong(expression));
	}
	
	@Override
	public boolean isNumeric() {
		return true;
	}
}
