package frameWork.base.core.viewCompiler.script.syntax.expression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

public class CallMethodScript extends ExpressionScript {
	private final List<ExpressionScript> expressionScripts;
	private final String expression;
	
	public CallMethodScript(final String expression, final List<ExpressionScript> expressionScripts) {
		this.expression = expression;
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws ScriptException {
		final List<InstanceBytecode> bytecodes = new ArrayList<>();
		for (final ExpressionScript script : expressionScripts) {
			bytecodes.add(script.execute(scope));
		}
		final Class<?>[] classes = new Class<?>[bytecodes.size()];
		final Object[] objects = new Object[bytecodes.size()];
		int index = 0;
		for (final InstanceBytecode script : bytecodes) {
			classes[index] = script.type();
			objects[index] = script.get();
			index++;
		}
		
		try {
			try {
				final Method method = object.getClass().getMethod(expression, classes);
				method.setAccessible(true);
				return new InstanceBytecode(method.getReturnType(), method.invoke(object, objects));
			}
			catch (final NoSuchMethodException e) {
				for (final Method method : object.getClass().getMethods()) {
					final Class<?>[] parameterTypes = method.getParameterTypes();
					if (method.getName().equals(expression) && (classes.length == parameterTypes.length)) {
						boolean flg = true;
						for (int i = 0; i < parameterTypes.length; i++) {
							if (!parameterTypes[i].isAssignableFrom(classes[i])) {
								flg = false;
								break;
							}
						}
						if (flg) {
							method.setAccessible(true);
							return new InstanceBytecode(method.getReturnType(), method.invoke(object, objects));
						}
					}
				}
				throw ScriptException.illegalStateException(e);
			}
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw ScriptException.illegalStateException(e);
		}
	}
	
	@Override
	public String toNameString() {
		return expression;
	}
}
