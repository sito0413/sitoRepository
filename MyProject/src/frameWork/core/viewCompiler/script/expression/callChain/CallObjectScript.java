package frameWork.core.viewCompiler.script.expression.callChain;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallObjectScript extends CallChain {
	
	public CallObjectScript(final String expression) {
		super(expression, new ArrayList<ExpressionScript>());
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws Exception {
		if (type == null) {
			final InstanceBytecode result = scope.get(type, expression);
			return result;
		}
		final Field field = type.getField(expression);
		field.setAccessible(true);
		
		Object arrayObject = field.get(object);
		for (final ExpressionScript expressionScript : expressionScripts) {
			arrayObject = Array.get(arrayObject, Integer.parseInt(expressionScript.execute(scope).get().toString()));
		}
		return scope.create(type, field.get(object));
	}
	
	public void addArray(final ExpressionScript array) {
		expressionScripts.add(array);
	}
	
	@Override
	public String printString() {
		if (expressionScripts.isEmpty()) {
			return expression;
		}
		return expression + expressionScripts;
	}
	
	@Override
	public String toNameString() {
		if (expressionScripts.isEmpty()) {
			return expression;
		}
		return expression + expressionScripts;
	}
}
