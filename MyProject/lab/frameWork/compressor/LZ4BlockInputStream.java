package frameWork.compressor;

import static frameWork.compressor.LZ4BlockOutputStream.*;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LZ4BlockInputStream extends FilterInputStream {
	private final LZ4Decompressor decompressor;
	private final StreamingXXHash32 checksum;
	private byte[] buffer;
	private byte[] compressedBuffer;
	private int originalLen;
	private int o;
	private boolean finished;
	
	public LZ4BlockInputStream(final InputStream in) {
		super(in);
		this.decompressor = new LZ4Decompressor();
		this.checksum = new StreamingXXHash32(DEFAULT_SEED);
		this.buffer = new byte[0];
		this.compressedBuffer = new byte[HEADER_LENGTH];
		o = originalLen = 0;
		finished = false;
	}
	
	@Override
	public int available() throws IOException {
		return originalLen - o;
	}
	
	@Override
	public int read() throws IOException {
		if (finished) {
			return -1;
		}
		if (o == originalLen) {
			refill();
		}
		if (finished) {
			return -1;
		}
		return buffer[o++] & 0xFF;
	}
	
	@Override
	public int read(final byte[] b, final int off, int len) throws IOException {
		checkRange(b, off, len);
		if (finished) {
			return -1;
		}
		if (o == originalLen) {
			refill();
		}
		if (finished) {
			return -1;
		}
		len = Math.min(len, originalLen - o);
		System.arraycopy(buffer, o, b, off, len);
		o += len;
		return len;
	}
	
	@Override
	public int read(final byte[] b) throws IOException {
		return read(b, 0, b.length);
	}
	
	@Override
	public long skip(final long n) throws IOException {
		if (finished) {
			return -1;
		}
		if (o == originalLen) {
			refill();
		}
		if (finished) {
			return -1;
		}
		final int skipped = (int) Math.min(n, originalLen - o);
		o += skipped;
		return skipped;
	}
	
	private void refill() throws IOException {
		readFully(compressedBuffer, HEADER_LENGTH);
		for (int i = 0; i < MAGIC_LENGTH; ++i) {
			if (compressedBuffer[i] != MAGIC[i]) {
				throw new IOException("Stream is corrupted");
			}
		}
		final int token = compressedBuffer[MAGIC_LENGTH] & 0xFF;
		final int compressionMethod = token & 0xF0;
		final int compressionLevel = COMPRESSION_LEVEL_BASE + (token & 0x0F);
		if ((compressionMethod != COMPRESSION_METHOD_RAW) && (compressionMethod != COMPRESSION_METHOD_LZ4)) {
			throw new IOException("Stream is corrupted");
		}
		final int compressedLen = readIntLE(compressedBuffer, MAGIC_LENGTH + 1);
		originalLen = readIntLE(compressedBuffer, MAGIC_LENGTH + 5);
		final int check = readIntLE(compressedBuffer, MAGIC_LENGTH + 9);
		assert HEADER_LENGTH == (MAGIC_LENGTH + 13);
		if ((originalLen > (1 << compressionLevel)) || (originalLen < 0) || (compressedLen < 0)
		        || ((originalLen == 0) && (compressedLen != 0)) || ((originalLen != 0) && (compressedLen == 0))
		        || ((compressionMethod == COMPRESSION_METHOD_RAW) && (originalLen != compressedLen))) {
			throw new IOException("Stream is corrupted");
		}
		if ((originalLen == 0) && (compressedLen == 0)) {
			if (check != 0) {
				throw new IOException("Stream is corrupted");
			}
			finished = true;
			return;
		}
		if (buffer.length < originalLen) {
			buffer = new byte[Math.max(originalLen, (buffer.length * 3) / 2)];
		}
		switch ( compressionMethod ) {
			case COMPRESSION_METHOD_RAW :
				readFully(buffer, originalLen);
				break;
			case COMPRESSION_METHOD_LZ4 :
				if (compressedBuffer.length < originalLen) {
					compressedBuffer = new byte[Math.max(compressedLen, (compressedBuffer.length * 3) / 2)];
				}
				readFully(compressedBuffer, compressedLen);
				try {
					final int compressedLen2 = decompressor.decompress(compressedBuffer, buffer, originalLen);
					if (compressedLen != compressedLen2) {
						throw new IOException("Stream is corrupted");
					}
				}
				catch (final LZ4Exception e) {
					throw new IOException("Stream is corrupted", e);
				}
				break;
			default :
				throw new AssertionError();
		}
		checksum.reset();
		checksum.update(buffer, 0, originalLen);
		if ((int) checksum.getValue() != check) {
			throw new IOException("Stream is corrupted");
		}
		o = 0;
	}
	
	private void readFully(final byte[] b, final int len) throws IOException {
		int read = 0;
		while (read < len) {
			final int r = in.read(b, read, len - read);
			if (r < 0) {
				throw new EOFException("Stream ended prematurely");
			}
			read += r;
		}
		assert len == read;
	}
	
	@Override
	public boolean markSupported() {
		return false;
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public void mark(final int readlimit) {
		// unsupported
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public void reset() throws IOException {
		throw new IOException("mark/reset not supported");
	}
	
	private static int readIntLE(final byte[] buf, final int i) {
		return (buf[i] & 0xFF) | ((buf[i + 1] & 0xFF) << 8) | ((buf[i + 2] & 0xFF) << 16) | ((buf[i + 3] & 0xFF) << 24);
	}
	
	private static void checkRange(final byte[] buf, final int off, final int len) {
		if (len < 0) {
			throw new IllegalArgumentException("lengths must be >= 0");
		}
		
		if (len > 0) {
			if ((off < 0) || (off >= buf.length)) {
				throw new ArrayIndexOutOfBoundsException(off);
			}
			if ((((off + len) - 1) < 0) || (((off + len) - 1) >= buf.length)) {
				throw new ArrayIndexOutOfBoundsException(off);
			}
		}
	}
}
