package framework2.base.report.reader;

import javax.swing.SwingConstants;

/**
 * 縦位置<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/19
 */
@SuppressWarnings( { "nls", "unused" })
public class XMLVerticalAlignment {

	/** 標準 */
	private static final String AUTOMATIC = "Automatic";

	/** 上詰め */
	private static final String TOP = "Top";

	/** 下詰め */
	private static final String BOTTOM = "Bottom";

	/** 中央揃え */
	private static final String CENTER = "Center";

	/** 両端揃え */
	private static final String JUSTIFY = "Justify";

	/** 均等割り付け */
	private static final String DISTRIBUTED = "Distributed";

	/** ？？？ */
	private static final String JUSTIFY_DISTRIBUTED = "JustifyDistributed";

	public static int getDefaultValue() {
		return SwingConstants.TOP;
	}

	public static int parse(final Object obj) {
		int result = getDefaultValue();

		if (obj == null) {
			return result;
		}

		String s = String.valueOf(obj);
		if ((CENTER.equals(s)) || (JUSTIFY.equals(s)) || (DISTRIBUTED.equals(s)) || (JUSTIFY_DISTRIBUTED.equals(s))) {
			result = SwingConstants.CENTER;
		}
		else if (BOTTOM.equals(s)) {
			result = SwingConstants.BOTTOM;
		}
		else {
			result = SwingConstants.TOP;
		}
		return result;
	}

}
