package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.expression.BooleanScript;
import frameWork.core.viewCompiler.script.expression.StringScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;

public class DoubleBytecode implements InstanceBytecode {
	private final double value;
	
	public DoubleBytecode(final double value) {
		this.value = value;
	}
	
	@Override
	public Object get() {
		return value;
	}
	
	@Override
	public Class<?> type() {
		return Double.class;
	}
	
	@Override
	public InstanceBytecode prefixIncrement() {
		throw new IllegalStateException();
	}
	
	@Override
	public InstanceBytecode prefixDecrement() {
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
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Integer) {
			final int v = (Integer) obj;
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
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Short) {
			final short v = (Short) obj;
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
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Byte) {
			final byte v = (Byte) obj;
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
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Character) {
			final char v = (Character) obj;
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
				case "|" :
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
				case "%" :
					return new DoubleBytecode(value % v);
				default :
					throw new IllegalStateException();
			}
		}
		else if (obj instanceof Float) {
			final float v = (Float) obj;
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
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
}