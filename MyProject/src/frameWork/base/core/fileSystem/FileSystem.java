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
	public static final Temp Temp;
	public static final FileElement Database;
	public static final Resource Data;
	public static final FileElement Viewer;
	public static final FileElement Print;
	public static final Resource Resource;
	public static final Config Config;
	public static final Log Log;
	public static final FileElement Lock;
	static {
		File dir;
		File path = null;
		String systemIDString = null;
		try {
			final Properties properties = loadFromXML(FileSystem.class.getClassLoader().getResource(Literal.info_xml));
			path = loadPathDir(properties.getProperty(Literal.Path));
			systemIDString = loadSystemID(properties.getProperty(Literal.SystemID));
		}
		catch (final IOException e) {
			//NOOP
		}
		dir = loadRootDir(path, systemIDString);
		
		SystemID = systemIDString;
		Root = dir;
		Temp = new Temp(Root);
		Database = new FileElement(Root, "database");
		Data = new Resource(Root, "data");
		Viewer = new FileElement(Root, Literal.viewer);
		Print = new FileElement(Root, "print");
		Resource = new Resource(Root, "resource");
		Config = new Config(Root);
		Log = new Log(Root);
		Lock = new FileElement(Root, "lock");
	}
	
	static Properties loadFromXML(final URL url) throws IOException {
		final Properties properties = new Properties();
		if (url != null) {
			final URLConnection connection = url.openConnection();
			if (connection != null) {
				properties.loadFromXML(connection.getInputStream());
			}
		}
		return properties;
	}
	
	static File loadRootDir(final File path, final String systemIDString) {
		File dir;
		if ((path != null) && (!path.exists() || path.isDirectory())) {
			if (systemIDString != null) {
				dir = new File(path, systemIDString);
			}
			else {
				dir = new File(path, Literal.Temp);
			}
		}
		else {
			dir = new File(Literal.Root, Literal.Temp);
		}
		if (dir.exists() && dir.isFile()) {
			dir = new File(path, Literal.Temp);
			dir.mkdirs();
		}
		else if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	static File loadPathDir(final String pathString) {
		if (pathString != null) {
			File path = new File(pathString);
			if (path.exists() && path.isFile()) {
				path = new File(Literal.Root);
			}
			else if (!path.exists()) {
				path.mkdirs();
			}
			return path;
		}
		return new File(Literal.Root);
	}
	
	static String loadSystemID(final String systemIDString) {
		if (systemIDString != null) {
			return systemIDString;
		}
		return Literal.Temp;
	}
	
	public static boolean neweComparison(final File src, final File base) {
		return FileUtil.neweComparison(src, base);
	}
	
	public static void copy(final File src, final File dest) throws IOException {
		FileUtil.copy(src, dest);
	}
	
	public static void delete(final File file) {
		FileUtil.delete(file);
	}
	
	public static void zip(final File src, final File dest) throws IOException {
		FileUtil.zip(src, dest);
	}
	
	public static void unzip(final File src, final File dest) throws IOException {
		FileUtil.unzip(src, dest);
	}
}
