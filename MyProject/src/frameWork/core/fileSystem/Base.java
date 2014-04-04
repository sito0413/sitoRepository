package frameWork.core.fileSystem;

import java.io.File;
import java.net.URLConnection;
import java.util.Properties;

public class Base {
	public static final File Path;
	static {
		File dir;
		try {
			final Properties properties = new Properties();
			final URLConnection connection = Base.class.getClassLoader().getResource("info.xml").openConnection();
			if (connection != null) {
				properties.loadFromXML(connection.getInputStream());
			}
			dir = new File(properties.getProperty("Path"));
			if (dir.exists() && dir.isFile()) {
				dir = new File(new File("").getAbsolutePath());
			}
		}
		catch (final Exception e) {
			dir = new File(new File("").getAbsolutePath());
		}
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		catch (final Exception e) {
			dir = new File(new File("").getAbsolutePath());
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		Path = dir;
	}
}
