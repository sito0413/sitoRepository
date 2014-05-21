package frameWork.base.print.reader.xml;

/**
 * 単位をピクセル数に変換します<br>
 */
abstract class UnitConversion {
	
	/**
	 * ポイント数からピクセル数を算出します<br>
	 */
	static double pointsToPixels(final double points, final double coefficient) {
		return (points * coefficient);
	}
	
	/**
	 * 長さ(インチ)からピクセル数を算出します<br>
	 */
	static double inchesToPixels(final double inches) {
		return inches * XMLConstants.RESOLUTION;
	}
	
	/**
	 * 長さ(インチ)からピクセル数を算出します<br>
	 */
	static double inchesToPixels(final double inches, final double coefficient) {
		return inches * XMLConstants.RESOLUTION * coefficient;
	}
	
	/**
	 * 長さ(ミリメートル)からピクセル数を算出します<br>
	 */
	static double millimetersToPixels(final double millimeters) {
		return (millimeters * XMLConstants.RESOLUTION) / XMLConstants.MILLIMETERS_PER_INCH;
	}
	
	/**
	 * 文字列 s を実数に変換した値を返します<br>
	 * 文字列 s が実数に変換できない場合は、既定値 defaultValue を返します<br>
	 */
	static double parseDouble(final String s, final double defaultValue) {
		try {
			return Double.parseDouble(s);
		}
		catch (final NumberFormatException exp) {
			return defaultValue;
		}
	}
	
	/**
	 * 文字列 s を整数に変換した値を返します<br>
	 * 文字列 s が整数に変換できない場合は、既定値 defaultValue を返します<br>
	 */
	static int parseInt(final String s, final int defaultValue) {
		try {
			return Integer.parseInt(s);
		}
		catch (final NumberFormatException exp) {
			return defaultValue;
		}
	}
}
