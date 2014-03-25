package sito.archive.driver.packager;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import sito.archive.Archive;


public abstract class AbstractPackager {
	private static final String DELIMITA = "_";
	private static final byte BYTE_OF_DELIMITA = DELIMITA.getBytes(Archive.CHARSET)[0];
	private static FileChannel BUFFER_CHANNEL = null;
	static {
		String dir = System.getProperty("java.io.tmpdir");
		if (dir == null) {
			dir = System.getProperty("user.dir");
			if (dir == null) {
				dir = "";
			}
		}
		final File file = new File(dir, Archive.class.getName() + Archive.BUFFER_EXTENSION);
		file.deleteOnExit();
		try {
			BUFFER_CHANNEL = FileChannel.open(file.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE,
			        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
		}
		catch (final IOException e) {
			throw new RuntimeException("CAN NOT CREATE BUFFER", e);
		}
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
	
	protected void packing(final FileChannel dest, final File baseDirectory) throws IOException {
		for (final File connectionDir : baseDirectory.listFiles()) {
			if (!(connectionDir.isDirectory() && connectionDir.getName().endsWith(Archive.DRIVER_EXTENSION))) {
				delete(connectionDir);
				continue;
			}
			int count = 0;
			final ArrayList<File> arrayFileList = new ArrayList<>();
			final ArrayList<byte[]> arrayByteList = new ArrayList<>();
			long size = 0;
			for (final File file : connectionDir.listFiles()) {
				if (!(file.isFile() && file.getName().endsWith(Archive.CONNECTION_EXTENSION)) || (file.length() == 0)) {
					delete(file);
					continue;
				}
				final byte[] indexBytes = getIndexBytes(file.length(), file, Archive.CONNECTION_EXTENSION);
				arrayByteList.add(indexBytes);
				arrayFileList.add(file);
				count++;
				size += indexBytes.length;
				size += file.length();
			}
			if (count != 0) {
				dest.write(ByteBuffer.wrap(getIndexBytes(size, connectionDir, Archive.DRIVER_EXTENSION)));
				for (int i = 0; i < count; i++) {
					dest.write(ByteBuffer.wrap(arrayByteList.get(i)));
					try (final FileChannel src = FileChannel.open(arrayFileList.get(i).toPath(), Archive.READ)) {
						src.transferTo(0, src.size(), dest);
					}
					arrayFileList.get(i).delete();
				}
			}
			connectionDir.delete();
		}
		baseDirectory.delete();
	}
	
	private byte[] getIndexBytes(final long size, final File file, final String extension) {
		final String index = size + DELIMITA
		        + file.getName().substring(0, file.getName().length() - extension.length());
		return (index.getBytes(Archive.CHARSET).length + DELIMITA + index).getBytes(Archive.CHARSET);
	}
	
	protected void unpacking(final File baseDirectory, final FileChannel src) throws IOException {
		BUFFER_CHANNEL.position(0);
		src.transferTo(0, src.size(), BUFFER_CHANNEL);
		unpacking(BUFFER_CHANNEL.map(MapMode.READ_WRITE, 0, src.size()), baseDirectory, src.size(), true);
	}
	
	private void unpacking(final ByteBuffer buffer, final File directory, final long size, final boolean flg)
	        throws IOException {
		directory.mkdirs();
		while (buffer.position() < size) {
			final int indexSize = getIndexSize(buffer, size);
			if (((buffer.position() + indexSize) < size) && (indexSize != 0)) {
				buffer.position(buffer.position() + 1);
				final int limit = buffer.position() + indexSize;
				final int fileSize = getIndexSize(buffer, limit);
				if (fileSize != 0) {
					buffer.position(buffer.position() + 1);
					final byte[] fileNameBytes = new byte[(limit - buffer.position())];
					buffer.get(fileNameBytes);
					final File file = new File(directory, new String(fileNameBytes, Archive.CHARSET)
					        + (flg ? Archive.DRIVER_EXTENSION : Archive.CONNECTION_EXTENSION));
					if (flg) {
						unpacking(buffer, file, (buffer.position() + fileSize), false);
					}
					else {
						try (final FileChannel dest = FileChannel.open(file.toPath(), Archive.WRITE)) {
							BUFFER_CHANNEL.transferTo(buffer.position(), fileSize, dest);
							buffer.position(buffer.position() + fileSize);
						}
					}
				}
			}
		}
	}
	
	private int getIndexSize(final ByteBuffer buffer, final long limit) {
		int indexPosition = buffer.position();
		final int lastIndexPosition = buffer.position();
		while (buffer.position() < limit) {
			if (buffer.get() == BYTE_OF_DELIMITA) {
				final byte[] indexSizeBytes = new byte[(indexPosition - lastIndexPosition)];
				buffer.position(lastIndexPosition);
				buffer.get(indexSizeBytes);
				return Integer.parseInt(new String(indexSizeBytes, Archive.CHARSET));
			}
			indexPosition++;
		}
		return 0;
	}
}