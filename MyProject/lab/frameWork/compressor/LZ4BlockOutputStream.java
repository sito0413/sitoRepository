package frameWork.compressor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LZ4BlockOutputStream extends FilterOutputStream {
	
	static final byte[] MAGIC = new byte[] {
	        'L', 'Z', '4', 'B', 'l', 'o', 'c', 'k'
	};
	static final int MAGIC_LENGTH = MAGIC.length;
	
	static final int HEADER_LENGTH = MAGIC_LENGTH // magic bytes
	        + 1 // token
	        + 4 // compressed length
	        + 4 // decompressed length
	        + 4; // checksum
	
	static final int COMPRESSION_LEVEL_BASE = 10;
	static final int MIN_BLOCK_SIZE = 64;
	static final int MAX_BLOCK_SIZE = 1 << (COMPRESSION_LEVEL_BASE + 0x0F);
	
	static final int COMPRESSION_METHOD_RAW = 0x10;
	static final int COMPRESSION_METHOD_LZ4 = 0x20;
	
	static final int DEFAULT_SEED = 0x9747b28c;
	
	private static int compressionLevel(final int blockSize) {
		if (blockSize < MIN_BLOCK_SIZE) {
			throw new IllegalArgumentException("blockSize must be >= " + MIN_BLOCK_SIZE + ", got " + blockSize);
		}
		else if (blockSize > MAX_BLOCK_SIZE) {
			throw new IllegalArgumentException("blockSize must be <= " + MAX_BLOCK_SIZE + ", got " + blockSize);
		}
		int compressionLevel = 32 - Integer.numberOfLeadingZeros(blockSize - 1); // ceil of log2
		assert (1 << compressionLevel) >= blockSize;
		assert (blockSize * 2) > (1 << compressionLevel);
		compressionLevel = Math.max(0, compressionLevel - COMPRESSION_LEVEL_BASE);
		assert (compressionLevel >= 0) && (compressionLevel <= 0x0F);
		return compressionLevel;
	}
	
	private final int blockSize;
	private final int compressionLevel;
	private final LZ4Compressor compressor;
	private final StreamingXXHash32 checksum;
	private final byte[] buffer;
	private final byte[] compressedBuffer;
	private final boolean syncFlush;
	private boolean finished;
	private int o;
	
	public LZ4BlockOutputStream(final OutputStream out) {
		super(out);
		this.blockSize = 1 << 16;
		this.compressor = new LZ4Compressor();
		this.checksum = new StreamingXXHash32(DEFAULT_SEED);
		this.compressionLevel = compressionLevel(blockSize);
		this.buffer = new byte[blockSize];
		final int compressedBlockSize = HEADER_LENGTH + compressor.maxCompressedLength(blockSize);
		this.compressedBuffer = new byte[compressedBlockSize];
		this.syncFlush = false;
		o = 0;
		finished = false;
		System.arraycopy(MAGIC, 0, compressedBuffer, 0, MAGIC_LENGTH);
	}
	
	private void ensureNotFinished() {
		if (finished) {
			throw new IllegalStateException("This stream is already closed");
		}
	}
	
	@Override
	public void write(final int b) throws IOException {
		ensureNotFinished();
		if (o == blockSize) {
			flushBufferedData();
		}
		buffer[o++] = (byte) b;
	}
	
	@Override
	public void write(final byte[] b, int off, int len) throws IOException {
		checkRange(b, off, len);
		ensureNotFinished();
		
		while ((o + len) > blockSize) {
			final int l = blockSize - o;
			System.arraycopy(b, off, buffer, o, blockSize - o);
			o = blockSize;
			flushBufferedData();
			off += l;
			len -= l;
		}
		System.arraycopy(b, off, buffer, o, len);
		o += len;
	}
	
	@Override
	public void write(final byte[] b) throws IOException {
		ensureNotFinished();
		write(b, 0, b.length);
	}
	
	@Override
	public void close() throws IOException {
		if (!finished) {
			finish();
		}
		if (out != null) {
			out.close();
			out = null;
		}
	}
	
	private void flushBufferedData() throws IOException {
		if (o == 0) {
			return;
		}
		checksum.reset();
		checksum.update(buffer, 0, o);
		final int check = (int) checksum.getValue();
		int compressedLength = compressor.compress(buffer, o, compressedBuffer, HEADER_LENGTH);
		final int compressMethod;
		if (compressedLength >= o) {
			compressMethod = COMPRESSION_METHOD_RAW;
			compressedLength = o;
			System.arraycopy(buffer, 0, compressedBuffer, HEADER_LENGTH, o);
		}
		else {
			compressMethod = COMPRESSION_METHOD_LZ4;
		}
		
		compressedBuffer[MAGIC_LENGTH] = (byte) (compressMethod | compressionLevel);
		writeIntLE(compressedLength, compressedBuffer, MAGIC_LENGTH + 1);
		writeIntLE(o, compressedBuffer, MAGIC_LENGTH + 5);
		writeIntLE(check, compressedBuffer, MAGIC_LENGTH + 9);
		assert (MAGIC_LENGTH + 13) == HEADER_LENGTH;
		out.write(compressedBuffer, 0, HEADER_LENGTH + compressedLength);
		o = 0;
	}
	
	@Override
	public void flush() throws IOException {
		if (syncFlush) {
			flushBufferedData();
		}
		out.flush();
	}
	
	public void finish() throws IOException {
		ensureNotFinished();
		flushBufferedData();
		compressedBuffer[MAGIC_LENGTH] = (byte) (COMPRESSION_METHOD_RAW | compressionLevel);
		writeIntLE(0, compressedBuffer, MAGIC_LENGTH + 1);
		writeIntLE(0, compressedBuffer, MAGIC_LENGTH + 5);
		writeIntLE(0, compressedBuffer, MAGIC_LENGTH + 9);
		assert (MAGIC_LENGTH + 13) == HEADER_LENGTH;
		out.write(compressedBuffer, 0, HEADER_LENGTH);
		finished = true;
		out.flush();
	}
	
	private static void writeIntLE(final int i, final byte[] buf, int off) {
		buf[off++] = (byte) i;
		buf[off++] = (byte) (i >>> 8);
		buf[off++] = (byte) (i >>> 16);
		buf[off++] = (byte) (i >>> 24);
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
