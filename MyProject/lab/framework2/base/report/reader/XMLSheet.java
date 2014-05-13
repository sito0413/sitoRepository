package framework2.base.report.reader;

import javax.print.attribute.standard.OrientationRequested;

import org.w3c.dom.Element;

import framework2.base.report.util.XMLConstants;

/**
 * ワークシート<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/15
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
public class XMLSheet {

	/** 用紙サイズの既定値 */
	private static final XMLPaperSize DEFAULT_PAPER_SIZE = XMLPaperSize.A4;
	/** 用紙方向の既定値 */
	private static final OrientationRequested DEFAULT_ORIENTATION = OrientationRequested.PORTRAIT;
	/** ヘッダマージンの既定値 */
	private static final double DEFAULT_HEADER_MARGIN = 0.50d;
	/** トップマージンの既定値 */
	private static final double DEFAULT_TOP_MARGIN = 1.00d;
	/** レフトマージンの既定値 */
	private static final double DEFAULT_LEFT_MARGIN = 0.75d;
	/** ライトマージンの既定値 */
	private static final double DEFAULT_RIGHT_MARGIN = 0.75d;
	/** ボトムマージンの既定値 */
	private static final double DEFAULT_BOTTOM_MARGIN = 1.00d;
	/** フッタマージンの既定値 */
	private static final double DEFAULT_FOOTER_MARGIN = 0.50d;
	/** スケールの既定値 */
	private static final double DEFAULT_SCALE = 100.00d;

	/** 用紙サイズ */
	private XMLPaperSize paperSize;

	/** 用紙方向 */
	private OrientationRequested orientation;

	/** ヘッダマージン(インチ数) */
	private double headerMargin;

	/** トップマージン(インチ数) */
	private double topMargin;

	/** レフトマージン(インチ数) */
	private double leftMargin;

	/** ライトマージン(インチ数) */
	private double rightMargin;

	/** ボトムマージン(インチ数) */
	private double bottomMargin;

	/** フッタマージン(インチ数) */
	private double footerMargin;

	/** スケール */
	private double scale;

	public XMLSheet(final Element worksheetElement) {
		// テーブル生成＆属性設定.
		XMLTable newTable = new XMLTable(worksheetElement.getElementsByTagName(XMLConstants.ELM_TABLE).item(0));
		setTable(newTable);
		// オプション生成＆属性設定.
		this.initialize((Element) worksheetElement.getElementsByTagName(XMLConstants.ELM_WSOPTIONS).item(0));
	}

	// --------------------------------------------------

	/**
	 * 初期化<br>
	 */
	@SuppressWarnings("nls")
	private void initialize(final Element optionsElement) {
		paperSize = (DEFAULT_PAPER_SIZE);
		orientation = (DEFAULT_ORIENTATION);
		scale = (DEFAULT_SCALE);

		//
		if (optionsElement == null) {
			return;
		}
		// --------------------------------------------------
		// note
		// ELMOptionsオブジェクトを生成時に、各メンバ.
		// 既定値が設定されているので、数値項目変換時の.
		// 既定値として、それぞれの現在値を使用する.
		// --------------------------------------------------
		// ページセットアップ (用紙方向 & 各マージン).
		Element pageSetupElement = (Element) optionsElement.getElementsByTagName(XMLConstants.ELM_PAGESETUP).item(0);
		if (pageSetupElement != null) {
			// 用紙方向.
			Element layoutElement = (Element) pageSetupElement.getElementsByTagName(XMLConstants.ELM_LAYOUT).item(0);
			if (layoutElement != null) {
				String orientationBuffer = layoutElement.getAttribute(XMLConstants.ATTR_ORIENTATION);
				if (orientationBuffer.equalsIgnoreCase("Portrait")) {
					orientation = OrientationRequested.PORTRAIT;
				}
				else if (orientationBuffer.equalsIgnoreCase("Landscape")) {
					orientation = OrientationRequested.LANDSCAPE;

				}
			}
			// ヘッダマージン.
			Element headerElement = (Element) pageSetupElement.getElementsByTagName(XMLConstants.ELM_HEADER).item(0);
			if (headerElement != null) {
				headerMargin = (this.parseDouble(headerElement.getAttribute(XMLConstants.ATTR_HFMARGIN), DEFAULT_HEADER_MARGIN));
			}
			// フッタマージン.
			Element footerElement = (Element) pageSetupElement.getElementsByTagName(XMLConstants.ELM_FOOTER).item(0);
			if (footerElement != null) {
				footerMargin = (this.parseDouble(footerElement.getAttribute(XMLConstants.ATTR_HFMARGIN), DEFAULT_FOOTER_MARGIN));
			}
			// 印刷範囲マージン.
			Element marginsElement = (Element) pageSetupElement.getElementsByTagName(XMLConstants.ELM_PAGEMARGINS).item(0);
			if (marginsElement != null) {
				// トップマージン.
				topMargin = (this.parseDouble(marginsElement.getAttribute(XMLConstants.ATTR_MARGINTOP), DEFAULT_TOP_MARGIN));
				// レフトマージン.
				leftMargin = (this.parseDouble(marginsElement.getAttribute(XMLConstants.ATTR_MARGINLEFT), DEFAULT_LEFT_MARGIN));
				// ライトマージン.
				rightMargin = (this.parseDouble(marginsElement.getAttribute(XMLConstants.ATTR_MARGINRIGHT), DEFAULT_RIGHT_MARGIN));
				// ボトムマージン.
				bottomMargin = (this.parseDouble(marginsElement.getAttribute(XMLConstants.ATTR_MARGINBOTTOM),
				        DEFAULT_BOTTOM_MARGIN));
			}
		}
		// 印刷設定 (用紙サイズ & スケール).
		Element printElement = (Element) optionsElement.getElementsByTagName(XMLConstants.ELM_PRINT).item(0);
		if (printElement != null) {
			// 用紙サイズ.
			Element paperSizeElement = (Element) printElement.getElementsByTagName(XMLConstants.ELM_PAPERSIZE).item(0);
			XMLPaperSize paperSizeBuffer = XMLPaperSize.parse(paperSizeElement.getTextContent());
			if (paperSizeBuffer != null) {
				paperSize = paperSizeBuffer;
			}

			// スケール.
			Element scaleElementBuffer = (Element) printElement.getElementsByTagName(XMLConstants.ELM_SCALE).item(0);
			if (scaleElementBuffer != null) {
				this.scale = (this.parseDouble(scaleElementBuffer.getTextContent(), getScale()));
			}
		}
	}

	// --------------------------------------------------

	/** テーブル情報 */
	private XMLTable table;

	/**
	 * テーブル情報を取得します<br>
	 * 
	 * @return
	 */
	public XMLTable getTable() {
		return this.table;
	}

	/**
	 * テーブル情報を設定します<br>
	 * 
	 * @param table
	 */
	public void setTable(final XMLTable table) {
		this.table = table;
	}

	// --------------------------------------------------

	/**
	 * 文字列 s を実数に変換した値を返します<br>
	 * 文字列 s が実数に変換できない場合は、既定値 defaultValue を返します<br>
	 * 
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	private double parseDouble(final String s, final double defaultValue) {
		//
		double result = defaultValue;
		if (this.isDouble(s)) {
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
	private boolean isDouble(final String s) {
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
					result = (i != str.length() - 1) && (digits != 0) && (exponents == 0);
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

	// --------------------------------------------------
	/**
	 * 用紙サイズを取得します<br>
	 * 
	 * @return
	 */
	public XMLPaperSize getPaperSize() {
		return this.paperSize;
	}

	// --------------------------------------------------
	/**
	 * 用紙方向を取得します<br>
	 * 
	 * @return
	 */
	public OrientationRequested getOrientation() {
		return this.orientation;
	}

	// --------------------------------------------------
	/**
	 * ヘッダマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getHeaderMargin() {
		return this.headerMargin;
	}

	// --------------------------------------------------
	/**
	 * トップマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getTopMargin() {
		return this.topMargin;
	}

	// --------------------------------------------------
	/**
	 * レフトマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getLeftMargin() {
		return this.leftMargin;
	}

	// --------------------------------------------------
	/**
	 * ライトマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getRightMargin() {
		return this.rightMargin;
	}

	// --------------------------------------------------
	/**
	 * ボトムマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getBottomMargin() {
		return this.bottomMargin;
	}

	// --------------------------------------------------
	/**
	 * フッタマージン(インチ数)を取得します<br>
	 * 
	 * @return
	 */
	public double getFooterMargin() {
		return this.footerMargin;
	}

	// --------------------------------------------------
	/**
	 * スケールを取得します<br>
	 * 
	 * @return
	 */
	public double getScale() {
		return this.scale;
	}
}