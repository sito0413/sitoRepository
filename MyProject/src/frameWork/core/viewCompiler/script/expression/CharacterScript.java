package frameWork.core.viewCompiler.script.expression;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.DoubleBytecode;
import frameWork.core.viewCompiler.script.bytecode.FloatBytecode;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.IntegerBytecode;
import frameWork.core.viewCompiler.script.bytecode.LongBytecode;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class CharacterScript extends ExpressionScript implements InstanceBytecode {
	private char value;
	
	public CharacterScript(final char value) {
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
	public IntegerBytecode postfixIncrement() {
		final IntegerBytecode bytecode = new IntegerBytecode(value);
		value++;
		return bytecode;
	}
	
	@Override
	public IntegerBytecode postfixDecrement() {
		final IntegerBytecode bytecode = new IntegerBytecode(value);
		value--;
		return bytecode;
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
		return new IntegerBytecode(~value);
	}
	
	@Override
	public Class<?> type() {
		return Integer.class;
	}
	
	@Override
	public InstanceBytecode operation(final String op, final ExpressionScript expressionScript2, final Scope scope)
	        throws Exception {
		switch ( op ) {
			case "<" :
			case ">" :
			case "<=" :
			case ">=" :
			case "==" :
			case "!=" :
				return new BooleanScript(expressionScript2.execute(scope).logic(op, value));
			case "+" :
			case "-" :
			case "/" :
			case "*" :
			case "%" :
			case "|" :
			case "&" :
			case "^" :
			case "<<" :
			case ">>" :
			case ">>>" :
				return expressionScript2.execute(scope).operation(op, value);
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean logic(final String op, final Object obj) {
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
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
					throw new IllegalStateException();
			}
		}
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode operation(final String op, final Object obj) {
		if (obj instanceof Long) {
			final long v = (Long) obj;
			switch ( op ) {
				case "+" :
					return new LongBytecode(value + v);
				case "-" :
					return new LongBytecode(value - v);
				case "/" :
					return new LongBytecode(value / v);
				case "*" :
					return new LongBytecode(value * v);
				case "%" :
					return new LongBytecode(value % v);
				case "|" :
					return new LongBytecode(value | v);
				case "&" :
					return new LongBytecode(value & v);
				case "^" :
					return new LongBytecode(value ^ v);
				case "<<" :
					return new LongBytecode(value << v);
				case ">>" :
					return new LongBytecode(value >> v);
				case ">>>" :
					return new LongBytecode(value >>> v);
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Integer) {
			final int v = (Integer) obj;
			switch ( op ) {
				case "+" :
					return new IntegerBytecode(value + v);
				case "-" :
					return new IntegerBytecode(value - v);
				case "/" :
					return new IntegerBytecode(value / v);
				case "*" :
					return new IntegerBytecode(value * v);
				case "%" :
					return new IntegerBytecode(value % v);
				case "|" :
					return new IntegerBytecode(value | v);
				case "&" :
					return new IntegerBytecode(value & v);
				case "^" :
					return new IntegerBytecode(value ^ v);
				case "<<" :
					return new IntegerBytecode(value << v);
				case ">>" :
					return new IntegerBytecode(value >> v);
				case ">>>" :
					return new IntegerBytecode(value >>> v);
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Short) {
			final short v = (Short) obj;
			switch ( op ) {
				case "+" :
					return new IntegerBytecode(value + v);
				case "-" :
					return new IntegerBytecode(value - v);
				case "/" :
					return new IntegerBytecode(value / v);
				case "*" :
					return new IntegerBytecode(value * v);
				case "%" :
					return new IntegerBytecode(value % v);
				case "|" :
					return new IntegerBytecode(value | v);
				case "&" :
					return new IntegerBytecode(value & v);
				case "^" :
					return new IntegerBytecode(value ^ v);
				case "<<" :
					return new IntegerBytecode(value << v);
				case ">>" :
					return new IntegerBytecode(value >> v);
				case ">>>" :
					return new IntegerBytecode(value >>> v);
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Byte) {
			final byte v = (Byte) obj;
			switch ( op ) {
				case "+" :
					return new IntegerBytecode(value + v);
				case "-" :
					return new IntegerBytecode(value - v);
				case "/" :
					return new IntegerBytecode(value / v);
				case "*" :
					return new IntegerBytecode(value * v);
				case "%" :
					return new IntegerBytecode(value % v);
				case "|" :
					return new IntegerBytecode(value | v);
				case "&" :
					return new IntegerBytecode(value & v);
				case "^" :
					return new IntegerBytecode(value ^ v);
				case "<<" :
					return new IntegerBytecode(value << v);
				case ">>" :
					return new IntegerBytecode(value >> v);
				case ">>>" :
					return new IntegerBytecode(value >>> v);
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Character) {
			final char v = (Character) obj;
			switch ( op ) {
				case "+" :
					return new IntegerBytecode(value + v);
				case "-" :
					return new IntegerBytecode(value - v);
				case "/" :
					return new IntegerBytecode(value / v);
				case "*" :
					return new IntegerBytecode(value * v);
				case "%" :
					return new IntegerBytecode(value % v);
				case "|" :
					return new IntegerBytecode(value | v);
				case "&" :
					return new IntegerBytecode(value & v);
				case "^" :
					return new IntegerBytecode(value ^ v);
				case "<<" :
					return new IntegerBytecode(value << v);
				case ">>" :
					return new IntegerBytecode(value >> v);
				case ">>>" :
					return new IntegerBytecode(value >>> v);
				default :
					throw new IllegalStateException();
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
				default :
					throw new IllegalStateException();
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
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof String) {
			final String v = (String) obj;
			switch ( op ) {
				case "+" :
					return new StringScript(value + v);
			}
		}
		throw new IllegalStateException();
	}
	
	@Override
	public String printString() {
		return Character.toString(value);
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
