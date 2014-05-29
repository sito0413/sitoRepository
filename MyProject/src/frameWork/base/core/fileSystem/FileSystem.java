package frameWork.base.core.fileSystem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import frameWork.architect.Literal;

public class FileSystem {
	public static final String SystemID;
	public static final File Root;
	public static Temp Temp;
	public static FileElement Database;
	public static Resource Data;
	public static FileElement Viewer;
	public static FileElement Print;
	public static Resource Resource;
	public static Config Config;
	public static Log Log;
	static {
		File dir;
		String systemIDString = Literal.Temp;
		try {
			File path;
			final Properties properties = new Properties();
			final URL url = FileSystem.class.getClassLoader().getResource(Literal.info_xml);
			if (url != null) {
				final URLConnection connection = url.openConnection();
				if (connection != null) {
					properties.loadFromXML(connection.getInputStream());
				}
			}
			final String pathString = properties.getProperty(Literal.Path);
			if (pathString != null) {
				path = new File(pathString);
				if (path.exists() && path.isFile()) {
					path = new File(Literal.Root);
					
				}
				else if (!path.exists()) {
					path.mkdirs();
				}
			}
			else {
				path = new File(Literal.Root);
			}
			systemIDString = properties.getProperty(Literal.SystemID);
			if (systemIDString != null) {
				dir = new File(path, systemIDString);
				if (dir.exists() && dir.isFile()) {
					dir = new File(path, Literal.Temp);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				else if (!dir.exists()) {
					dir.mkdirs();
				}
			}
			else {
				dir = new File(path, Literal.Temp);
			}
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		catch (final IOException e) {
			dir = new File(new File(Literal.Root), Literal.Temp);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		SystemID = systemIDString;
		Root = dir;
		Temp = new Temp(Root);
		Database = new FileElement(Root, "database");
		Data = new Resource(Root, "data");
		Viewer = new FileElement(Root, Literal.viewer);
		Print = new FileElement(Root, "Print");
		Resource = new Resource(Root, "resource");
		Config = new Config(Root);
		Log = new Log(Root);
	}
}
