package frameWork.core.config;

import java.io.File;
import java.net.URLConnection;
import java.util.Properties;

public class FileSystem {
	private static final File Root;
	static FileElement Temp;
	static FileElement Data;
	static FileElement Viewer;
	static FileElement Resource;
	static {
		File dir;
		try {
			final Properties properties = new Properties();
			final URLConnection connection = Base.class.getClassLoader().getResource("info.xml").openConnection();
			if (connection != null) {
				properties.loadFromXML(connection.getInputStream());
			}
			dir = new File(Base.Path, properties.getProperty("SystemID"));
			if (dir.exists() && dir.isFile()) {
				dir = new File(Base.Path, "Temp");
			}
		}
		catch (final Exception e) {
			dir = new File(Base.Path, "Temp");
		}
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		catch (final Exception e) {
			dir = new File(Base.Path, "Temp");
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		Root = dir;
		Temp = new FileElement(Root, "temp");
		Data = new FileElement(Root, "data");
		Viewer = new FileElement(Root, "viewer");
		Resource = new FileElement(Root, "resource");
		
	}
	
	static class FileElement {
		
		public FileElement(final File root, final String name) {
			new File(root, name).mkdirs();
		}
	}
}
