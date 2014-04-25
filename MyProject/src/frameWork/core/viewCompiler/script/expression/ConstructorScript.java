package frameWork.core.viewCompiler.script.expression;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ConstructorScript extends ExpressionScript {
	private final List<ExpressionScript> expressionScripts;
	private final String expression;
	
	public ConstructorScript(final String expression, final List<ExpressionScript> expressionScripts) {
		this.expression = expression;
		this.expressionScripts = expressionScripts;
	}
	
	private List<InstanceBytecode> getBytecodes(final Scope scope) throws Exception {
		final List<InstanceBytecode> objectScripts = new ArrayList<>();
		for (final ExpressionScript script : expressionScripts) {
			objectScripts.add(script.execute(scope));
		}
		return objectScripts;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		final List<InstanceBytecode> bytecodes = getBytecodes(scope);
		final Class<?>[] classes = new Class<?>[bytecodes.size()];
		final Object[] objects = new Object[bytecodes.size()];
		int index = 0;
		for (final InstanceBytecode script : bytecodes) {
			classes[index] = script.type();
			objects[index] = script.get();
			index++;
		}
		final Class<?> class1 = scope.getImport(expression);
		final Constructor<?> constructor = class1.getConstructor(classes);
		constructor.setAccessible(true);
		return scope.create(class1, constructor.newInstance(objects));
	}
	
	@Override
	public String printString() {
		return "new " + expression + expressionScripts;
	}
}
