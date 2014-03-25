package frameWork.compressor;

import static frameWork.compressor.Utils.*;

import java.nio.ByteOrder;
import java.util.Arrays;

public class LZ4Compressor {
	private static final int NOT_COMPRESSIBLE_DETECTION_LEVEL = 6;
	private static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();
	private static final int MEMORY_USAGE = 14;
	private static final int HASH_LOG = MEMORY_USAGE - 2;
	private static final int HASH_LOG_64K = HASH_LOG + 1;
	private static final int LAST_LITERALS = 5;
	private static final int MF_LIMIT = COPY_LENGTH + MIN_MATCH;
	private static final int HASH_TABLE_SIZE_64K = 1 << HASH_LOG_64K;
	private static final int SKIP_STRENGTH = Math.max(NOT_COMPRESSIBLE_DETECTION_LEVEL, 2);
	private static final int MIN_LENGTH = MF_LIMIT + 1;
	private static final int LZ4_64K_LIMIT = (1 << 16) + (MF_LIMIT - 1);
	private static final int HASH_TABLE_SIZE = 1 << HASH_LOG;
	private static final int MAX_DISTANCE = 1 << 16;
	
	private static int compress64k(final byte[] src, final int srcLen, final byte[] dest, final int destOff,
	        final int destEnd) {
		final int srcEnd = 0 + srcLen;
		final int srcLimit = srcEnd - LAST_LITERALS;
		final int mflimit = srcEnd - MF_LIMIT;
		
		int sOff = 0, dOff = destOff;
		
		int anchor = sOff;
		
		if (srcLen >= MIN_LENGTH) {
			
			final short[] hashTable = new short[HASH_TABLE_SIZE_64K];
			
			++sOff;
			
			main:
			while (true) {
				
				// find a match
				int forwardOff = sOff;
				
				int ref;
				int findMatchAttempts = (1 << SKIP_STRENGTH) + 3;
				do {
					sOff = forwardOff;
					forwardOff += findMatchAttempts++ >>> SKIP_STRENGTH;
					
					if (forwardOff > mflimit) {
						break main;
					}
					
					final int h = hash64k(readInt(src, sOff));
					ref = 0 + readShort(hashTable, h);
					writeShort(hashTable, h, sOff - 0);
				}
				while (!readIntEquals(src, ref, sOff));
				
				// catch up
				final int excess = commonBytesBackward(src, ref, sOff, 0, anchor);
				sOff -= excess;
				ref -= excess;
				
				// sequence == refsequence
				final int runLen = sOff - anchor;
				
				// encode literal length
				int tokenOff = dOff++;
				
				if ((dOff + runLen + (2 + 1 + LAST_LITERALS) + (runLen >>> 8)) > destEnd) {
					throw new LZ4Exception("maxDestLen is too small");
				}
				
				if (runLen >= RUN_MASK) {
					writeByte(dest, tokenOff, RUN_MASK << ML_BITS);
					dOff = writeLen(runLen - RUN_MASK, dest, dOff);
				}
				else {
					writeByte(dest, tokenOff, runLen << ML_BITS);
				}
				
				// copy literals
				wildArraycopy(src, anchor, dest, dOff, runLen);
				dOff += runLen;
				
				while (true) {
					// encode offset
					writeShortLittleEndian(dest, dOff, (short) (sOff - ref));
					dOff += 2;
					
					// count nb matches
					sOff += MIN_MATCH;
					ref += MIN_MATCH;
					final int matchLen = commonBytes(src, ref, sOff, srcLimit);
					if ((dOff + (1 + LAST_LITERALS) + (matchLen >>> 8)) > destEnd) {
						throw new LZ4Exception("maxDestLen is too small");
					}
					sOff += matchLen;
					
					// encode match len
					if (matchLen >= ML_MASK) {
						writeByte(dest, tokenOff, dest[tokenOff] | ML_MASK);
						dOff = writeLen(matchLen - ML_MASK, dest, dOff);
					}
					else {
						writeByte(dest, tokenOff, dest[tokenOff] | matchLen);
					}
					
					// test end of chunk
					if (sOff > mflimit) {
						anchor = sOff;
						break main;
					}
					
					// fill table
					writeShort(hashTable, hash64k(readInt(src, sOff - 2)), sOff - 2 - 0);
					
					// test next position
					final int h = hash64k(readInt(src, sOff));
					ref = 0 + readShort(hashTable, h);
					writeShort(hashTable, h, sOff - 0);
					
					if (!readIntEquals(src, sOff, ref)) {
						break;
					}
					
					tokenOff = dOff++;
					dest[tokenOff] = 0;
				}
				
				// prepare next loop
				anchor = sOff++;
			}
		}
		
		dOff = lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
		return dOff - destOff;
	}
	
