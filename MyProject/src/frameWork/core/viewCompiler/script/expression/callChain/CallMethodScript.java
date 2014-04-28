package frameWork.core.viewCompiler.script.expression.callChain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallMethodScript extends CallChain {
	
	public CallMethodScript(final String expression, final List<ExpressionScript> expressionScripts) {
		super(expression, expressionScripts);
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
			final Method method = object.getClass().getMethod(expression, classes);
			method.setAccessible(true);
			return new ObjectBytecode(method.getReturnType(), method.invoke(object, objects));
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			throw ScriptException.IllegalStateException(e);
		}
		catch (final InvocationTargetException e) {
			throw ScriptException.IllegalStateException(e.getCause());
		}
	}
	
	@Override
	public String toNameString() {
		return expression;
	}
}
