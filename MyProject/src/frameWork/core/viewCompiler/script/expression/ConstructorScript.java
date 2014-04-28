package frameWork.core.viewCompiler.script.expression;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ConstructorScript extends ExpressionScript {
	private final List<ExpressionScript> expressionScripts;
	private final String expression;
	
	public ConstructorScript(final String expression, final List<ExpressionScript> expressionScripts) {
		this.expression = expression;
		this.expressionScripts = expressionScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
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
		final Class<?> class1 = scope.getImport(expression);
		Constructor<?> constructor;
		try {
			constructor = class1.getConstructor(classes);
			constructor.setAccessible(true);
			return new ObjectBytecode(class1, constructor.newInstance(objects));
		}
		catch (final Exception e) {
			throw ScriptException.IllegalStateException(e);
		}
	}
}
