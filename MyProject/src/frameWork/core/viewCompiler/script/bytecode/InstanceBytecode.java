package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public interface InstanceBytecode extends Bytecode {
	
	InstanceBytecode postfixIncrement() throws ScriptException;
	
	InstanceBytecode postfixDecrement() throws ScriptException;
	
	InstanceBytecode not() throws ScriptException;
	
	InstanceBytecode condition(final Scope scope, ExpressionScript expressionScript1, ExpressionScript expressionScript2)
	        throws ScriptException;
	
	InstanceBytecode complement() throws ScriptException;
	
	Class<?> type();
	
	InstanceBytecode operation(String op, ExpressionScript expressionScript2, Scope scope) throws ScriptException;
	
	boolean logic(String op, Object value) throws ScriptException;
	
	InstanceBytecode operation(String op, Object value) throws ScriptException;
	
	InstanceBytecode set(Scope scope, InstanceBytecode execute) throws ScriptException;
	
	boolean getBoolean() throws ScriptException;
	
	int getInteger() throws ScriptException;
}
