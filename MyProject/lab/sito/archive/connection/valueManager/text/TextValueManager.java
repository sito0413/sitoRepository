package sito.archive.connection.valueManager.text;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import sito.archive.Archive;
import sito.archive.Value;
import sito.archive.connection.valueManager.ValueManager;


public class TextValueManager extends ValueManager {
	private static final String TYPE = "T";
	public static final byte TYPE_OF_BYTE = TYPE.getBytes(Archive.CHARSET)[0];
	
	@Override
	public byte getTypOfByte() {
		return TYPE_OF_BYTE;
	}
	
	@Override
	public boolean isMyType(final Object object) {
		return object != null;
	}
	
	@Override
	public Value change(final File valueFile, final Object object, final FileChannel dest) throws IOException {
		final String text = object.toString();
		dest.write(ByteBuffer.wrap((TYPE + text).getBytes(Archive.CHARSET)));
		return new TextValue(text);
	}
	
	@Override
	public Value read(final File dataFile, final FileChannel src, final FileChannel dest) throws IOException {
		if ((src.size() - 1) != 0) {
			final byte[] bs = new byte[(int) src.size() - 1];
			src.position(1);
			src.read(ByteBuffer.wrap(bs));
			final String text = new String(bs, Archive.CHARSET);
			dest.write(ByteBuffer.wrap(bs));
			return new TextValue(text);
		}
		return null;
	}
	
}