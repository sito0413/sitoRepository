package frameWork.base.database.scheme;

public class Field<T> {
	
	private final String name;
	private final boolean isKey;
	private final Type type;
	private final boolean isUnique;
	private final boolean isNullable;
	private final T defaultValue;
	private T value = null;
	
	public Field(final String name, final boolean isKey, final Type type, final boolean isUnique,
	        final boolean isNullable, final String defaultValue) {
		this.name = name;
		this.isKey = isKey;
		this.type = type;
		this.isUnique = isUnique;
		this.isNullable = isNullable;
		this.defaultValue = (T) type.parseValue(defaultValue);
	}
	
	public String getName() {
		return name;
	}
	
	public T get() {
		return value;
	}
	
	public void set(final T v) {
		value = v;
	}
	
	public String toAssignment() {
		return name + " = " + type.parseSQL(value);
	}
	
	public String toColumnDefine() {
		return name + " " + type.toString() + " " + (isKey ? "PRIMARY_KEY  AUTOINCREMENT " : "")
		        + (isUnique ? "UNIQUE " : "") + (isNullable ? "" : "NOT NULL ")
		        + (defaultValue == null ? "" : ("DEFAULT " + type.parseSQL(value) + " "));
	}
	
	public String getSqlValue() {
		return type.parseSQL(value);
	}
}