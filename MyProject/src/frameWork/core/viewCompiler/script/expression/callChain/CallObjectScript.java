package frameWork.core.viewCompiler.script.expression.callChain;

import java.lang.reflect.Field;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;

public class CallObjectScript extends CallChain {
	public CallObjectScript(final String expression) {
		super(expression, null);
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws ScriptException {
		if (type == null) {
			final InstanceBytecode result = scope.get(type, expression);
			return result;
		}
		try {
			final Field field = type.getField(expression);
			field.setAccessible(true);
			return new ObjectBytecode(field.getType(), field.get(object));
		}
		catch (final Exception e) {
			throw ScriptException.IllegalStateException(e);
		}
	}
	
	@Override
	public String toNameString() {
		return expression;
	}
}
