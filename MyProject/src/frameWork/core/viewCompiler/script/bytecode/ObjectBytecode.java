package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.expression.CharacterScript;
import frameWork.core.viewCompiler.script.expression.StringScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class ObjectBytecode implements InstanceBytecode {
	@SuppressWarnings("rawtypes")
	private Class type;
	private Object value;
	
	public ObjectBytecode(@SuppressWarnings("rawtypes") final Class type, final Object value) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	@Override
	public InstanceBytecode set(final Scope scope, final InstanceBytecode execute) {
		type = execute.type();
		value = execute.get();
		return this;
	}
	
	@Override
	public Class<?> type() {
		if (value == null) {
			return type;
		}
		return value.getClass();
	}
	
	@Override
	public InstanceBytecode postfixIncrement() throws ScriptException {
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
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode postfixDecrement() throws ScriptException {
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
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode not() throws ScriptException {
		if (value instanceof Boolean) {
			return new ObjectBytecode(boolean.class, !(Boolean) value);
		}
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws ScriptException {
		if (value instanceof Boolean) {
			return ((Boolean) value) ? expressionScript1.execute(scope) : expressionScript2.execute(scope);
		}
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode complement() throws ScriptException {
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
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws ScriptException {
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
			switch ( op ) {
				case "+" :
					return new StringScript(v + (String) value);
				default :
					throw ScriptException.IllegalStateException();
			}
			return new StringScript((String) value).logic(op, v);
			return new StringScript((String) value).operation(op, expressionScript2, scope);
		}
		switch ( op ) {
			case "||" :
				if (value instanceof Boolean) {
					if ((Boolean) value) {
						return new ObjectBytecode(boolean.class, true);
					}
					return expressionScript2.execute(scope);
				}
			case "&&" :
				if (value instanceof Boolean) {
					if (!(Boolean) value) {
						return new ObjectBytecode(boolean.class, false);
					}
					return expressionScript2.execute(scope);
				}
			case "|" :
			case "&" :
			case "^" :
			case "==" :
			case "!=" :
				return new ObjectBytecode(boolean.class, expressionScript2.execute(scope).logic(op, value));
			default :
				throw ScriptException.IllegalStateException();
		}
	}
	
	@Override
	public boolean logic(final String op, final Object v) throws ScriptException {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).logic(op, v);
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).logic(op, v);
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).logic(op, v);
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).logic(op, v);
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).logic(op, v);
		}
		else if (value instanceof Double) {
			return new DoubleBytecode((Double) value).logic(op, v);
		}
		else if (value instanceof Float) {
			return new FloatBytecode((Float) value).logic(op, v);
		}
		else if (value instanceof String) {
			return new StringScript((String) value).logic(op, v);
		}
		else if (value instanceof Boolean) {
			if (v instanceof Boolean) {
				final Boolean result = (Boolean) v;
				switch ( op ) {
					case "|" :
						return result | ((Boolean) value);
					case "&" :
						return result & ((Boolean) value);
					case "^" :
						return result ^ ((Boolean) value);
					case "==" :
						return result == ((Boolean) value);
					case "!=" :
						return result != ((Boolean) value);
					default :
						break;
				}
			}
			else {
				final Boolean result = (Boolean) v;
				switch ( op ) {
					case "==" :
						return false;
					case "!=" :
						return true;
					default :
						break;
				}
			}
		}
		else {
			switch ( op ) {
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		throw ScriptException.IllegalStateException();
		
	}
	
	@Override
	public InstanceBytecode operation(final String op, final Object v) throws ScriptException {
		throw ScriptException.IllegalStateException();
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
	public boolean getBoolean() throws ScriptException {
		if (value instanceof Boolean) {
			return ((Boolean) value);
		}
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public int getInteger() throws ScriptException {
		if (value instanceof Long) {
			return new LongBytecode((Long) value).getInteger();
		}
		else if (value instanceof Integer) {
			return new IntegerBytecode((Integer) value).getInteger();
		}
		else if (value instanceof Short) {
			return new ShortBytecode((Short) value).getInteger();
		}
		else if (value instanceof Byte) {
			return new ByteBytecode((Byte) value).getInteger();
		}
		else if (value instanceof Character) {
			return new CharacterScript((Character) value).getInteger();
		}
		throw ScriptException.IllegalStateException();
	}
}