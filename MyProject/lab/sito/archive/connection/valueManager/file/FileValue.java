package sito.archive.connection.valueManager.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import sito.archive.Archive;
import sito.archive.StoreException;
import sito.archive.Value;


class FileValue extends Value {
	protected final File valueFile;
	protected final String fileName;
	
	public FileValue(final File valueFile, final String fileName) {
		this.valueFile = valueFile;
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return fileName;
	}
	
	@Override
	public synchronized void open(final File dirPath) throws StoreException {
		dirPath.mkdirs();
		try (final FileChannel src = FileChannel.open(valueFile.toPath(), Archive.READ)) {
			try (final FileChannel dest = FileChannel.open(new File(dirPath, fileName).toPath(), Archive.WRITE)) {
				src.transferTo(0, src.size(), dest);
			}
		}
		catch (final IOException e) {
			throw new StoreException("Value Not Open", e);
		}
	}
	
	@Override
	public void exe() {
		//NOP
	}
}