	private int compress(final byte[] src, final int srcLen, final byte[] dest, final int destOff, final int maxDestLen) {
		checkRange(src, 0, srcLen);
		checkRange(dest, destOff, maxDestLen);
		final int destEnd = destOff + maxDestLen;
		
		if (srcLen < LZ4_64K_LIMIT) {
			return compress64k(src, srcLen, dest, destOff, destEnd);
		}
		
		final int srcEnd = 0 + srcLen;
		final int srcLimit = srcEnd - LAST_LITERALS;
		final int mflimit = srcEnd - MF_LIMIT;
		
		int sOff = 0, dOff = destOff;
		int anchor = sOff++;
		
		final int[] hashTable = new int[HASH_TABLE_SIZE];
		Arrays.fill(hashTable, anchor);
		
		main:
		while (true) {
			
			// find a match
			int forwardOff = sOff;
			
			int ref;
			int findMatchAttempts = (1 << SKIP_STRENGTH) + 3;
			int back;
			do {
				sOff = forwardOff;
				forwardOff += findMatchAttempts++ >>> SKIP_STRENGTH;
				
				if (forwardOff > mflimit) {
					break main;
				}
				
				final int h = hash(readInt(src, sOff));
				ref = readInt(hashTable, h);
				back = sOff - ref;
				writeInt(hashTable, h, sOff);
			}
			while ((back >= MAX_DISTANCE) || !readIntEquals(src, ref, sOff));
			
			final int excess = commonBytesBackward(src, ref, sOff, 0, anchor);
			sOff -= excess;
			ref -= excess;
			
			// sequence == refsequence
			final int runLen = sOff - anchor;
			
			// encode literal length
			int tokenOff = dOff++;
			
			if ((dOff + runLen + (2 + 1 + LAST_LITERALS) + (runLen >>> 8)) > destEnd) {
				throw new LZ4Exception("maxDestLen is too small");
			}
			
			if (runLen >= RUN_MASK) {
				writeByte(dest, tokenOff, RUN_MASK << ML_BITS);
				dOff = writeLen(runLen - RUN_MASK, dest, dOff);
			}
			else {
				writeByte(dest, tokenOff, runLen << ML_BITS);
			}
			
			// copy literals
			wildArraycopy(src, anchor, dest, dOff, runLen);
			dOff += runLen;
			
			while (true) {
				// encode offset
				writeShortLittleEndian(dest, dOff, back);
				dOff += 2;
				
				// count nb matches
				sOff += MIN_MATCH;
				final int matchLen = commonBytes(src, ref + MIN_MATCH, sOff, srcLimit);
				if ((dOff + (1 + LAST_LITERALS) + (matchLen >>> 8)) > destEnd) {
					throw new LZ4Exception("maxDestLen is too small");
				}
				sOff += matchLen;
				
				// encode match len
				if (matchLen >= ML_MASK) {
					writeByte(dest, tokenOff, dest[tokenOff] | ML_MASK);
					dOff = writeLen(matchLen - ML_MASK, dest, dOff);
				}
				else {
					writeByte(dest, tokenOff, dest[tokenOff] | matchLen);
				}
				
				// test end of chunk
				if (sOff > mflimit) {
					anchor = sOff;
					break main;
				}
				
				// fill table
				writeInt(hashTable, hash(readInt(src, sOff - 2)), sOff - 2);
				
				// test next position
				final int h = hash(readInt(src, sOff));
				ref = readInt(hashTable, h);
				writeInt(hashTable, h, sOff);
				back = sOff - ref;
				
				if ((back >= MAX_DISTANCE) || !readIntEquals(src, ref, sOff)) {
					break;
				}
				
				tokenOff = dOff++;
				writeByte(dest, tokenOff, 0);
			}
			
			// prepare next loop
			anchor = sOff++;
		}
		
		dOff = lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
		return dOff - destOff;
	}
	
