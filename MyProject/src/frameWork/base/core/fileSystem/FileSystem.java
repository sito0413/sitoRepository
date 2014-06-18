package frameWork.base.core.fileSystem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

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
		if (!base.exists()) {
			return true;
		}
		if (src.isDirectory()) {
			if (base.isDirectory()) {
				final File[] srcListFiles = src.listFiles();
				for (final File file : srcListFiles) {
					if (neweComparison(file, new File(base, file.getName()))) {
						return true;
					}
				}
				return srcListFiles.length != base.listFiles().length;
			}
			return true;
		}
		else if (base.isDirectory()) {
			return true;
		}
		else {
			return src.lastModified() > base.lastModified();
		}
	}
	
	public static void copy(final File src, final File dest) throws IOException {
		delete(dest);
		if (src.isDirectory()) {
			// ディレクトリがない場合、作成
			if (!dest.exists()) {
				dest.mkdir();
			}
			
			for (final String file : src.list()) {
				copy(new File(src, file), new File(dest, file));
			}
		}
		else {
			// ファイルのコピー
			try (FileChannel srcChannel = new FileInputStream(src).getChannel();
			        FileChannel destChannel = new FileOutputStream(dest).getChannel();) {
				srcChannel.transferTo(0, srcChannel.size(), destChannel);
			}
		}
	}
	
	public static void delete(final File file) {
		if (file.isDirectory()) {
			for (final File subFile : file.listFiles()) {
				delete(subFile);
			}
		}
		file.delete();
	}
	
	public static void zip(final File src, final File dest) throws IOException {
		dest.getParentFile().mkdirs();
		try (final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest))) {
			encode(zos, src, src.getAbsolutePath());
		}
	}
	
	private static void encode(final ZipOutputStream zos, final File src, final String basePath) throws IOException {
		if (src.isDirectory()) {
			for (final File subFile : src.listFiles()) {
				encode(zos, subFile, basePath);
			}
		}
		else {
			final byte[] buf = new byte[1024];
			final ZipEntry entry = new ZipEntry(src.getAbsolutePath().substring(basePath.length() + 1)
			        .replace('\\', '/'));
			zos.putNextEntry(entry);
			try (InputStream is = new BufferedInputStream(new FileInputStream(src))) {
				for (;;) {
					final int len = is.read(buf);
					if (len < 0) {
						break;
					}
					zos.write(buf, 0, len);
				}
			}
		}
	}
	
	public static void unzip(final File src, final File dest) throws IOException {
		final byte[] buf = new byte[1024];
		try (final ZipFile zf = new ZipFile(src)) {
			for (final Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
				final ZipEntry entry = e.nextElement();
				if (entry.isDirectory()) {
					new File(dest, entry.getName()).mkdirs();
					continue;
				}
				try (FileOutputStream fo = new FileOutputStream(new File(dest, entry.getName()))) {
					try (final InputStream is = zf.getInputStream(entry)) {
						for (;;) {
							final int len = is.read(buf);
							if (len < 0) {
								break;
							}
							fo.write(buf, 0, len);
						}
					}
				}
			}
		}
	}
}
