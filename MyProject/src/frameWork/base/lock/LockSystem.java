package frameWork.base.lock;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import frameWork.base.core.fileSystem.FileSystem;

public class LockSystem {
	private static Map<LockKey, FileLock> lockTable = new ConcurrentHashMap<>();

	public static boolean isLock(final LockKey key) {
		if (!lockTable.containsKey(key)) {
			final File lockFile = new File(FileSystem.Lock, key.toString());
			try {
				@SuppressWarnings("resource")
				final FileLock fileLock = new RandomAccessFile(lockFile, "rw").getChannel().tryLock();
				if (fileLock != null) {
					fileLock.release();
					fileLock.channel().close();
					return false;
				}
				return true;
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public static synchronized boolean lock(final LockKey key) throws IOException {
		if (!lockTable.containsKey(key)) {
			final File lockFile = new File(FileSystem.Lock, key.toString());
			@SuppressWarnings("resource")
			final FileLock fileLock = new RandomAccessFile(lockFile, "rw").getChannel().tryLock();
			if (fileLock != null) {
				lockTable.put(key, fileLock);
			}
			return fileLock != null;
		}
		return false;
	}

	public static synchronized void unlock(final LockKey key) throws IOException {
		if (lockTable.containsKey(key)) {
			lockTable.get(key).release();
			lockTable.get(key).channel().close();
			lockTable.remove(key);
		}
	}
}
