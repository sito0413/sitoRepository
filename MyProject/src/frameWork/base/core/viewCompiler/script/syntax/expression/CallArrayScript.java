package frameWork.core.viewCompiler.script.syntax.expression;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallArrayScript extends ExpressionScript {
	private final List<ExpressionScript> expressionScripts;
	private final String expression;
	
	public CallArrayScript(final String expression) {
		this.expression = expression;
		this.expressionScripts = new ArrayList<>();
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws ScriptException {
		if (type == null) {
			final InstanceBytecode result = scope.get(null, expression + "[]");
			return result;
		}
		try {
			final Field field = type.getField(expression);
			field.setAccessible(true);
			Object arrayObject = field.get(object);
			for (final ExpressionScript expressionScript : expressionScripts) {
				arrayObject = Array.get(arrayObject, expressionScript.execute(scope).getInteger());
			}
			return new InstanceBytecode(arrayObject.getClass(), arrayObject);
		}
		catch (final Exception e) {
			throw ScriptException.IllegalStateException(e);
		}
	}
	
	public void addArray(final ExpressionScript array) {
		expressionScripts.add(array);
	}
	
	@Override
	public String toNameString() {
		return expression + expressionScripts;
	}
	
}
