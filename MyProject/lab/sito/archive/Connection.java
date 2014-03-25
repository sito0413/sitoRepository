package sito.archive;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import sito.archive.connection.AbstractConnection;
import sito.archive.connection.valueManager.ValueManager;


public class Connection extends AbstractConnection {
	Connection(final File tempDir, final File connectionDir) {
		super(tempDir, connectionDir);
	}
	
	public synchronized Collection<Value> read(final String key) throws StoreException {
		final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();
		final ByteBuffer typeBuffer = ByteBuffer.wrap(new byte[1]);
		for (final File f : find(key)) {
			final File dataFile = getTempFile();
			try (final FileChannel src = FileChannel.open(f.toPath(), Archive.READ)) {
				if (src.read(typeBuffer) != -1) {
					typeBuffer.flip();
					values.add(ValueManager.read(typeBuffer.get(), dataFile, src));
					continue;
				}
				throw new StoreException("Illegal Result");
			}
			catch (final IOException e) {
				throw new StoreException("IllegalResultException", e);
			}
		}
		return values;
	}
	
	public synchronized Collection<Value> change(final String key, final Object object) throws StoreException {
		if (object == null) {
			return delete(key);
		}
		final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();
		for (final File f : find(key)) {
			values.add(ValueManager.change(f, object));
		}
		if (values.isEmpty()) {
			values.add(ValueManager.change(new File(getConnectionDir(), key + Archive.CONNECTION_EXTENSION), object));
		}
		return values;
	}
	
	private Collection<Value> delete(final String key) {
		for (final File f : find(key)) {
			delete(f);
		}
		return new CopyOnWriteArrayList<>();
	}
	
	public static void delete(final File f) {
		if (f.exists() == false) {
			return;
		}
		else if (f.isFile()) {
			f.delete();
		}
		else if (f.isDirectory()) {
			final File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				delete(files[i]);
			}
			f.delete();
		}
	}
	
}