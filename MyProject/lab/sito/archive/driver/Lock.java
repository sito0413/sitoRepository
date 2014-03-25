package sito.archive.driver;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

import sito.archive.Archive;
import sito.archive.StoreException;


class Lock {
	private final File lockFile;
	private FileChannel channel;
	
	Lock(final File baseDirectory, final String fileName) throws StoreException {
		try {
			this.lockFile = new File(baseDirectory, fileName + Archive.LOCK_EXTENSION);
			final long s = System.nanoTime();
			FileLock fileLock = null;
			this.channel = FileChannel.open(this.lockFile.toPath(), StandardOpenOption.WRITE,
			        StandardOpenOption.CREATE, StandardOpenOption.READ);
			while ((channel != null) && (fileLock == null) && ((10 * 1000 * 1000 * 1000) > (System.nanoTime() - s))) {
				try {
					fileLock = channel.tryLock();
					return;
				}
				catch (final Exception e) {
					continue;
				}
			}
			throw new StoreException("Can Not Lock");
		}
		catch (final IOException e) {
			throw new StoreException("Can Not Lock", e);
		}
	}
	
	boolean isLock() {
		return channel != null;
	}
	
	void unlock() throws StoreException {
		try {
			channel.close();
		}
		catch (final IOException e) {
			throw new StoreException("Can Not Unlock", e);
		}
		finally {
			channel = null;
			lockFile.delete();
		}
	}
}