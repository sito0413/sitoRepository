package frameWork.base.core.viewCompiler.script.expression;

import java.lang.reflect.Field;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

public class CallObjectScript extends ExpressionScript {
	private final String expression;
	
	public CallObjectScript(final String expression) {
		this.expression = expression;
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws ScriptException {
		if (type == null) {
			final InstanceBytecode result = scope.get(null, expression);
			return result;
		}
		try {
			final Field field = type.getField(expression);
			field.setAccessible(true);
			return new InstanceBytecode(field.getType(), field.get(object));
		}
		catch (final Exception e) {
			throw ScriptException.illegalStateException(e);
		}
	}
	
	@Override
	public String toNameString() {
		return expression;
	}
}
