package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public interface InstanceBytecode extends Bytecode {
	
	Object get();
	
	InstanceBytecode postfixIncrement();
	
	InstanceBytecode postfixDecrement();
	
	InstanceBytecode not();
	
	InstanceBytecode condition(final Scope scope, ExpressionScript expressionScript1, ExpressionScript expressionScript2)
	        throws Exception;
	
	InstanceBytecode complement();
	
	Class<?> type();
	
	InstanceBytecode operation(String op, ExpressionScript expressionScript2, Scope scope) throws Exception;
	
	boolean logic(String op, Object value);
	
	InstanceBytecode operation(String op, Object value);
	
	InstanceBytecode set(Scope scope, InstanceBytecode execute);
}