	@SuppressWarnings("static-method")
	public final int maxCompressedLength(final int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length must be >= 0, got " + length);
		}
		return length + (length / 255) + 16;
	}
	
	public final int compress(final byte[] src, final int srcLen, final byte[] dest, final int destOff) {
		return compress(src, srcLen, dest, destOff, dest.length - destOff);
	}
	
	private static int lastLiterals(final byte[] src, final int sOff, final int srcLen, final byte[] dest, int dOff,
	        final int destEnd) {
		final int runLen = srcLen;
		
		if ((dOff + runLen + 1 + (((runLen + 255) - RUN_MASK) / 255)) > destEnd) {
			throw new LZ4Exception();
		}
		
		if (runLen >= RUN_MASK) {
			dest[dOff++] = (byte) (RUN_MASK << ML_BITS);
			dOff = writeLen(runLen - RUN_MASK, dest, dOff);
		}
		else {
			dest[dOff++] = (byte) (runLen << ML_BITS);
		}
		// copy literals
		System.arraycopy(src, sOff, dest, dOff, runLen);
		dOff += runLen;
		
		return dOff;
	}
	
	private static boolean readIntEquals(final byte[] buf, final int i, final int j) {
		return (buf[i] == buf[j]) && (buf[i + 1] == buf[j + 1]) && (buf[i + 2] == buf[j + 2])
		        && (buf[i + 3] == buf[j + 3]);
	}
	
	private static int writeLen(int len, final byte[] dest, int dOff) {
		while (len >= 0xFF) {
			dest[dOff++] = (byte) 0xFF;
			len -= 0xFF;
		}
		dest[dOff++] = (byte) len;
		return dOff;
	}
	
	private static int hash(final int i) {
		return (i * -1640531535) >>> ((MIN_MATCH * 8) - HASH_LOG);
	}
	
	private static int commonBytes(final byte[] b, int o1, int o2, final int limit) {
		int count = 0;
		while ((o2 < limit) && (b[o1++] == b[o2++])) {
			++count;
		}
		return count;
	}
	
	private static int hash64k(final int i) {
		return (i * -1640531535) >>> ((MIN_MATCH * 8) - HASH_LOG_64K);
	}
	
	private static int commonBytesBackward(final byte[] b, int o1, int o2, final int l1, final int l2) {
		int count = 0;
		while ((o1 > l1) && (o2 > l2) && (b[--o1] == b[--o2])) {
			++count;
		}
		return count;
	}
	
	private static int readInt(final byte[] buf, final int i) {
		if (NATIVE_BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
			return readIntBE(buf, i);
		}
		return readIntLE(buf, i);
	}
	
	private static int readIntBE(final byte[] buf, final int i) {
		return ((buf[i] & 0xFF) << 24) | ((buf[i + 1] & 0xFF) << 16) | ((buf[i + 2] & 0xFF) << 8) | (buf[i + 3] & 0xFF);
	}
	
	private static void writeShortLittleEndian(final byte[] buf, int off, final int v) {
		buf[off++] = (byte) v;
		buf[off++] = (byte) (v >>> 8);
	}
	
	private static void writeInt(final int[] buf, final int off, final int v) {
		buf[off] = v;
	}
	
	private static int readInt(final int[] buf, final int off) {
		return buf[off];
	}
	
	private static void writeByte(final byte[] dest, final int tokenOff, final int i) {
		dest[tokenOff] = (byte) i;
	}
	
	private static void writeShort(final short[] buf, final int off, final int v) {
		buf[off] = (short) v;
	}
	
	private static int readShort(final short[] buf, final int off) {
		return buf[off] & 0xFFFF;
	}
	
	private static void copy8Bytes(final byte[] src, final int sOff, final byte[] dest, final int dOff) {
		for (int i = 0; i < 8; ++i) {
			dest[dOff + i] = src[sOff + i];
		}
	}
	
	private static void wildArraycopy(final byte[] src, final int sOff, final byte[] dest, final int dOff, final int len) {
		try {
			for (int i = 0; i < len; i += 8) {
				copy8Bytes(src, sOff + i, dest, dOff + i);
			}
		}
		catch (final ArrayIndexOutOfBoundsException e) {
			throw new LZ4Exception("Malformed input at offset " + sOff);
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
