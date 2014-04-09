package frameWork.core.fileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.MissingResourceException;
import java.util.Properties;

public class Config extends FileElement {
	private final Properties properties;
	
	public Config(final File root) {
		super(root, "config");
		properties = new Properties();
		try {
			final File file = new File(this, "config.xml");
			if (!file.exists()) {
				try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
					properties.storeToXML(fileOutputStream, "");
				}
			}
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.loadFromXML(fileInputStream);
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	private String get(final String key) {
		final Object object = properties.get(key);
		if (object != null) {
			return object.toString();
		}
		throw new MissingResourceException("Not Found", Config.class.getCanonicalName(), key);
	}
	
	public String getString(final String key, final String defaultValue) {
		try {
			return get(key);
		}
		catch (final MissingResourceException e) {
			return defaultValue;
		}
	}
	
	public int getInteger(final String key, final int defaultValue) {
		try {
			return Integer.parseInt(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public long getLong(final String key, final long defaultValue) {
		try {
			return Long.parseLong(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public double getDouble(final String key, final double defaultValue) {
		try {
			return Double.parseDouble(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public boolean getBoolean(final String key, final boolean defaultValue) {
		try {
			return Boolean.parseBoolean(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
}
