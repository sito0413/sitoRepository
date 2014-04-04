package frameWork.core.fileSystem;

import java.io.File;
import java.net.URLConnection;
import java.util.Properties;

import frameWork.core.fileSystem.config.Config;

public class FileSystem {
	private static final File Root;
	public static FileElement Temp;
	public static FileElement Data;
	public static FileElement Viewer;
	public static Resource Resource;
	public static Config Config;
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
		Resource = new Resource(Root);
		Config = new Config(Root);
		
	}
	
}
