package frameWork.core.viewCompiler.script.syntax.expression;

import java.lang.reflect.Array;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ArrayConstructorScript extends ConstructorScript {
	private final ExpressionScript index;
	
	public ArrayConstructorScript(final String expression, final ExpressionScript index,
	        final List<ExpressionScript> expressionScripts) {
		super(expression, expressionScripts);
		this.index = index;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		if (expressionScripts.size() == 0) {
			
		}
		final Class<?> class1 = scope.getImport(expression + "[]");
		try {
			return new InstanceBytecode(class1, Array.newInstance(class1, index.execute(scope).getInteger()));
		}
		catch (final Exception e) {
			throw ScriptException.IllegalStateException(e);
		}
	}
	
	@Override
	public String toNameString() {
		return "new " + expression + "[" + index + "]" + expressionScripts;
		
	}
	
}
