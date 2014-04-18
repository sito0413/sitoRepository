package frameWork.core.viewCompiler.script;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class MethodScript extends Script {
	private final List<ExpressionScript> statement;
	private final String expression;
	
	public MethodScript(final String expression, final List<ExpressionScript> statement) {
		this.statement = statement;
		this.expression = expression;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
	@Override
	public String toString() {
		return expression;
	}
	
	@Override
	public boolean isMethod() {
		return true;
	}
	
	public Script callMethod(final Object object, final Scope scope) throws Exception {
		final List<ObjectScript> objectScripts = getObjectScripts(scope);
		final Class<?>[] classes = new Class<?>[objectScripts.size()];
		final Object[] objects = new Object[objectScripts.size()];
		int index = 0;
		for (final ObjectScript script : objectScripts) {
			classes[index] = script.type;
			objects[index] = script.object;
			index++;
		}
		String methodName = expression;
		if (expression.startsWith(".")) {
			methodName = expression.substring(1);
		}
		final Method method = object.getClass().getMethod(methodName, classes);
		method.setAccessible(true);
		//TODO
		final ObjectScript objectScript = new ObjectScript(method.getReturnType(), method.invoke(object, objects));
		scope.put("_", objectScript);
		return objectScript;
	}
	
	public Script callConstructor(final Class<?> class1, final Scope scope) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	private List<ObjectScript> getObjectScripts(final Scope scope) throws Exception {
		final List<ObjectScript> objectScripts = new ArrayList<>();
		for (final Script script : statement) {
			final ObjectScript objectScript = (ObjectScript) script.execute(scope);
			if (objectScript != null) {
				objectScripts.add(objectScript);
			}
			else {
				System.out.println("@" + script);
			}
		}
		return objectScripts;
	}
	
}
