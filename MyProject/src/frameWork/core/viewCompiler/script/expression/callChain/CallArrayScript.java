package frameWork.core.viewCompiler.script.expression.callChain;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallArrayScript extends CallChain {
	public CallArrayScript(final String expression) {
		super(expression, new ArrayList<ExpressionScript>());
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
			Object arrayObject = field.get(object);
			for (final ExpressionScript expressionScript : expressionScripts) {
				arrayObject = Array.get(arrayObject, expressionScript.execute(scope).getInteger());
			}
			return new ObjectBytecode(arrayObject.getClass(), arrayObject);
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
