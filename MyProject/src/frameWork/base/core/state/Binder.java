package frameWork.base.core.state;

import java.lang.reflect.Field;

import frameWork.base.util.ThrowableUtil;

public class Binder {
	public void bind(final State state, final Object obj) {
		try {
			for (final Field field : obj.getClass().getFields()) {
				Object value = null;
				final String strValue = state.getParameter(field.getName());
				if (strValue != null) {
					try {
						if (field.getType().isAssignableFrom(String.class)) {
							value = strValue;
						}
						else if (field.getType().isAssignableFrom(int.class)) {
							value = Integer.parseInt(strValue);
						}
						else if (field.getType().isAssignableFrom(double.class)) {
							value = Double.parseDouble(strValue);
						}
						else if (field.getType().isAssignableFrom(boolean.class)) {
							value = Boolean.parseBoolean(strValue);
						}
						else if (field.getType().isAssignableFrom(byte.class)) {
							value = Byte.parseByte(strValue);
						}
						else if (field.getType().isAssignableFrom(long.class)) {
							value = Long.parseLong(strValue);
						}
						else if (field.getType().isAssignableFrom(short.class)) {
							value = Short.parseShort(strValue);
						}
						else if (field.getType().isAssignableFrom(float.class)) {
							value = Float.parseFloat(strValue);
						}
						else if (field.getType().isAssignableFrom(char.class)) {
							value = strValue.charAt(0);
						}
					}
					catch (final Exception e) {
						ThrowableUtil.throwable(e);
					}
					if (value != null) {
						field.setAccessible(true);
						field.set(obj, value);
					}
				}
			}
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
		}
	}
	
}
