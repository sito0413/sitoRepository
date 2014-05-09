package frameWork.core.fileSystem;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Properties;

public class FileSystem {
	public static class Temp extends FileElement {
		public static class UploadDir extends FileElement {
			public UploadDir(final File root) {
				super(root, "_uploadDir");
			}
			
			public File getUploadfile(final String fileName) throws IOException {
				return File.createTempFile("upload", "." + fileName, this);
			}
		}
		
		public final UploadDir UploadDir;
		
		public Temp(final File root) {
			super(root, "temp");
			UploadDir = new UploadDir(this);
		}
	}
	
	public static class Resource extends FileElement {
		public Resource(final File root) {
			super(root, "resource");
		}
		
		public File getResource(final String target) {
			return new File(this, target);
		}
	}
	
	static final File Root;
	public static Temp Temp;
	public static FileElement Data;
	public static FileElement Viewer;
	public static Resource Resource;
	public static Config Config;
	static {
		File dir;
		try {
			File path;
			final Properties properties = new Properties();
			final URLConnection connection = FileSystem.class.getClassLoader().getResource("info.xml").openConnection();
			if (connection != null) {
				properties.loadFromXML(connection.getInputStream());
			}
			final String pathString = properties.getProperty("Path");
			if (pathString != null) {
				path = new File(pathString);
				if (path.exists() && path.isFile()) {
					path = new File(new File("").getAbsolutePath());
				}
				else if (!path.exists()) {
					path.mkdirs();
				}
			}
			else {
				path = new File(new File("").getAbsolutePath());
			}
			final String systemIDString = properties.getProperty("SystemID");
			if (systemIDString != null) {
				dir = new File(path, systemIDString);
				if (dir.exists() && dir.isFile()) {
					dir = new File(path, "Temp");
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				else if (!dir.exists()) {
					dir.mkdirs();
				}
			}
			else {
				dir = new File(path, "Temp");
			}
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		catch (final IOException e) {
			dir = new File(new File(new File("").getAbsolutePath()), "Temp");
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		Root = dir;
		Temp = new Temp(Root);
		Data = new FileElement(Root, "data");
		Viewer = new FileElement(Root, "viewer");
		Resource = new Resource(Root);
		Config = new Config(Root);
	}
}
