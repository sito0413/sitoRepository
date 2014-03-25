package frameWork.compressor;

import static java.lang.Integer.*;

public class StreamingXXHash32 {
	private static final int PRIME1 = -1640531535;
	private static final int PRIME2 = -2048144777;
	private static final int PRIME3 = -1028477379;
	private static final int PRIME4 = 668265263;
	private static final int PRIME5 = 374761393;
	
	private int v1;
	private int v2;
	private int v3;
	private int v4;
	private int memSize;
	private long totalLen;
	private final byte[] memory;
	
	private final int seed;
	
	StreamingXXHash32(final int seed) {
		this.seed = seed;
		memory = new byte[16];
		reset();
	}
	
	void reset() {
		v1 = seed + PRIME1 + PRIME2;
		v2 = seed + PRIME2;
		v3 = seed + 0;
		v4 = seed - PRIME1;
		totalLen = 0;
		memSize = 0;
	}
	
	long getValue() {
		int h32;
		if (totalLen >= 16) {
			h32 = rotateLeft(v1, 1) + rotateLeft(v2, 7) + rotateLeft(v3, 12) + rotateLeft(v4, 18);
		}
		else {
			h32 = seed + PRIME5;
		}
		
		h32 += totalLen;
		
		int off = 0;
		while (off <= (memSize - 4)) {
			h32 += readIntLE(memory, off) * PRIME3;
			h32 = rotateLeft(h32, 17) * PRIME4;
			off += 4;
		}
		
		while (off < memSize) {
			h32 += (memory[off] & 0xFF) * PRIME5;
			h32 = rotateLeft(h32, 11) * PRIME1;
			++off;
		}
		
		h32 ^= h32 >>> 15;
		h32 *= PRIME2;
		h32 ^= h32 >>> 13;
		h32 *= PRIME3;
		h32 ^= h32 >>> 16;
		
		return h32 & 0xFFFFFFFL;
	}
	
	void update(final byte[] buf, int off, final int len) {
		checkRange(buf, off, len);
		
		totalLen += len;
		
		if ((memSize + len) < 16) { // fill in tmp buffer
			System.arraycopy(buf, off, memory, memSize, len);
			memSize += len;
			return;
		}
		
		final int end = off + len;
		
		if (memSize > 0) { // data left from previous update
			System.arraycopy(buf, off, memory, memSize, 16 - memSize);
			
			v1 += readIntLE(memory, 0) * PRIME2;
			v1 = rotateLeft(v1, 13);
			v1 *= PRIME1;
			
			v2 += readIntLE(memory, 4) * PRIME2;
			v2 = rotateLeft(v2, 13);
			v2 *= PRIME1;
			
			v3 += readIntLE(memory, 8) * PRIME2;
			v3 = rotateLeft(v3, 13);
			v3 *= PRIME1;
			
			v4 += readIntLE(memory, 12) * PRIME2;
			v4 = rotateLeft(v4, 13);
			v4 *= PRIME1;
			
			off += 16 - memSize;
			memSize = 0;
		}
		
		{
			final int limit = end - 16;
			int _v1 = this.v1;
			int _v2 = this.v2;
			int _v3 = this.v3;
			int _v4 = this.v4;
			
			while (off <= limit) {
				_v1 += readIntLE(buf, off) * PRIME2;
				_v1 = rotateLeft(_v1, 13);
				_v1 *= PRIME1;
				off += 4;
				
				_v2 += readIntLE(buf, off) * PRIME2;
				_v2 = rotateLeft(_v2, 13);
				_v2 *= PRIME1;
				off += 4;
				
				_v3 += readIntLE(buf, off) * PRIME2;
				_v3 = rotateLeft(_v3, 13);
				_v3 *= PRIME1;
				off += 4;
				
				_v4 += readIntLE(buf, off) * PRIME2;
				_v4 = rotateLeft(_v4, 13);
				_v4 *= PRIME1;
				off += 4;
			}
			
			this.v1 = _v1;
			this.v2 = _v2;
			this.v3 = _v3;
			this.v4 = _v4;
		}
		
		if (off < end) {
			System.arraycopy(buf, off, memory, 0, end - off);
			memSize = end - off;
		}
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