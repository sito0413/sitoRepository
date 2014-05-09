package frameWork.base.database.scheme;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum Type {
	TEXT("String"), INTEGER("BigInteger"), DOUBLE("BigDecimal"), ;
	public static Type parse(final String type) {
		if (Type.TEXT.toString().equals(type)) {
			return TEXT;
		}
		if (Type.INTEGER.toString().equals(type)) {
			return INTEGER;
		}
		if (Type.DOUBLE.toString().equals(type)) {
			return DOUBLE;
		}
		return TEXT;
	}
	
	private String type;
	
	private Type(final String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public Object parseValue(final String defaultValue) {
		if (defaultValue == null) {
			return null;
		}
		try {
			switch ( this ) {
				case TEXT :
					return defaultValue;
				case INTEGER :
					return new BigInteger(defaultValue);
				case DOUBLE :
					return new BigDecimal(defaultValue);
			}
		}
		catch (final Exception e) {
			return null;
		}
		return null;
	}
	
	public String parseSQL(final Object value) {
		if (value == null) {
			return "null";
		}
		switch ( this ) {
			case TEXT :
				return "'" + value.toString().replace("'", "''") + "'";
			case INTEGER :
			case DOUBLE :
				return value.toString();
		}
		return "null";
	}
}
