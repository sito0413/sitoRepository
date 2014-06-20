package frameWork.base.core.fileSystem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
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
