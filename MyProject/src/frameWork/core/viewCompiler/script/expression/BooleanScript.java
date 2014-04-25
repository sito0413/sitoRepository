package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class BooleanScript extends ExpressionScript implements InstanceBytecode {
	private final boolean value;
	
	public BooleanScript(final boolean value) {
		this.value = value;
	}
	
	@Override
	public Class<?> type() {
		return Boolean.class;
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
	public InstanceBytecode not() {
		return new BooleanScript(!value);
	}
	
	@Override
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws Exception {
		return value ? expressionScript1.execute(scope) : expressionScript2.execute(scope);
	}
	
	@Override
	public InstanceBytecode complement() {
		throw new IllegalStateException();
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
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws Exception {
		switch ( op ) {
			case "||" :
				if (value) {
					return new BooleanScript(true);
				}
				return expressionScript2.execute(scope);
			case "&&" :
				if (!value) {
					return new BooleanScript(false);
				}
				return expressionScript2.execute(scope);
			case "|" :
			case "&" :
			case "^" :
			case "==" :
			case "!=" :
				return new BooleanScript(expressionScript2.execute(scope).logic(op, value));
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean logic(final String op, final Object v) {
		if (v instanceof Boolean) {
			final Boolean result = (Boolean) v;
			switch ( op ) {
				case "|" :
					return value | result;
				case "&" :
					return value & result;
				case "^" :
					return value ^ result;
				case "==" :
					return value == result;
				case "!=" :
					return value != result;
				default :
					break;
			}
		}
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final Object v) {
		return null;
	}
	
	@Override
	public String printString() {
		return String.valueOf(value);
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
