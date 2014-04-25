package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class StringScript extends ExpressionScript implements InstanceBytecode {
	private final String value;
	
	public StringScript(final String value) {
		this.value = value;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws Exception {
		return this;
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	@Override
	public Class<?> type() {
		return String.class;
	}
	
	@Override
	public InstanceBytecode postfixIncrement() {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode postfixDecrement() {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode not() {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws Exception {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode complement() {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws Exception {
		switch ( op ) {
			case "==" :
			case "!=" :
				return new BooleanScript(expressionScript2.execute(scope).logic(op, value));
			case "+" :
				return expressionScript2.execute(scope).operation(op, value);
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean logic(final String op, final Object v) {
		switch ( op ) {
			case "==" :
				return value == v;
			case "!=" :
				return value != v;
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	public InstanceBytecode operation(final String op, final Object v) {
		switch ( op ) {
			case "+" :
				return new StringScript(value + v);
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	public String printString() {
		return value;
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
	
	@Override
	public InstanceBytecode set(final Scope scope, final InstanceBytecode execute) {
		throw new IllegalStateException();
	}
}
