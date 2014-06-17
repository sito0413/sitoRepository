package frameWork.base.core.viewCompiler.script.expression;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

@SuppressWarnings("rawtypes")
public class CastScript extends ExpressionScript {
	private final ExpressionScript expressionScript1;
	private final ExpressionScript expressionScript2;
	
	public CastScript(final ExpressionScript expressionScript1, final ExpressionScript expressionScript2) {
		this.expressionScript1 = expressionScript1;
		this.expressionScript2 = expressionScript2;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		final Class type = expressionScript1.execute(scope).type();
		final Object value = expressionScript2.execute(scope).get();
		return new InstanceBytecode(type, value);
	}
	
	@Override
	public String toNameString() {
		return "(" + expressionScript1.toNameString() + ")" + expressionScript2.toNameString();
	}
	
	@Override
	public void callDefine(final Scope scope) throws ScriptException {
		super.callDefine(scope);
		if (expressionScript1 != null) {
			expressionScript1.callDefine(scope);
		}
		if (expressionScript2 != null) {
			expressionScript2.callDefine(scope);
		}
	}
}