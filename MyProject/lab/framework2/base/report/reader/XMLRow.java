package framework2.base.report.reader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import framework2.base.report.util.XMLConstants;

/**
 * 行情報<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/17
 * @最終更新者 若井<br>
 * @最終更新日 2009/11/12
 */
@SuppressWarnings("nls")
public class XMLRow {

	/** 行インデックス */
	private int index;

	/** 行高さ */
	private double height;

	/** 繰り返し数 */
	private int span;

	/** スタイルID */
	private String styleId;

	/** セル情報 */
	List<XMLCell> cells;

	public XMLRow(final int index, final int span, final double height) {
		initialize(index, span, height);
	}

	private void initialize(final int index2, final int span2, final double height2) {
		this.index = index2;
		// スパン.
		this.span = span2;
		// 行高.
		this.height = height2;
		// スタイルID.
		this.styleId = "";
		// セル.
		this.cells = new CopyOnWriteArrayList<XMLCell>();

	}

	/**
	 * 行情報を変換します<br>
	 * 
	 * @param rowNode
	 * @param index
	 * @param defaultHeight
	 * @return
	 */
	public XMLRow(final Node rowNode, final int index, final double defaultHeight) {
		// 行情報が存在しない場合は、既定値の行情報を返す.
		if (rowNode == null) {
			initialize(index, span, height);
			return;
		}
		// 行情報変換.
		Element rowElement = (Element) rowNode;
		// インデックス.
		this.index = (this.parseInt(rowElement.getAttribute(XMLConstants.ATTR_INDEX), index));
		// スパン.
		this.span = (this.parseInt(rowElement.getAttribute(XMLConstants.ATTR_SPAN), 0));
		// 行高.
		this.height = (this.parseDouble(rowElement.getAttribute(XMLConstants.ATTR_HEIGHT), defaultHeight));
		// スタイルID
		this.styleId = (rowElement.getAttribute(XMLConstants.ATTR_STYLEID));
		// セル.
		this.cells = new CopyOnWriteArrayList<XMLCell>();
		this.parseAndSetCells(rowElement.getElementsByTagName(XMLConstants.ELM_CELL));
	}

	// --------------------------------------------------
	/**
	 * 行インデックスを取得します<br>
	 * 
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}

	// --------------------------------------------------
	/**
	 * 行高さ(ポイント数)を取得します<br>
	 * 
	 * @return
	 */
	public double getHeight() {
		return this.height;
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
	 * スタイルIDを取得します<br>
	 * 
	 * @return
	 */
	public String getStyleId() {
		return this.styleId;
	}

	// --------------------------------------------------
	/**
	 * セル情報を取得します<br>
	 * 
	 * @return
	 */
	private List<XMLCell> getCells() {
		return this.cells;
	}

	public XMLCell getCell(final int colIndex) {
		while (colIndex >= getCells().size()) {
			getCells().add(null);
		}
		return getCells().get(colIndex);
	}

	/**
	 * セル情報を変換し設定します<br>
	 * 
	 * @param row
	 * @param cellsNodeList
	 */
	private void parseAndSetCells(final NodeList cellsNodeList) {
		//
		if (cellsNodeList == null) {
			return;
		}
		int idx = 0;
		for (int i = 0; i < cellsNodeList.getLength(); i++) {
			Element cellElement = (Element) cellsNodeList.item(i);
			XMLCell newCell = new XMLCell(idx, cellElement);
			// 値.
			NodeList dataNodeList = cellElement.getElementsByTagName(XMLConstants.ELM_DATA);
			if (dataNodeList.getLength() > 0) {
				newCell.setValue(dataNodeList.item(0).getTextContent());
			}
			while (newCell.getIndex() >= getCells().size()) {
				getCells().add(null);
			}
			// セル追加.
			getCells().set(newCell.getIndex(), newCell);
			// インデックス更新
			idx = newCell.getIndex() + newCell.getMergeAccross();
		}
	}

	/**
	 * 文字列 s を整数に変換した値を返します<br>
	 * 文字列 s が整数に変換できない場合は、既定値 defaultValue を返します<br>
	 * 
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	private int parseInt(final String s, final int defaultValue) {
		//
		int result = defaultValue;
		if (this.isInteger(s)) {
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
	private boolean isInteger(final String s) {
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

}
