package frameWork.base.core.fileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.MissingResourceException;
import java.util.Properties;

import frameWork.architect.Literal;
import frameWork.base.core.authority.Role;

@SuppressWarnings("unused")
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
	public final int VIEWER_WRITER = 5012;
	public final int VIEW_STACK_SIZE = 12;
	public final String DATABASE_EXTENSION = "." + "dat";
	public final int DATABASE_CONNECTOR_POOL_CAPACITY = 100;
	public final String LOGIN_PATH = "";
	public final String CALL_REQUEST_URI = "@REQUEST_URI";
	public final String LOGGING_EXTENSION = "." + "log";
	public final String LOGGING_EXCEPTION_FILE_NAME = "exception";
	public final String LOGGING_MESSAGE_FILE_NAME = "message";
	public final String LOGGING_LOGGING_FILE_NAME = "logging";
	public final String ENCRYPTION_KEY = "framework_encryption_key";
	public final String ENCRYPTION_BASE64_ENCODING = "UTF-8";
	
	public final String MAIL_SENDER = "sito0413@yahoo.co.jp";
	public final String MAIL_SENDER_NAME = "sito";
	public final String MAIL_ENCODING = "Shift_JIS";
	public final int MAIL_SMTP_PORT = 587;
	public final String MAIL_SMTP_HOST = "smtp.mail.yahoo.co.jp";
	public final boolean MAIL_SMTP_SSL = false;
	public final boolean MAIL_SMTP_TLS = false;
	public final boolean MAIL_SMTP_AUTH = true;
	public final String MAIL_SMTP_USER = "sito0413";
	public final String MAIL_SMTP_PASSWORD = "inates0820";
	
	public Config(final File root) {
		super(root, "config");
		properties = new Properties();
		try {
			final File file = new File(this, Literal.config_xml);
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
		DATABASE_URL = getString("DatabaseURL",
		        "jdbc:sqlite:" + FileSystem.Database.getAbsolutePath().replace('\\', '/') + "/");
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
