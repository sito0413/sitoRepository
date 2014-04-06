package config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Config {
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config",
	        new XMLResourceBundleControl());
	static {
		
	}
	
	public static String getString(final String key, final String defaultValue) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (final MissingResourceException e) {
			return defaultValue;
		}
	}
	
	public static long getLong(final String key, final long defaultValue) {
		try {
			return Long.parseLong(RESOURCE_BUNDLE.getString(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
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
	
	public static double getDouble(final String key, final double defaultValue) {
		try {
			return Double.parseDouble(RESOURCE_BUNDLE.getString(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static boolean getBoolean(final String key, final boolean defaultValue) {
		try {
			return Boolean.parseBoolean(RESOURCE_BUNDLE.getString(key));
		}
		catch (final MissingResourceException e) {
			return defaultValue;
		}
	}
}
