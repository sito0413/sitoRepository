package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.expression.BooleanScript;
import frameWork.core.viewCompiler.script.expression.CharacterScript;
import frameWork.core.viewCompiler.script.expression.StringScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ObjectBytecode implements InstanceBytecode {
	@SuppressWarnings("rawtypes")
	private final Class type;
	private final Object value;
	
	public ObjectBytecode(@SuppressWarnings("rawtypes") final Class type, final Object value) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	@Override
	public Class<?> type() {
		return type;
	}
	
	@Override
	public InstanceBytecode prefixIncrement() {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).prefixIncrement();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).prefixIncrement();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).prefixIncrement();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).prefixIncrement();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).prefixIncrement();
		}
		System.out.println(value);
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode prefixDecrement() {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).prefixDecrement();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).prefixDecrement();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).prefixDecrement();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).prefixDecrement();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).prefixDecrement();
		}
		System.out.println(value);
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode postfixIncrement() {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).postfixIncrement();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).postfixIncrement();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).postfixIncrement();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).postfixIncrement();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).postfixIncrement();
		}
		System.out.println(value);
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode postfixDecrement() {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).postfixDecrement();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).postfixDecrement();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).postfixDecrement();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).postfixDecrement();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).postfixDecrement();
		}
		System.out.println(value);
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode not() {
		if (value instanceof Boolean) {
			return new BooleanScript((Boolean) value).not();
		}
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws Exception {
		if (value instanceof Boolean) {
			return new BooleanScript((Boolean) value).condition(scope, expressionScript1, expressionScript2);
		}
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode complement() {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).complement();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).complement();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).complement();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).complement();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).complement();
		}
		else if (value instanceof Boolean) {
			return new BooleanScript((Boolean) value).complement();
		}
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws Exception {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Double) {
			return new DoubleBytecode((Double) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Float) {
			return new FloatBytecode((Float) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof String) {
			return new StringScript((String) value).operation(op, expressionScript2, scope);
		}
		else if (value instanceof Boolean) {
			return new BooleanScript((Boolean) value).operation(op, expressionScript2, scope);
		}
		switch ( op ) {
			case "==" :
			case "!=" :
				return new BooleanScript(expressionScript2.execute(scope).logic(op, value));
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
		throw new IllegalStateException();
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
}