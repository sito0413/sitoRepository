package framework2.base.report.reader;

import javax.swing.SwingConstants;

/**
 * 横位置<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/19
 */
@SuppressWarnings( { "nls", "unused" })
public class XMLHorizontalAlignment {
	/** 標準 */
	private static final String AUTOMATIC = "Automatic";

	/** 左詰め (インデント) */
	private static final String LEFT = "Left";
	//
	/** 中央揃え */
	private static final String CENTER = "Center";

	/** 右詰め (インデント) */
	private static final String RIGHT = "Right";

	/** 繰り返し */
	private static final String FILL = "Fill";

	/** 両端揃え */
	private static final String JUSTIFY = "Justify";

	/** 選択範囲内で中央 */
	private static final String CENTER_ACCROSS_SELECTION = "CenterAcrossSelection";

	/** 均等割り付け (インデント) */
	private static final String DISTRIBUTED = "Distributed";

	/** ？？？ */
	private static final String JUSTIFY_DISTRIBUTED = "JustifyDistributed";

	public static int parse(final Object obj) {
		if (obj == null) {
			return getDefaultValue();
		}

		int result = SwingConstants.LEFT;
		String s = String.valueOf(obj);
		if ((CENTER.equalsIgnoreCase(s)) || (JUSTIFY.equalsIgnoreCase(s)) || (CENTER_ACCROSS_SELECTION.equalsIgnoreCase(s))
		        || (DISTRIBUTED.equalsIgnoreCase(s)) || (JUSTIFY_DISTRIBUTED.equalsIgnoreCase(s))) {
			result = SwingConstants.CENTER;
		}
		else if (RIGHT.equalsIgnoreCase(s)) {
			result = SwingConstants.RIGHT;
		}
		else {
			result = SwingConstants.LEFT;
		}

		return result;
	}

	public static int getDefaultValue() {
		return SwingConstants.LEFT;
	}
}
