package sito.archive.connection;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import sito.archive.Archive;


public abstract class AbstractConnection {
	
	private static Long sNo = 0L;
	private final File tempDir;
	private final File connectionDir;
	
	protected AbstractConnection(final File tempDir, final File connectionDir) {
		this.tempDir = tempDir;
		this.connectionDir = connectionDir;
		this.connectionDir.mkdirs();
	}
	
	protected File getConnectionDir() {
		return connectionDir;
	}
	
	protected File getTempFile() {
		do {
			final File dataFile = new File(tempDir, (sNo++) + Archive.TEMP_EXTENSION);
			if (dataFile.exists()) {
				continue;
			}
			try {
				dataFile.createNewFile();
			}
			catch (final IOException exception) {
				continue;
			}
			return dataFile;
		}
		while (true);
	}
	
	protected CopyOnWriteArrayList<File> find(final String key) {
		final CopyOnWriteArrayList<File> listFiles = new CopyOnWriteArrayList<>();
		final File[] files = connectionDir.listFiles();
		if (files != null) {
			for (final File file : files) {
				final String fileName = file.getName();
				if (fileName.endsWith(Archive.CONNECTION_EXTENSION)) {
					if (fileName.substring(0, fileName.length() - Archive.CONNECTION_EXTENSION.length())
					        .startsWith(key)) {
						listFiles.add(file);
					}
				}
			}
		}
		return listFiles;
	}
	
}