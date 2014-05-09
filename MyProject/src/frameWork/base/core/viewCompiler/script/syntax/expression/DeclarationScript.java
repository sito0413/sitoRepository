package frameWork.base.core.viewCompiler.script.syntax.expression;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

public class DeclarationScript extends ExpressionScript {
	private final String type;
	private final String expressionScript;
	
	public DeclarationScript(final CallChainScript type, final CallChainScript expressionScript) {
		this.type = type.toNameString();
		this.expressionScript = expressionScript.toNameString();
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		switch ( type ) {
			case "long" :
				return scope.put(expressionScript, new InstanceBytecode(long.class, (long) 0));
			case "int" :
				return scope.put(expressionScript, new InstanceBytecode(int.class, 0));
			case "short" :
				return scope.put(expressionScript, new InstanceBytecode(short.class, (short) 0));
			case "byte" :
				return scope.put(expressionScript, new InstanceBytecode(byte.class, (byte) 0));
			case "char" :
				return scope.put(expressionScript, new InstanceBytecode(char.class, (char) 0));
			case "double" :
				return scope.put(expressionScript, new InstanceBytecode(double.class, (double) 0));
			case "float" :
				return scope.put(expressionScript, new InstanceBytecode(float.class, (float) 0));
			case "boolean" :
				return scope.put(expressionScript, new InstanceBytecode(boolean.class, false));
			case "long[]" :
				return scope.put(expressionScript, new InstanceBytecode(long[].class, (long) 0));
			case "int[]" :
				return scope.put(expressionScript, new InstanceBytecode(int[].class, null));
			case "short[]" :
				return scope.put(expressionScript, new InstanceBytecode(short[].class, null));
			case "byte[]" :
				return scope.put(expressionScript, new InstanceBytecode(byte[].class, null));
			case "char[]" :
				return scope.put(expressionScript, new InstanceBytecode(char[].class, null));
			case "double[]" :
				return scope.put(expressionScript, new InstanceBytecode(double[].class, null));
			case "float[]" :
				return scope.put(expressionScript, new InstanceBytecode(float[].class, null));
			case "boolean[]" :
				return scope.put(expressionScript, new InstanceBytecode(boolean.class, null));
			default :
				return scope.put(expressionScript, new InstanceBytecode(scope.getImport(type), null));
		}
	}
	
	@Override
	public String toNameString() {
		return type + " " + expressionScript;
	}
}
