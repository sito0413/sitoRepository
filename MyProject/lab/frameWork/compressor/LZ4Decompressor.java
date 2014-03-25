package frameWork.compressor;

import static frameWork.compressor.Utils.*;

public class LZ4Decompressor {
	
	public int decompress(final byte[] src, final byte[] dest, final int destLen) {
		if ((0 >= src.length)) {
			throw new ArrayIndexOutOfBoundsException(0);
		}
		checkRange(dest, 0, destLen);
		if (destLen == 0) {
			if (src[0] != 0) {
				throw new LZ4Exception("Malformed input at " + 0);
			}
			return 1;
		}
		
		final int destEnd = 0 + destLen;
		
		int sOff = 0;
		int dOff = 0;
		
		while (true) {
			final int token = src[sOff] & 0xFF;
			++sOff;
			
			// literals
			int literalLen = token >>> ML_BITS;
			if (literalLen == RUN_MASK) {
				byte len = (byte) 0xFF;
				while ((len = src[sOff++]) == (byte) 0xFF) {
					literalLen += 0xFF;
				}
				literalLen += len & 0xFF;
			}
			
			final int literalCopyEnd = dOff + literalLen;
			
			if (literalCopyEnd > (destEnd - COPY_LENGTH)) {
				if (literalCopyEnd != destEnd) {
					throw new LZ4Exception("Malformed input at " + sOff);
				}
				safeArraycopy(src, sOff, dest, dOff, literalLen);
				sOff += literalLen;
				dOff = literalCopyEnd;
				break; // EOF
			}
			
			wildArraycopy(src, sOff, dest, dOff, literalLen);
			sOff += literalLen;
			dOff = literalCopyEnd;
			
			// matchs
			final int matchDec = readShortLittleEndian(src, sOff);
			sOff += 2;
			final int matchOff = dOff - matchDec;
			
			if (matchOff < 0) {
				throw new LZ4Exception("Malformed input at " + sOff);
			}
			
			int matchLen = token & ML_MASK;
			if (matchLen == ML_MASK) {
				byte len = (byte) 0xFF;
				while ((len = src[sOff++]) == (byte) 0xFF) {
					matchLen += 0xFF;
				}
				matchLen += len & 0xFF;
			}
			matchLen += MIN_MATCH;
			
			final int matchCopyEnd = dOff + matchLen;
			
			if (matchCopyEnd > (destEnd - COPY_LENGTH)) {
				if (matchCopyEnd > destEnd) {
					throw new LZ4Exception("Malformed input at " + sOff);
				}
				safeIncrementalCopy(dest, matchOff, dOff, matchLen);
			}
			else {
				wildIncrementalCopy(dest, matchOff, dOff, matchCopyEnd);
			}
			dOff = matchCopyEnd;
		}
		return sOff - 0;
	}
	
	private static void safeArraycopy(final byte[] src, final int sOff, final byte[] dest, final int dOff, final int len) {
		System.arraycopy(src, sOff, dest, dOff, len);
	}
	
	private static int readShortLittleEndian(final byte[] buf, final int i) {
		return (buf[i] & 0xFF) | ((buf[i + 1] & 0xFF) << 8);
	}
	
	private static void safeIncrementalCopy(final byte[] dest, final int matchOff, final int dOff, final int matchLen) {
		for (int i = 0; i < matchLen; ++i) {
			dest[dOff + i] = dest[matchOff + i];
		}
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
	
	private static void wildIncrementalCopy(final byte[] dest, int matchOff, int dOff, final int matchCopyEnd) {
		do {
			copy8Bytes(dest, matchOff, dest, dOff);
			matchOff += 8;
			dOff += 8;
		}
		while (dOff < matchCopyEnd);
	}
}