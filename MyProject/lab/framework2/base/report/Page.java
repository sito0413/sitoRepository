package framework2.base.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;

import framework2.base.report.element.CellPanel;
import framework2.base.report.util.ReportException;
import framework2.base.report.writer.PageObject;


public abstract class Page extends PageObject {

	/** 中央パネル */
	private JPanel centerNWPanel;

	/** 変数セル一覧 */
	private Map<String, CellPanel> variableCells;

	/** セル一覧 */
	private Vector<Vector<CellPanel>> cells;

	// -------------------------------------------------------------
	public Page(final int pageWidth, final int pageHeight) {
		super(pageWidth, pageHeight);
		this.initialize(pageWidth, pageHeight);
	}

	/**
	 * 初期化<br>
	 * 
	 * @param width
	 * @param height
	 */
	private void initialize(final int width, final int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.add(getCenterNWPanel(), BorderLayout.CENTER);
	}

	// -------------------------------------------------------------

	/**
	 * 中央パネルを取得します<br>
	 * 
	 * @return centerNWPanel
	 */
	public JPanel getCenterNWPanel() {
		if (this.centerNWPanel == null) {
			this.centerNWPanel = new JPanel();
			this.centerNWPanel.setLayout(null);
			this.centerNWPanel.setBackground(Color.WHITE);
		}
		return this.centerNWPanel;
	}

	// --------------------------------------------------
	/**
	 * セル一覧を取得します<br>
	 * 
	 * @return
	 */
	private Vector<Vector<CellPanel>> getCells() {
		if (this.cells == null) {
			this.cells = new Vector<Vector<CellPanel>>();
		}
		return this.cells;
	}

	/**
	 * 指定された行、列のセルを取得します<br>
	 * 
	 * @param row
	 *            行を指定します. 最初の行は 0 を指定します<br>
	 * @param column
	 *            列を指定します. 最初の列は 0 を指定します<br>
	 * @return
	 */
	public CellPanel getCell(final int row, final int column) {
		// 行インデックスが有効範囲にない場合は null を返す.
		if ((row < 0) || (row >= this.getCells().size())) {
			return null;
		}
		// 行データを取得.
		Vector<CellPanel> rowData = this.getCells().get(row);
		// 列インデックスが有効範囲にない場合は null を返す.
		if ((column < 0) || (column > rowData.size())) {
			return null;
		}
		// 指定箇所のセルを返す.
		return rowData.get(column);
	}

	/**
	 * セルオブジェクトを取得します<br>
	 * 
	 * @param key
	 *            キーとなる文字列<br>
	 * @return
	 * @throws ReportException
	 */
	public CellPanel getCell(final String key) throws ReportException {
		return this.getCell(key, 0);
	}

	/**
	 * セルオブジェクトを取得します<br>
	 * offset に、キーセルオブジェクトからの下方向へのオフセットを渡すことで、<br>
	 * キーセルの下のオブジェクトを取得できます<br>
	 * 
	 * @param key
	 *            キーとなる文字列<br>
	 * @param offset
	 *            キーセルオブジェクトからのオフセット<br>
	 * @return
	 * @throws ReportException
	 */
	public CellPanel getCell(final String key, final int offset) throws ReportException {
		CellPanel cellPanel = getVariableCell(key, offset);
		if (cellPanel == null) {
			throw new ReportException(key + ":指定されたセルが見つかりませんでした", new Exception()); //$NON-NLS-1$
		}
		return cellPanel;
	}

	/**
	 * セル一覧にセルを設定します<br>
	 * 
	 * @param cell
	 */
	public void setCell(final CellPanel cell) {
		// セルが指定されていない場合は処理中止.
		if (cell == null) {
			return;
		}
		// 行追加.
		// ※ CellPanel の row, column は 1 から始まり、.
		// ReportObject#cells の row, column は 0 から始まる.
		int row = cell.getRowIndex() - 1;
		for (; this.getCells().size() <= row;) {
			this.getCells().add(new Vector<CellPanel>());
		}
		// 列追加.
		int column = cell.getColumnIndex() - 1;
		for (; this.getCells().get(row).size() <= column;) {
			this.getCells().get(row).add(null);
		}
		// セル設定.
		this.getCells().get(row).set(column, cell);
	}

	// -------------------------------------------------------------
	/**
	 * 変数セル一覧を取得します<br>
	 * 
	 * @return
	 */
	private Map<String, CellPanel> getVariableCells() {
		if (this.variableCells == null) {
			this.variableCells = new ConcurrentHashMap<String, CellPanel>();
		}
		return this.variableCells;
	}

	/**
	 * 指定されたキーの変数セルを取得します<br>
	 * 
	 * @param key
	 * @return
	 */
	public CellPanel getVariableCell(final String key) {
		return this.getVariableCell(key, 0);
	}

	/**
	 * 指定されたキーの変数セルを設定します<br>
	 * 
	 * @param key
	 * @param cell
	 */
	public void setVariableCell(final String key, final CellPanel cell) {
		if ((key == null) || key.isEmpty() || (cell == null)) {
			return;
		}
		this.getVariableCells().put(key, cell);
	}

	/**
	 * 指定されたキー、オフセットの変数セルを取得します<br>
	 * 
	 * @param key
	 * @param offset
	 * @return
	 */
	public CellPanel getVariableCell(final String key, final int offset) {
		// 取得条件が不正な場合は null を返す.
		if ((key == null) || key.isEmpty() || (offset < 0)) {
			return null;
		}
		// 基準セルの取得.
		CellPanel originCell = null;
		if (this.getVariableCells().containsKey(key)) {
			originCell = this.getVariableCells().get(key);
		}
		// 基準セルが存在しない場合は null を返す.
		if (originCell == null) {
			return null;
		}
		// offset が 0 の場合は、基準セルを返す.
		if (offset == 0) {
			return originCell;
		}
		// 対象セルの取得.
		// ※ CellPanel の row, column は 1 から始まり、.
		// ReportObject#cells の row, column は 0 から始まる.
		CellPanel cell = this.getCell(originCell.getRowIndex() - 1 + offset, originCell.getColumnIndex() - 1);
		// 対象セルを返す.
		return cell;
	}

}
