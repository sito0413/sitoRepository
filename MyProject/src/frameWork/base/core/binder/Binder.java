package frameWork.base.core.binder;

import java.lang.reflect.Field;
import java.util.Map;

import frameWork.base.core.fileSystem.FileSystem;

public final class Binder {
	public Binder() {
		// NOP
	}
	
	public void bind(final Map<String, String> parameters, final Object obj) {
		for (final Field field : obj.getClass().getDeclaredFields()) {
			final String strValue = parameters.get(field.getName());
			if (strValue != null) {
				final Object value = getFieldValue(strValue, field.getType());
				if (value != null) {
					field.setAccessible(true);
					try {
						field.set(obj, value);
					}
					catch (final Exception e) {
						FileSystem.Log.logging(e);
					}
				}
			}
		}
	}
	
	Object getFieldValue(final String strValue, final Class<?> type) {
		try {
			if (String.class.isAssignableFrom(type)) {
				return strValue;
			}
			else if (int.class.isAssignableFrom(type)) {
				return Integer.parseInt(strValue);
			}
			else if (double.class.isAssignableFrom(type)) {
				return Double.parseDouble(strValue);
			}
			else if (boolean.class.isAssignableFrom(type)
			        && (strValue.equalsIgnoreCase("true") || strValue.equalsIgnoreCase("false"))) {
				return Boolean.parseBoolean(strValue);
			}
			else if (byte.class.isAssignableFrom(type)) {
				return Byte.parseByte(strValue);
			}
			else if (long.class.isAssignableFrom(type)) {
				return Long.parseLong(strValue);
			}
			else if (short.class.isAssignableFrom(type)) {
				return Short.parseShort(strValue);
			}
			else if (float.class.isAssignableFrom(type)) {
				return Float.parseFloat(strValue);
			}
			else if (char.class.isAssignableFrom(type) && (strValue.length() == 1)) {
				return strValue.charAt(0);
			}
			else {
				return null;
			}
		}
		catch (final Exception e) {
			return null;
		}
		
	}
}
