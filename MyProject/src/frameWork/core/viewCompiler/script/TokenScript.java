package frameWork.core.viewCompiler.script;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class TokenScript extends Script {
	private final String expression;
	
	public TokenScript(final String expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return expression;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
	@Override
	public boolean isDefault() {
		return "default".equals(expression);
	}
	
	@Override
	public boolean isCase() {
		return "case".equals(expression);
	}
	
	@Override
	public boolean isSyntax() {
		return "?".equals(expression);
	}
	
	@Override
	public boolean isToken() {
		return true;
	}
	
	@Override
	public boolean isBoolean() {
		return "true".equals(expression) || "false".equals(expression);
	}
}
