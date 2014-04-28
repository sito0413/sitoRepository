package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.bytecode.ByteBytecode;
import frameWork.core.viewCompiler.script.bytecode.DoubleBytecode;
import frameWork.core.viewCompiler.script.bytecode.FloatBytecode;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.IntegerBytecode;
import frameWork.core.viewCompiler.script.bytecode.LongBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.bytecode.ShortBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

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
				return scope.put(expressionScript, new LongBytecode(0));
			case "int" :
				return scope.put(expressionScript, new IntegerBytecode(0));
			case "short" :
				return scope.put(expressionScript, new ShortBytecode((short) 0));
			case "byte" :
				return scope.put(expressionScript, new ByteBytecode((byte) 0));
			case "char" :
				return scope.put(expressionScript, new CharacterScript((char) 0));
			case "double" :
				return scope.put(expressionScript, new DoubleBytecode(0));
			case "float" :
				return scope.put(expressionScript, new FloatBytecode(0));
			case "boolean" :
				return scope.put(expressionScript, new ObjectBytecode(boolean.class, false));
			default :
				return scope.put(expressionScript, new ObjectBytecode(scope.getImport(type), null));
		}
	}
}
