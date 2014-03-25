package sito.archive.connection.valueManager;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import sito.archive.Archive;
import sito.archive.StoreException;
import sito.archive.Value;
import sito.archive.connection.valueManager.file.FileValueManager;
import sito.archive.connection.valueManager.text.TextValueManager;


public abstract class ValueManager {
	private static final ValueManager[] MANAGERS = {
	        new FileValueManager(), new TextValueManager()
	};
	
	protected ValueManager() {
	}
	
	public abstract boolean isMyType(final Object object);
	
	public abstract byte getTypOfByte();
	
	public abstract Value change(File valueFile, Object object, FileChannel dest) throws IOException;
	
	public abstract Value read(File dataFile, FileChannel src, FileChannel dest) throws IOException;
	
	public static Value read(final byte type, final File dataFile, final FileChannel src) throws StoreException {
		try (final FileChannel dest = FileChannel.open(dataFile.toPath(), Archive.WRITE)) {
			for (final ValueManager manager : MANAGERS) {
				if (type == manager.getTypOfByte()) {
					return manager.read(dataFile, src, dest);
				}
			}
			throw new StoreException("Illegal Result");
		}
		catch (final IOException e) {
			throw new StoreException("Illegal Result", e);
		}
	}
	
	public static Value change(final File file, final Object object) throws StoreException {
		try (final FileChannel dest = FileChannel.open(file.toPath(), Archive.WRITE)) {
			for (final ValueManager manager : MANAGERS) {
				if (manager.isMyType(object)) {
					return manager.change(file, object, dest);
				}
			}
			throw new StoreException("Illegal Result");
		}
		catch (final IOException e) {
			throw new StoreException("Illegal Result", e);
		}
	}
}