package frameWork.base.core.fileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.MissingResourceException;
import java.util.Properties;

import frameWork.base.core.authority.Role;

public class Config extends FileElement {
	private final Properties properties;
	public final String PACKAGE_NAME;
	public final String DATABASE_DRIVER_CLASS;
	public final String DATABASE_URL;
	public final int MAX_UPLOADFILE_SIZE;
	public final String VIEW_CHAREET;
	public final int VIEW_SRC_READ_BUFFER_SIZE;
	//TODO
	public final Role DEFAULT_ROLE = Role.ANONYMOUS;
	public final String CALL_AUTH = "@AUTH";
	public final String VIEW_EXTENSION = "." + "jsp";
	public final String VIEW_OUTPUT_METHOD = "out.write";
	public final boolean IS_VAR_USEABLE = true;
	public int VIEWER_WRITER = 5012;
	public int VIEW_STACK_SIZE = 12;
	public final String DATABASE_EXTENSION = "." + "dt";
	public final int DATABASE_CONNECTOR_POOL_CAPACITY = 100;
	
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
		PACKAGE_NAME = getString("packageName", "controller");
		DATABASE_DRIVER_CLASS = getString("DatabaseDriverClassName", "org.sqlite.JDBC");
		DATABASE_URL = getString("DatabaseURL", "jdbc:sqlite:" + FileSystem.Data.getAbsolutePath().replace('\\', '/')
		        + "/");
		MAX_UPLOADFILE_SIZE = getInteger("MaxUploadfileSize", 100 * 1024 * 1024);
		
		VIEW_CHAREET = getString("ViewChareet", "UTF-8");
		VIEW_SRC_READ_BUFFER_SIZE = getInteger("ViewSrcReadBufferSize", 5120);
	}
	
	private String get(final String key) {
		final Object object = properties.get(key);
		if (object != null) {
			return object.toString();
		}
		throw new MissingResourceException("Not Found", Config.class.getCanonicalName(), key);
	}
	
	private String getString(final String key, final String defaultValue) {
		try {
			return get(key);
		}
		catch (final MissingResourceException e) {
			return defaultValue;
		}
	}
	
	private int getInteger(final String key, final int defaultValue) {
		try {
			return Integer.parseInt(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private long getLong(final String key, final long defaultValue) {
		try {
			return Long.parseLong(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private double getDouble(final String key, final double defaultValue) {
		try {
			return Double.parseDouble(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private boolean getBoolean(final String key, final boolean defaultValue) {
		try {
			return Boolean.parseBoolean(get(key));
		}
		catch (final MissingResourceException | NumberFormatException e) {
			return defaultValue;
		}
	}
}
