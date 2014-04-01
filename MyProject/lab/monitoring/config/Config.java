package monitoring.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Config {
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("monitoring.config.config",
	        new XMLResourceBundleControl());
	
	public static String getString(final String key, final String defaultValue) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (final MissingResourceException e) {
			return defaultValue;
		}
	}
	
	public static int getInteger(final String key, final int defaultValue) {
		try {
			return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static boolean getBoolean(final String key, final boolean defaultValue) {
		try {
			return Boolean.parseBoolean(RESOURCE_BUNDLE.getString(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
}
