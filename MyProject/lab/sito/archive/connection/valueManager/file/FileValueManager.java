package sito.archive.connection.valueManager.file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import sito.archive.Archive;
import sito.archive.Value;
import sito.archive.connection.valueManager.ValueManager;


public class FileValueManager extends ValueManager {
	private static final String TYPE = "F";
	public static final byte TYPE_OF_BYTE = TYPE.getBytes(Archive.CHARSET)[0];
	private static final String LF = "\n";
	private static final byte BYTE_OF_LF = LF.getBytes(Archive.CHARSET)[0];
	
	@Override
	public byte getTypOfByte() {
		return TYPE_OF_BYTE;
	}
	
	@Override
	public boolean isMyType(final Object object) {
		return object instanceof File;
	}
	
	@Override
	public Value change(final File valueFile, final Object object, final FileChannel dest) throws IOException {
		final File file = (File) object;
		dest.write(ByteBuffer.wrap((TYPE + file.getName() + LF).getBytes(Archive.CHARSET)));
		try (final FileChannel src = FileChannel.open(file.toPath(), Archive.READ);) {
			src.transferTo(0, src.size(), dest);
		}
		return new FileValue(valueFile, file.getName());
	}
	
	@Override
	public Value read(final File dataFile, final FileChannel src, final FileChannel dest) throws IOException {
		if ((src.size() - 1) != 0) {
			final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);//1K
			long position = 1;
			src.position(position);
			while (src.read(buffer) != -1) {
				buffer.flip();
				while (buffer.position() < buffer.limit()) {
					if (buffer.get() == BYTE_OF_LF) {
						final long dataPosition = position + buffer.position();
						src.position(dataPosition);
						dest.transferFrom(src, 0, src.size());
						
						src.position(1);
						final byte[] bs = new byte[(int) dataPosition - 2];
						src.read(ByteBuffer.wrap(bs));
						return new FileValue(dataFile, new String(bs, Archive.CHARSET));
					}
				}
				buffer.clear();
				position = src.position();
			}
		}
		return null;
	}
}