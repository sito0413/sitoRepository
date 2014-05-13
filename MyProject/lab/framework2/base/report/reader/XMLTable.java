package framework2.base.report.reader;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import framework2.base.report.util.XMLConstants;

/**
 * テーブル<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/17
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
public class XMLTable {

	/** 列幅の既定値 (ポイント数) */
	private static double DEFAULT_COLUMN_WIDTH = 48.00d;
	/** 行高さの既定値 (ポイント数) */
	private static double DEFAULT_ROW_HEIGHT = 12.75d;

	/** 既定の列幅 */
	private final double defaultColumnWidth;

	/** 列数 */
	private final int columnCount;

	/** 既定の行高さ */
	private final double defaultRowHeight;

	/** 行数 */
	private final int rowCount;

	// --------------------------------------------------

	public XMLTable(final Node tableNode) {
		//
		Element tableElement = (Element) tableNode;
		// 標準列幅.
		defaultColumnWidth = (this.parseDouble(tableElement.getAttribute(XMLConstants.ATTR_DEFCOLUMNWIDTH), DEFAULT_COLUMN_WIDTH));
		// 標準行高さ.
		defaultRowHeight = (this.parseDouble(tableElement.getAttribute(XMLConstants.ATTR_DEFROWHEIGHT), DEFAULT_ROW_HEIGHT));
		// 列数.
		columnCount = (this.parseInt(tableElement.getAttribute(XMLConstants.ATTR_EXCOLUMNCOUNT), 0));
		// 行数.
		rowCount = (this.parseInt(tableElement.getAttribute(XMLConstants.ATTR_EXROWCOUNT), 0));
		// 列.
		this.parseAndSetColumns(tableElement.getElementsByTagName(XMLConstants.ELM_COLUMN));
		// 行.
		this.parseAndSetRows(tableElement.getElementsByTagName(XMLConstants.ELM_ROW));
	}

	// --------------------------------------------------
	/**
	 * 列数を取得します<br>
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return this.columnCount;
	}

	// --------------------------------------------------
	/**
	 * 行数を取得します<br>
	 * 
	 * @return
	 */
	public int getRowCount() {
		return this.rowCount;
	}

	// --------------------------------------------------
	/**
	 * 既定の列幅(ポイント数)を取得します<br>
	 * 
	 * @return
	 */
	public double getDefaultColumnWidth() {
		return this.defaultColumnWidth;
	}

	// --------------------------------------------------
	/**
	 * 既定の行高さ(ポイント数)を取得します<br>
	 * 
	 * @return
	 */
	public double getDefaultRowHeight() {
		return this.defaultRowHeight;
	}

	// --------------------------------------------------

	/** 列情報 */
	private Set<XMLColumn> columns;

	/**
	 * 列情報を取得します<br>
	 * 
	 * @return
	 */
	public Set<XMLColumn> getColumns() {
		if (this.columns == null) {
			this.columns = new CopyOnWriteArraySet<XMLColumn>();
		}
		return this.columns;
	}

	// --------------------------------------------------

	/** 行情報 */
	private Set<XMLRow> rows;

	/**
	 * 行情報を取得します<br>
	 * 
	 * @return
	 */
	public Set<XMLRow> getRows() {
		if (this.rows == null) {
			this.rows = new CopyOnWriteArraySet<XMLRow>();
		}
		return this.rows;
	}

	// --------------------------------------------------

	/**
	 * 列情報を変換し設定します<br>
	 * 
	 * @param table
	 * @param columnsNodeList
	 */
	private void parseAndSetColumns(final NodeList columnsNodeList) {
		//
		if (columnsNodeList == null) {
			return;
		}
		int index = 0;
		for (int i = 0; i < columnsNodeList.getLength(); i++) {
			// 列情報変換.
			XMLColumn newColumn = new XMLColumn(columnsNodeList.item(i), index + 1, getDefaultColumnWidth());
			// 列情報補完.
			// - 列情報が明示的に存在しない場合、列情報を補完する.
			if (index < newColumn.getIndex() - 1) {
				int completeIndex = index + 1;
				getColumns().add(new XMLColumn(completeIndex, newColumn.getIndex() - completeIndex - 1, getDefaultColumnWidth()));
			}
			// インデックス更新.
			index = newColumn.getIndex() + newColumn.getSpan();
			// 列情報追加.
			getColumns().add(newColumn);
		}
		// 後方補完.
		// - 列数が table.getColumnCount() に届かない場合、列情報を補完する.
		if (index < getColumnCount()) {
			getColumns().add(new XMLColumn(index + 1, getColumnCount() - index - 1, getDefaultColumnWidth()));
		}
	}

	/**
	 * 行情報を変換し設定します<br>
	 * 
	 * @param table
	 * @param rowsNodeList
	 */
	private void parseAndSetRows(final NodeList rowsNodeList) {
		//
		if (rowsNodeList == null) {
			return;
		}
		int index = 0;
		for (int i = 0; i < rowsNodeList.getLength(); i++) {
			// 行情報変換.
			XMLRow newRow = new XMLRow(rowsNodeList.item(i), index + 1, getDefaultRowHeight());
			// インデックス更新.
			index = newRow.getIndex() + newRow.getSpan();
			// 前方補完.
			// - 最初の行情報が 2行目以降 の情報ならば、.
			// 1行目からの行情報を補完する.
			if ((i == 0) && (newRow.getIndex() > 1)) {
				getRows().add(new XMLRow(1, newRow.getIndex() - 2, getDefaultRowHeight()));
			}
			// 行情報追加.
			getRows().add(newRow);
		}
		// 後方補完.
		// - 行数が table.getRowCount() に届かない場合、.
		// 最終行までの行情報を補完する.
		if (index < getRowCount()) {
			getRows().add(new XMLRow(index + 1, getRowCount() - index - 1, getDefaultRowHeight()));
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
