package frameWork.base.barcode;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public final class Barcode {
	static {
		try {
			BarcodeFont = Font.createFont(
			        Font.TRUETYPE_FONT,
			        Barcode.class.getClassLoader().getResourceAsStream(
			                Barcode.class.getPackage().getName().replace('.', '/') + "/3of9.ttf"));
		}
		catch (FontFormatException | IOException e) {
			BarcodeFont = null;
		}
	}
	private static Font BarcodeFont;
	static String EXTEND = "*";
	
	public static Font getBarcodeFont(final float size) {
		return BarcodeFont.deriveFont(size * 4);
	}
	
	public static String createBarcode(final String text) {
		if (text.isEmpty()) {
			return text;
		}
		return EXTEND + text + EXTEND;
		
	}
}
