package frameWork.base.encryption;

import java.io.UnsupportedEncodingException;

import frameWork.base.core.fileSystem.FileSystem;

final class Base64 {
	private static final char pem_array[];
	private static final byte pem_convert_array[];
	static {
		pem_array = new char[] {
		        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
		        '8', '9', '+', '/'
		};
		pem_convert_array = new byte[256];
		for (int i = 0; i < 255; i++) {
			Base64.pem_convert_array[i] = -1;
		}
		for (int i = 0; i < Base64.pem_array.length; i++) {
			Base64.pem_convert_array[Base64.pem_array[i]] = (byte) i;
		}
	}
	
	/**
	 * BASE64暗号化
	 *
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static String encode(final byte inbuf[]) throws UnsupportedEncodingException {
		if (inbuf.length != 0) {
			int remainder = inbuf.length;
			final byte outbuf[] = new byte[((remainder + 2) / 3) * 4];
			int inpos = 0;
			int outpos;
			for (outpos = 0; remainder >= 3; outpos += 4) {
				int val = inbuf[inpos++] & 255;
				val <<= 8;
				val |= inbuf[inpos++] & 255;
				val <<= 8;
				val |= inbuf[inpos++] & 255;
				outbuf[outpos + 3] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 2] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 1] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 0] = (byte) Base64.pem_array[val & 63];
				remainder -= 3;
			}
			if (remainder == 1) {
				int val = inbuf[inpos++] & 255;
				val <<= 4;
				outbuf[outpos + 3] = 61;
				outbuf[outpos + 2] = 61;
				outbuf[outpos + 1] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 0] = (byte) Base64.pem_array[val & 63];
			}
			else if (remainder == 2) {
				int val = inbuf[inpos++] & 255;
				val <<= 8;
				val |= inbuf[inpos++] & 255;
				val <<= 2;
				outbuf[outpos + 3] = 61;
				outbuf[outpos + 2] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 1] = (byte) Base64.pem_array[val & 63];
				val >>= 6;
				outbuf[outpos + 0] = (byte) Base64.pem_array[val & 63];
			}
			return new String(outbuf, FileSystem.Config.ENCRYPTION_BASE64_ENCODING);
		}
		return "";
	}
	
	/**
	 * BASE64復号化
	 *
	 * @param value
	 * @return
	 * @throws IOExceptionString
	 *             IO異常
	 * @throws Exception
	 */
	public static byte[] decode(final String inString) throws UnsupportedEncodingException {
		final byte[] inbuf = inString.getBytes(FileSystem.Config.ENCRYPTION_BASE64_ENCODING);
		int size = (inbuf.length / 4) * 3;
		if (size == 0) {
			return inbuf;
		}
		
		if (inbuf[inbuf.length - 1] == 61) {
			size--;
			if (inbuf[inbuf.length - 2] == 61) {
				size--;
			}
		}
		
		final byte outbuf[] = new byte[size];
		int inpos = 0;
		int outpos = 0;
		for (size = inbuf.length; size > 0; size -= 4) {
			int osize = 3;
			int val = Base64.pem_convert_array[inbuf[inpos++] & 255];
			val <<= 6;
			val |= Base64.pem_convert_array[inbuf[inpos++] & 255];
			val <<= 6;
			if (inbuf[inpos] != 61) {
				val |= Base64.pem_convert_array[inbuf[inpos++] & 255];
			}
			else {
				osize--;
			}
			val <<= 6;
			if (inbuf[inpos] != 61) {
				val |= Base64.pem_convert_array[inbuf[inpos++] & 255];
			}
			else {
				osize--;
			}
			if (osize > 2) {
				outbuf[outpos + 2] = (byte) (val & 255);
			}
			val >>= 8;
			if (osize > 1) {
				outbuf[outpos + 1] = (byte) (val & 255);
			}
			val >>= 8;
			outbuf[outpos] = (byte) (val & 255);
			outpos += osize;
		}
		return outbuf;
	}
}