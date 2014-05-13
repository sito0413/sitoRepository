package framework2.base.report.book.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * カラム<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/18
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
@SuppressWarnings("nls")
public class XMLColumn {

	/** 位置 */
	private int index;

	/** 繰り返し数 */
	private int span;

	/** 幅 */
	private double width;

	/** スタイル */
	private String styleId;

	/**
	 * 既定値の列情報を取得します<br>
	 * 
	 * @param index
	 * @param span
	 * @param width
	 * @return
	 */
	public XMLColumn(final int index, final int span, final double width) {
		initialize(index, span, width);
	}

	private void initialize(final int index2, final int span2,
			final double width2) {
		this.index = index2;
		// スパン.
		this.span = span2;
		// 列幅.
		this.width = width2;
		// スタイルID.
		this.styleId = "";

	}

	public XMLColumn(final Node columnNode, final int index,
			final double defaultWidth) {
		// 列情報が存在しない場合は、既定値の列情報を返す.
		if (columnNode == null) {
			initialize(index, 0, defaultWidth);
			return;
		}
		// 列情報変換.
		Element columnElement = (Element) columnNode;
		// インデックス.
		this.index = (parseInt(
				columnElement.getAttribute(XMLConstants.ATTR_INDEX), index));
		// スパン.
		this.span = (parseInt(
				columnElement.getAttribute(XMLConstants.ATTR_SPAN), 0));
		// 列幅.
		this.width = (parseDouble(
				columnElement.getAttribute(XMLConstants.ATTR_WIDTH),
				defaultWidth));
		// スタイルID.
		this.styleId = (columnElement.getAttribute(XMLConstants.ATTR_STYLEID));
	}

	// --------------------------------------------------
	/**
	 * 位置を取得します<br>
	 * 
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}

	// --------------------------------------------------
	/**
	 * 繰り返し数を取得します<br>
	 * 
	 * @return
	 */
	public int getSpan() {
		return this.span;
	}

	// --------------------------------------------------
	/**
	 * 幅(ポイント数)を取得します<br>
	 * 
	 * @return
	 */
	public double getWidth() {
		return this.width;
	}

	// --------------------------------------------------
	/**
	 * スタイルを取得します<br>
	 * 
	 * @return
	 */
	public String getStyleId() {
		return this.styleId;
	}

	// --------------------------------------------------
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
		if (XMLColumn.isInteger(s)) {
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

	/**
	 * 文字列 s を実数に変換した値を返します<br>
	 * 文字列 s が実数に変換できない場合は、既定値 defaultValue を返します<br>
	 * 
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	private static double parseDouble(final String s, final double defaultValue) {
		//
		double result = defaultValue;
		if (isDouble(s)) {
			try {
				result = Double.parseDouble(s);
			}
			catch (NumberFormatException exp) {
				exp.printStackTrace();
			}
		}
		//
		return result;
	}

	/**
	 * 文字列 s が実数に変換可能か判定します<br>
	 * &nbsp;&nbsp;変換可能な文字列 (正規表現) :<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;^
	 * *[+-]?([0-9]+(\.[0-9]*)?|[0-9]*\.[0-9]+)([Ee][0-9]+)? *$<br>
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isDouble(final String s) {
		//
		if ((s == null) || s.isEmpty()) {
			return false;
		}
		boolean result = true;
		String str = s.trim();
		int digits = 0;
		int periods = 0;
		int exponents = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				digits++;
				continue;
			}
			switch (str.charAt(i)) {
			case '+':
			case '-':
				result = (i == 0);
				break;
			case '.':
				result = (periods == 0) && (exponents == 0);
				periods++;
				break;
			case 'E':
			case 'e':
				result = (i != str.length() - 1) && (digits != 0)
						&& (exponents == 0);
				exponents++;
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
