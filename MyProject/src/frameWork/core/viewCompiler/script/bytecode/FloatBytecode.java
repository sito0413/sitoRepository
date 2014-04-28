package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.expression.StringScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class FloatBytecode implements InstanceBytecode {
	private final float value;
	
	public FloatBytecode(final float value) {
		this.value = value;
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	@Override
	public Class<?> type() {
		return float.class;
	}
	
	@Override
	public InstanceBytecode postfixIncrement() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode postfixDecrement() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode not() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode condition(final Scope scope, final ExpressionScript expressionScript1,
	        final ExpressionScript expressionScript2) throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode complement() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws ScriptException {
		switch ( op ) {
			case "<" :
			case ">" :
			case "<=" :
			case ">=" :
			case "==" :
			case "!=" :
				return new ObjectBytecode(boolean.class, expressionScript2.execute(scope).logic(op, value));
			case "+" :
			case "-" :
			case "/" :
			case "*" :
			case "%" :
				return expressionScript2.execute(scope).operation(op, value);
			default :
				throw ScriptException.IllegalStateException();
		}
	}
	
	@Override
	public boolean logic(final String op, final Object obj) throws ScriptException {
		if (obj instanceof Long) {
			final long v = (Long) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Integer) {
			final int v = (Integer) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Short) {
			final short v = (Short) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Byte) {
			final byte v = (Byte) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Character) {
			final float v = (Character) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Double) {
			final double v = (Double) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Float) {
			final float v = (Float) obj;
			switch ( op ) {
				case "<" :
					return value < v;
				case ">" :
					return value > v;
				case "<=" :
					return value <= v;
				case ">=" :
					return value >= v;
				case "==" :
					return value == v;
				case "!=" :
					return value != v;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else {
			switch ( op ) {
				case "==" :
					return false;
				case "!=" :
					return true;
				default :
					throw ScriptException.IllegalStateException();
			}
		}
	}
	
	@Override
	public InstanceBytecode operation(final String op, final Object obj) throws ScriptException {
		if (obj instanceof Long) {
			final long v = (Long) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Integer) {
			final int v = (Integer) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Short) {
			final short v = (Short) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Byte) {
			final byte v = (Byte) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Character) {
			final char v = (Character) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Double) {
			final double v = (Double) obj;
			switch ( op ) {
				case "+" :
					return new DoubleBytecode(value + v);
				case "-" :
					return new DoubleBytecode(value - v);
				case "/" :
					return new DoubleBytecode(value / v);
				case "*" :
					return new DoubleBytecode(value * v);
				case "%" :
					return new DoubleBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof Float) {
			final float v = (Float) obj;
			switch ( op ) {
				case "+" :
					return new FloatBytecode(value + v);
				case "-" :
					return new FloatBytecode(value - v);
				case "/" :
					return new FloatBytecode(value / v);
				case "*" :
					return new FloatBytecode(value * v);
				case "%" :
					return new FloatBytecode(value % v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
		else if (obj instanceof String) {
			final String v = (String) obj;
			switch ( op ) {
				case "+" :
					return new StringScript(value + v);
				default :
					throw ScriptException.IllegalStateException();
			}
		}
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
	public InstanceBytecode set(final Scope scope, final InstanceBytecode execute) throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public boolean getBoolean() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
	
	@Override
	public int getInteger() throws ScriptException {
		throw ScriptException.IllegalStateException();
	}
}