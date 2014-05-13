package framework2.base.report.book.xml;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.SwingConstants;

/**
 * 単位をピクセル数に変換します<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/20
 */
@SuppressWarnings("nls")
public abstract class UnitConversion {
	/** エンコードマップ */
	private static final Map<Character, String> encodingMap;
	static {
		encodingMap = new ConcurrentHashMap<>();
		UnitConversion.encodingMap.put('\n', "<br>"); // 復帰コード.
		UnitConversion.encodingMap.put('\r', ""); // 改行コード.
		UnitConversion.encodingMap.put('<', "&lt;"); // 不等号(より小).
		UnitConversion.encodingMap.put('>', "&gt;"); // 不等号(より大).
		UnitConversion.encodingMap.put('&', "&amp;"); // 復帰コード.
		UnitConversion.encodingMap.put('\"', "&quot;"); // 二重引用符.
	}
	/** 列(セル)幅算出係数 */
	private static final double WIDTH_COEFFICIENT = 1.0d;
	/** 行(セル)高さ算出係数 */
	private static final double HEIGHT_COEFFICIENT = 0.9d;

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
	public static double pointsToPixels(final double points,
			final double coefficient) {
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
	public static double pointsToPixels(final double points,
			final double coefficient, final double dpi) {
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
	public static double inchesToPixels(final double inches,
			final double coefficient) {
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
	public static double inchesToPixels(final double inches,
			final double coefficient, final double dpi) {
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
	public static double millimetersToPixels(final double millimeters,
			final double coefficient) {
		return millimetersToPixels(millimeters, coefficient,
				UnitConversion.RESOLUTION);
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
	public static double millimetersToPixels(final double millimeters,
			final double coefficient, final double dpi) {
		return millimeters * dpi * coefficient
				/ UnitConversion.MILLIMETERS_PER_INCH;
	}

	public static Dimension inquirePageSize(final XMLSheet worksheet) {
		// 定数定義.
		final int shortEdgeIndex = 0;
		final int longEdgeIndex = 1;
		final int unit = Size2DSyntax.MM;
		// 用紙辺長さ取得.
		MediaSize paperSize = MediaSize.getMediaSizeForName(worksheet
				.getPaperSize().toMediaSizename());
		float[] edgeLength = paperSize.getSize(unit);
		int shortEdge = getPaperEdgePixels(edgeLength[shortEdgeIndex], unit);
		int longEdge = getPaperEdgePixels(edgeLength[longEdgeIndex], unit);
		// ページサイズ算出.
		int width;
		int height;
		if (OrientationRequested.LANDSCAPE.equals(worksheet.getOrientation())) {
			width = longEdge;
			height = shortEdge;
		}
		else {
			width = shortEdge;
			height = longEdge;
		}
		//
		return new Dimension(width, height);

	}

	/**
	 * 用紙の辺の長さをピクセル数で取得します<br>
	 * 
	 * @param length
	 *            用紙の辺の長さ<br>
	 * @param unit
	 *            長さの単位<br>
	 *            (Size2DSyntax.MM or Size2DSyntax.INCH)<br>
	 * @return
	 */
	private static int getPaperEdgePixels(final double length, final int unit) {
		// 用紙サイズは拡大縮小率の影響を受けない.
		return (int) Math.floor(unit == Size2DSyntax.MM ? UnitConversion
				.millimetersToPixels(length) : UnitConversion
				.inchesToPixels(length));

	}

	public static Insets getMarginPixels(final XMLSheet worksheet,
			final double scaleRate) {
		// 帳票レイアウト XML ファイルでのマージンは、.
		// インチ単位で記述されている.
		// 拡大縮小率によって変更される.
		int top = (int) Math.ceil(UnitConversion.inchesToPixels(
				worksheet.getTopMargin(), scaleRate));
		int left = (int) Math.ceil(UnitConversion.inchesToPixels(
				worksheet.getLeftMargin(), scaleRate));
		int bottom = (int) Math.ceil(UnitConversion.inchesToPixels(
				worksheet.getBottomMargin(), scaleRate));
		int right = (int) Math.ceil(UnitConversion.inchesToPixels(
				worksheet.getRightMargin(), scaleRate));
		return new Insets(top, left, bottom, right);
	}

	// --------------------------------------------------

	/**
	 * 列(セル)の幅をピクセル数で取得します<br>
	 * 
	 * @param width
	 *            列幅 (ポイント指定)<br>
	 * @return
	 */
	public static int getWidthPixels(final double width, final double scaleRate) {
		// 帳票レイアウト XML ファイルでの列(セル)の幅は、.
		// ポイント数で記述されている.
		// 拡大縮小率によって変更される.
		return (int) Math.floor(UnitConversion.pointsToPixels(width,
				UnitConversion.WIDTH_COEFFICIENT * scaleRate));
	}

	// --------------------------------------------------

	/**
	 * 行(セル)の高さをピクセル数で取得します<br>
	 * 
	 * @param height
	 *            行高さ (ポイント指定)<br>
	 * @return
	 */
	public static int getHeightPixels(final double height,
			final double scaleRate) {
		// 帳票レイアウト XML ファイルでの行(セル)の高さは、.
		// ポイント数で記述されている.
		// 拡大縮小率によって変更される.
		return (int) Math.floor(UnitConversion.pointsToPixels(height,
				UnitConversion.HEIGHT_COEFFICIENT * scaleRate));
	}

	// --------------------------------------------------

	/**
	 * セル値をHTML形式に変換します<br>
	 * <br>
	 * 復帰コード文字(\n)を "&lt;br&gt;" に、改行コード文字(\r)を空文字列に変換します<br>
	 * <br>
	 * HTML形式に変換する必要がない場合は、プレーンテキストを返します<br>
	 * 
	 * @oaram str セル値の文字列<br>
	 * @param horizontalAlignment
	 *            横位置を SwingConstants.LEFT、～.CENTER、～.RIGHT から指定します<br>
	 * @return HTML形式の文字列<br>
	 */
	public static String convertToHTMLFormat(final String str,
			final int horizontalAlignment) {
		// HTML形式に変換が必要なのは、改行が含まれる場合のみ.
		if ((str == null)
				|| ((str.indexOf('\n') < 0) && (str.indexOf('\r') < 0))) {
			return str;
		}

		// HTML形式文字列生成.
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		switch (horizontalAlignment) {
		case SwingConstants.RIGHT:
			html.append("<p align='right'>");
			break;
		case SwingConstants.CENTER:
			html.append("<p align='center'>");
			break;
		default:
			html.append("<p align='left'>");
		}
		// エンコード.
		for (int i = 0; i < str.length(); i++) {
			if (UnitConversion.encodingMap.containsKey(str.charAt(i))) {
				// エンコード対象文字.
				html.append(UnitConversion.encodingMap.get(str.charAt(i)));
			}
			else {
				// その他の文字.
				html.append(str.charAt(i));
			}
		}
		//
		html.append("</p>");
		html.append("</html>");
		return html.toString();
	}
}
