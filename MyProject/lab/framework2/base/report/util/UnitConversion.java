package framework2.base.report.util;

/**
 * 単位をピクセル数に変換します<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/20
 */
public abstract class UnitConversion {

	/** 解像度 (DPI) */
	private static final double RESOLUTION = 72d;

	/** 1インチの長さ(ミリメートル) */
	private static final double MILLIMETERS_PER_INCH = 25.4d;

	// --------------------------------------------------

	/**
	 * ポイント数からピクセル数を算出します<br>
	 * 
	 * @param points
	 *            ポイント数<br>
	 * @return ピクセル数<br>
	 */
	public static double pointsToPixels(final double points) {
		return pointsToPixels(points, 1d);
	}

	/**
	 * ポイント数からピクセル数を算出します<br>
	 * 
	 * @param points
	 *            ポイント数<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @return ピクセル数<br>
	 */
	public static double pointsToPixels(final double points, final double coefficient) {
		return pointsToPixels(points, coefficient, UnitConversion.RESOLUTION);
	}

	/**
	 * ポイント数からピクセル数を算出します<br>
	 * 
	 * @param points
	 *            ポイント数<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @param dpi
	 *            解像度<br>
	 * @return ピクセル数<br>
	 */
	public static double pointsToPixels(final double points, final double coefficient, final double dpi) {
		return points * dpi * coefficient / UnitConversion.RESOLUTION;
	}

	// --------------------------------------------------

	/**
	 * 長さ(インチ)からピクセル数を算出します<br>
	 * 
	 * @param inches
	 *            長さ(インチ)<br>
	 * @return ピクセル数<br>
	 */
	public static double inchesToPixels(final double inches) {
		return inchesToPixels(inches, 1d);
	}

	/**
	 * 長さ(インチ)からピクセル数を算出します<br>
	 * 
	 * @param inches
	 *            長さ(インチ)<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @return ピクセル数<br>
	 */
	public static double inchesToPixels(final double inches, final double coefficient) {
		return inchesToPixels(inches, coefficient, UnitConversion.RESOLUTION);
	}

	/**
	 * 長さ(インチ)からピクセル数を算出します<br>
	 * 
	 * @param inches
	 *            長さ(インチ)<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @param dpi
	 *            解像度<br>
	 * @return ピクセル数<br>
	 */
	public static double inchesToPixels(final double inches, final double coefficient, final double dpi) {
		return inches * dpi * coefficient;
	}

	// --------------------------------------------------

	/**
	 * 長さ(ミリメートル)からピクセル数を算出します<br>
	 * 
	 * @param millimeters
	 *            長さ(ミリメートル)<br>
	 * @return ピクセル数<br>
	 */
	public static double millimetersToPixels(final double millimeters) {
		return millimetersToPixels(millimeters, 1d);
	}

	/**
	 * 長さ(ミリメートル)からピクセル数を算出します<br>
	 * 
	 * @param millimeters
	 *            長さ(ミリメートル)<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @return ピクセル数<br>
	 */
	public static double millimetersToPixels(final double millimeters, final double coefficient) {
		return millimetersToPixels(millimeters, coefficient, UnitConversion.RESOLUTION);
	}

	/**
	 * 長さ(ミリメートル)からピクセル数を算出します<br>
	 * 
	 * @param millimeters
	 *            長さ(ミリメートル)<br>
	 * @param coefficient
	 *            係数 (1.00 = 100%)<br>
	 * @param dpi
	 *            解像度<br>
	 * @return ピクセル数<br>
	 */
	public static double millimetersToPixels(final double millimeters, final double coefficient, final double dpi) {
		return millimeters * dpi * coefficient / UnitConversion.MILLIMETERS_PER_INCH;
	}

}
