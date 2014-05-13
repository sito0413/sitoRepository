package framework2.base.report.book.xml;

import org.w3c.dom.Element;

/**
 * セル<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/18
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
public class XMLCell {

	/** 位置 */
	private final int index;

	/** 下方向への結合数 */
	private final int mergeDown;

	/** 右方向への結合数 */
	private final int mergeAccross;

	/** スタイルID */
	private final String styleId;

	/** セルの値 */
	private String value;

	public XMLCell(final int idx, final Element cellElement) {
		// インデックス
		index = (parseInt(cellElement.getAttribute(XMLConstants.ATTR_INDEX),
				idx + 1));
		// 縦結合.
		mergeDown = (parseInt(
				cellElement.getAttribute(XMLConstants.ATTR_MERGEDOWN), 0));
		// 横結合.
		mergeAccross = (parseInt(
				cellElement.getAttribute(XMLConstants.ATTR_MERGEACROSS), 0));
		// スタイルID
		styleId = (cellElement.getAttribute(XMLConstants.ATTR_STYLEID));
	}

	/**
	 * 位置を取得します<br>
	 * 
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * 下方向への結合数を取得します<br>
	 * 
	 * @return
	 */
	public int getMergeDown() {
		return this.mergeDown;
	}

	/**
	 * 右方向への結合数を取得します<br>
	 * 
	 * @return
	 */
	public int getMergeAccross() {
		return this.mergeAccross;
	}

	/**
	 * スタイルIDを取得します<br>
	 * 
	 * @return
	 */
	public String getStyleId() {
		return this.styleId;
	}

	/**
	 * セルの値を取得します<br>
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * 文字列 s を整数に変換した値を返します<br>
	 * 文字列 s が整数に変換できない場合は、既定値 defaultValue を返します<br>
	 * 
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	private static int parseInt(final String s, final int defaultValue) {
		//
		int result = defaultValue;
		if (XMLCell.isInteger(s)) {
			try {
				result = Integer.parseInt(s);
			}
			catch (NumberFormatException exp) {
				exp.printStackTrace();
			}
		}
		//
		return result;
	}

	/**
	 * 文字列 s が整数に変換可能か判定します<br>
	 * &nbsp;&nbsp;変換可能な文字列 (正規表現) :<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;^-?[0-9]+$<br>
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isInteger(final String s) {
		//
		if ((s == null) || s.isEmpty()) {
			return false;
		}
		boolean result = true;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				continue;
			}
			switch (s.charAt(i)) {
			case '-':
				result = (i == 0);
				break;
			default:
				result = false;
			}
			if (!result) {
				break;
			}
		}
		//
		return result;
	}

}
