package frameWork.core.viewCompiler.script.expression.callChain;

import java.lang.reflect.Method;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CallMethodScript extends CallChain {
	
	public CallMethodScript(final String expression, final List<ExpressionScript> expressionScripts) {
		super(expression, expressionScripts);
	}
	
	@Override
	public InstanceBytecode execute(final Class<?> type, final Object object, final Scope scope) throws Exception {
		final List<InstanceBytecode> bytecodes = getBytecodes(scope);
		final Class<?>[] classes = new Class<?>[bytecodes.size()];
		final Object[] objects = new Object[bytecodes.size()];
		int index = 0;
		for (final InstanceBytecode script : bytecodes) {
			classes[index] = script.type();
			objects[index] = script.get();
			index++;
		}
		final Method method = object.getClass().getMethod(expression, classes);
		method.setAccessible(true);
		return scope.create(method.getReturnType(), method.invoke(object, objects));
	}
	
	@Override
	public String printString() {
		return expression + expressionScripts;
	}
	
	@Override
	public String toNameString() {
		return expression;
	}
}
