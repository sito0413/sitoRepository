package frameWork.base.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import frameWork.base.core.fileSystem.FileSystem;

@SuppressWarnings("nls")
public class Encryption {
	private final static String ALGORITHM = "Blowfish";
	private final static String KEY;
	
	static {
		String keyBuffer = FileSystem.Config.ENCRYPTION_KEY;
		while (keyBuffer.getBytes().length < 16) {
			keyBuffer += " ";
		}
		KEY = keyBuffer;
	}
	
	/**
	 * 暗号化する
	 *
	 * @param String
	 *            暗号化する文字列
	 * @return String 暗号化された文字列
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static String encrypt(final String text) {
		try {
			final SecretKeySpec sksSpec = new SecretKeySpec(KEY.getBytes(), 0, 16, ALGORITHM);
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);
			final byte[] data = cipher.doFinal(text.getBytes());
			return Base64.encode(data);
		}
		catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
		        | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException exp) {
			throw new IllegalStateException(exp);
		}
	}
	
	/**
	 * 複合化する
	 *
	 * @param String
	 *            暗号化された文字列
	 * @return String 複合化された文字列
	 * @throws Exception
	 */
	public static String decrypt(final String strBase64) {
		try {
			final byte[] encrypted = Base64.decode(strBase64);
			final SecretKeySpec sksSpec = new SecretKeySpec(KEY.getBytes(), 0, 16, ALGORITHM);
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, sksSpec);
			return new String(cipher.doFinal(encrypted));
		}
		catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
		        | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException exp) {
			throw new IllegalStateException(exp);
		}
	}
}
