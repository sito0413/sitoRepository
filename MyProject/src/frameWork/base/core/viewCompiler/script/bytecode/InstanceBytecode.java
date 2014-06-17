package frameWork.base.core.viewCompiler.script.bytecode;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;

@SuppressWarnings("rawtypes")
public class InstanceBytecode extends ExpressionScript implements Bytecode {
	public static final InstanceBytecode ONE = new InstanceBytecode(int.class, 1);
	public static final InstanceBytecode ZERO = new InstanceBytecode(int.class, 0);
	private Class type;
	private Object value;
	
	public InstanceBytecode(final Class type, final Object value) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public InstanceBytecode execute(final Scope scope) throws ScriptException {
		return this;
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	public Class<?> type() {
		if (value == null) {
			return type;
		}
		else if (value instanceof Long) {
			return long.class;
		}
		else if (value instanceof Integer) {
			return int.class;
		}
		else if (value instanceof Short) {
			return short.class;
		}
		else if (value instanceof Byte) {
			return byte.class;
		}
		else if (value instanceof Character) {
			return char.class;
		}
		else if (value instanceof Double) {
			return double.class;
		}
		else if (value instanceof Float) {
			return float.class;
		}
		else if (value instanceof Boolean) {
			return boolean.class;
		}
		return value.getClass();
	}
	
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws ScriptException {
		if (value instanceof Boolean) {
			return ((Boolean) value) ? expressionScript1.execute(scope) : expressionScript2.execute(scope);
		}
		throw ScriptException.illegalStateException();
	}
	
	public InstanceBytecode operation(final String op) throws ScriptException {
		switch ( op ) {
			case "!" :
				if (value instanceof Boolean) {
					return new InstanceBytecode(boolean.class, !(Boolean) value);
				}
				break;
			case "~" :
				if (value instanceof Long) {
					return new InstanceBytecode(long.class, ~(Long) value);
				}
				else if (value instanceof Integer) {
					return new InstanceBytecode(int.class, ~(Integer) value);
				}
				else if (value instanceof Short) {
					return new InstanceBytecode(int.class, ~(Short) value);
				}
				else if (value instanceof Byte) {
					return new InstanceBytecode(int.class, ~(Byte) value);
				}
				else if (value instanceof Character) {
					return new InstanceBytecode(int.class, ~(Character) value);
				}
				break;
			case "++" :
				if (value instanceof Long) {
					final InstanceBytecode bytecode = new InstanceBytecode(long.class, value);
					value = ((Long) value) + 1;
					return bytecode;
				}
				else if (value instanceof Integer) {
					final InstanceBytecode bytecode = new InstanceBytecode(int.class, value);
					value = ((Integer) value) + 1;
					return bytecode;
				}
				else if (value instanceof Short) {
					final InstanceBytecode bytecode = new InstanceBytecode(short.class, value);
					value = ((Short) value) + 1;
					return bytecode;
				}
				else if (value instanceof Byte) {
					final InstanceBytecode bytecode = new InstanceBytecode(byte.class, value);
					value = ((Byte) value) + 1;
					return bytecode;
				}
				else if (value instanceof Character) {
					final InstanceBytecode bytecode = new InstanceBytecode(char.class, value);
					value = ((Character) value) - 1;
					return bytecode;
				}
				break;
			case "--" :
				if (value instanceof Long) {
					final InstanceBytecode bytecode = new InstanceBytecode(long.class, value);
					value = ((Long) value) - 1;
					return bytecode;
				}
				else if (value instanceof Integer) {
					final InstanceBytecode bytecode = new InstanceBytecode(int.class, value);
					value = ((Integer) value) - 1;
					return bytecode;
				}
				else if (value instanceof Short) {
					final InstanceBytecode bytecode = new InstanceBytecode(short.class, value);
					value = ((Short) value) - 1;
					return bytecode;
				}
				else if (value instanceof Byte) {
					final InstanceBytecode bytecode = new InstanceBytecode(byte.class, value);
					value = ((Byte) value) - 1;
					return bytecode;
				}
				else if (value instanceof Character) {
					final InstanceBytecode bytecode = new InstanceBytecode(char.class, value);
					value = ((Character) value) - 1;
					return bytecode;
				}
				break;
		}
		throw ScriptException.illegalStateException();
	}
	
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws ScriptException {
		switch ( op ) {
			case "=" :
				final InstanceBytecode bytecode = expressionScript2.execute(scope);
				type = bytecode.type();
				value = bytecode.get();
				return bytecode;
				
			case "||" :
				if (value instanceof Boolean) {
					if ((Boolean) value) {
						return new InstanceBytecode(boolean.class, true);
					}
					return expressionScript2.execute(scope);
				}
				break;
			case "&&" :
				if (value instanceof Boolean) {
					if (!(Boolean) value) {
						return new InstanceBytecode(boolean.class, false);
					}
					return expressionScript2.execute(scope);
				}
				break;
			
			case "<" :
			case ">" :
			case "<=" :
			case ">=" :
			case "==" :
			case "!=" :
				return new InstanceBytecode(boolean.class, expressionScript2.execute(scope).logic(op, value));
				
			case "|" :
			case "&" :
			case "^" :
			case "+" :
			case "-" :
			case "/" :
			case "*" :
			case "%" :
			case "<<" :
			case ">>" :
			case ">>>" :
				return expressionScript2.execute(scope).operation(op, value);
			default :
				break;
		}
		throw ScriptException.illegalStateException();
	}
	
	public boolean logic(final String op, final Object obj) throws ScriptException {
		if (value instanceof Long) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Long) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Long) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Long) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Long) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Long) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Long) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Long) value);
			}
		}
		else if (value instanceof Integer) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Integer) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Integer) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Integer) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Integer) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Integer) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Integer) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Integer) value);
			}
		}
		else if (value instanceof Short) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Short) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Short) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Short) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Short) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Short) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Short) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Short) value);
			}
		}
		else if (value instanceof Byte) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Byte) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Byte) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Byte) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Byte) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Byte) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Byte) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Byte) value);
			}
		}
		else if (value instanceof Character) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Character) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Character) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Character) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Character) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Character) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Character) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Character) value);
			}
		}
		else if (value instanceof Double) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Double) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Double) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Double) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Double) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Double) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Double) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Double) value);
			}
		}
		else if (value instanceof Float) {
			if (obj instanceof Long) {
				return logic(op, (Long) obj, (Float) value);
			}
			else if (obj instanceof Integer) {
				return logic(op, (Integer) obj, (Float) value);
			}
			else if (obj instanceof Short) {
				return logic(op, (Short) obj, (Float) value);
			}
			else if (obj instanceof Byte) {
				return logic(op, (Byte) obj, (Float) value);
			}
			else if (obj instanceof Character) {
				return logic(op, (Character) obj, (Float) value);
			}
			else if (obj instanceof Double) {
				return logic(op, (Double) obj, (Float) value);
			}
			else if (obj instanceof Float) {
				return logic(op, (Float) obj, (Float) value);
			}
		}
		else if (value instanceof Boolean) {
			if (obj instanceof Boolean) {
				return logic(op, (Boolean) obj, (Boolean) value);
			}
		}
		//NOT ELSE
		{
			final Object value1 = obj;
			final Object value2 = value;
			switch ( op ) {
				case "==" :
					return value1 == value2;
				case "!=" :
					return value1 != value2;
				default :
					throw ScriptException.illegalStateException();
			}
		}
	}
	
	private boolean logic(final String op, final boolean value1, final boolean value2) throws ScriptException {
		switch ( op ) {
			case "==" :
				return value1 == value2;
			case "!=" :
				return value1 != value2;
			default :
				throw ScriptException.illegalStateException();
		}
		
	}
	
	private boolean logic(final String op, final double value1, final double value2) throws ScriptException {
		switch ( op ) {
			case "<" :
				return value1 < value2;
			case ">" :
				return value1 > value2;
			case "<=" :
				return value1 <= value2;
			case ">=" :
				return value1 >= value2;
			case "==" :
				return value1 == value2;
			case "!=" :
				return value1 != value2;
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private boolean logic(final String op, final long value1, final long value2) throws ScriptException {
		switch ( op ) {
			case "<" :
				return value1 < value2;
			case ">" :
				return value1 > value2;
			case "<=" :
				return value1 <= value2;
			case ">=" :
				return value1 >= value2;
			case "==" :
				return value1 == value2;
			case "!=" :
				return value1 != value2;
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	public InstanceBytecode operation(final String op, final Object obj) throws ScriptException {
		if (value instanceof Long) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Long) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Long) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Long) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Long) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Long) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Long) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Long) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Long) value);
			}
		}
		else if (value instanceof Integer) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Integer) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Integer) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Integer) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Integer) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Integer) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Integer) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Integer) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Integer) value);
			}
		}
		else if (value instanceof Short) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Short) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Short) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Short) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Short) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Short) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Short) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Short) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Short) value);
			}
		}
		else if (value instanceof Byte) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Byte) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Byte) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Byte) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Byte) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Byte) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Byte) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Byte) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Byte) value);
			}
		}
		else if (value instanceof Character) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Character) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Character) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Character) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Character) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Character) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Character) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Character) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Character) value);
			}
		}
		else if (value instanceof Double) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Double) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Double) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Double) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Double) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Double) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Double) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Double) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Double) value);
			}
		}
		else if (value instanceof Float) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (Float) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (Float) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (Float) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (Float) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (Float) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (Float) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (Float) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Float) value);
			}
		}
		else if (value instanceof Boolean) {
			if (obj instanceof Boolean) {
				return operation(op, (Boolean) obj, (Boolean) value);
			}
			else if (obj instanceof String) {
				return operation(op, (String) obj, (Boolean) value);
			}
		}
		else if (value instanceof String) {
			if (obj instanceof Long) {
				return operation(op, (Long) obj, (String) value);
			}
			else if (obj instanceof Integer) {
				return operation(op, (Integer) obj, (String) value);
			}
			else if (obj instanceof Short) {
				return operation(op, (Short) obj, (String) value);
			}
			else if (obj instanceof Byte) {
				return operation(op, (Byte) obj, (String) value);
			}
			else if (obj instanceof Character) {
				return operation(op, (Character) obj, (String) value);
			}
			else if (obj instanceof Double) {
				return operation(op, (Double) obj, (String) value);
			}
			else if (obj instanceof Float) {
				return operation(op, (Float) obj, (String) value);
			}
			else {
				return operation(op, obj, (String) value);
			}
		}
		else {
			if (obj instanceof String) {
				return operation(op, (String) obj, value);
			}
		}
		throw ScriptException.illegalStateException();
	}
	
	private InstanceBytecode operation(final String op, final String value1, final Object value2)
	        throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(String.class, value1 + value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final Object value1, final String value2)
	        throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(String.class, value1 + value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final boolean value1, final boolean value2)
	        throws ScriptException {
		switch ( op ) {
			case "|" :
				return new InstanceBytecode(boolean.class, value1 | value2);
			case "&" :
				return new InstanceBytecode(boolean.class, value1 & value2);
			case "^" :
				return new InstanceBytecode(boolean.class, value1 ^ value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final float value1, final float value2) throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(float.class, value1 + value2);
			case "-" :
				return new InstanceBytecode(float.class, value1 - value2);
			case "/" :
				return new InstanceBytecode(float.class, value1 / value2);
			case "*" :
				return new InstanceBytecode(float.class, value1 * value2);
			case "%" :
				return new InstanceBytecode(float.class, value1 % value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final double value1, final double value2)
	        throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(double.class, value1 + value2);
			case "-" :
				return new InstanceBytecode(double.class, value1 - value2);
			case "/" :
				return new InstanceBytecode(double.class, value1 / value2);
			case "*" :
				return new InstanceBytecode(double.class, value1 * value2);
			case "%" :
				return new InstanceBytecode(double.class, value1 % value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final int value1, final int value2) throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(int.class, value1 + value2);
			case "-" :
				return new InstanceBytecode(int.class, value1 - value2);
			case "/" :
				return new InstanceBytecode(int.class, value1 / value2);
			case "*" :
				return new InstanceBytecode(int.class, value1 * value2);
			case "%" :
				return new InstanceBytecode(int.class, value1 % value2);
			case "|" :
				return new InstanceBytecode(int.class, value1 | value2);
			case "&" :
				return new InstanceBytecode(int.class, value1 & value2);
			case "^" :
				return new InstanceBytecode(int.class, value1 ^ value2);
			case "<<" :
				return new InstanceBytecode(int.class, value1 << value2);
			case ">>" :
				return new InstanceBytecode(int.class, value1 >> value2);
			case ">>>" :
				return new InstanceBytecode(int.class, value1 >>> value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	private InstanceBytecode operation(final String op, final long value1, final long value2) throws ScriptException {
		switch ( op ) {
			case "+" :
				return new InstanceBytecode(long.class, value1 + value2);
			case "-" :
				return new InstanceBytecode(long.class, value1 - value2);
			case "/" :
				return new InstanceBytecode(long.class, value1 / value2);
			case "*" :
				return new InstanceBytecode(long.class, value1 * value2);
			case "%" :
				return new InstanceBytecode(long.class, value1 % value2);
			case "|" :
				return new InstanceBytecode(long.class, value1 | value2);
			case "&" :
				return new InstanceBytecode(long.class, value1 & value2);
			case "^" :
				return new InstanceBytecode(long.class, value1 ^ value2);
			case "<<" :
				return new InstanceBytecode(long.class, value1 << value2);
			case ">>" :
				return new InstanceBytecode(long.class, value1 >> value2);
			case ">>>" :
				return new InstanceBytecode(long.class, value1 >>> value2);
			default :
				throw ScriptException.illegalStateException();
		}
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
	
	public boolean getBoolean() throws ScriptException {
		if (value instanceof Boolean) {
			return ((Boolean) value);
		}
		throw ScriptException.illegalStateException();
	}
	
	public int getInteger() throws ScriptException {
		if (value instanceof Integer) {
			return (Integer) value;
		}
		else if (value instanceof Short) {
			return (Short) value;
		}
		else if (value instanceof Byte) {
			return (Byte) value;
		}
		else if (value instanceof Character) {
			return (Character) value;
		}
		throw ScriptException.illegalStateException();
	}
	
	@Override
	public String toNameString() {
		return String.valueOf(value);
	}
}