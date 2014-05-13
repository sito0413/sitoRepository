package framework2.base.report;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JLabel;

public class ReportTable {
	/** セル一覧 */
	private final List<ReportCell> cellList;

	private final Map<Integer, Map<Integer, ReportCell>> rcCellMap;
	private final Map<Integer, Map<Integer, ReportCell>> crCellMap;

	private final ReportPage reportPage;

	private Dimension size;

	public ReportTable(final ReportPage reportPage, final Dimension size) {
		this.reportPage = reportPage;
		this.size = size;
		this.cellList = new ArrayList<>();
		this.rcCellMap = new ConcurrentHashMap<>();
		this.crCellMap = new ConcurrentHashMap<>();
	}

	// -----------------------------------------------------

	public Dimension getSize() {
		return size;
	}

	public void setSize(final Dimension size) {
		this.size = size;
		reportPage.revalidate();
	}

	public ReportCell getCellAtIndex(final int row, final int column) {
		if (!rcCellMap.containsKey(row)) {
			ReportCell cell = new ReportCell(this, column, row);
			add(cell);
			return cell;
		}
		if (!crCellMap.containsKey(column)) {
			ReportCell cell = new ReportCell(this, column, row);
			add(cell);
			return cell;
		}
		if (crCellMap.get(column).get(row) == null) {
			ReportCell cell = new ReportCell(this, column, row);
			add(cell);
			return cell;
		}
		return crCellMap.get(column).get(row);
	}

	// --------------------------------------------------
	private void add(final ReportCell cell) {
		// 行追加.
		int row = cell.getRowIndex() - 1;
		if (!rcCellMap.containsKey(row)) {
			rcCellMap.put(row, new ConcurrentHashMap<Integer, ReportCell>());
		}
		// 列追加.
		int column = cell.getColumnIndex() - 1;
		if (!crCellMap.containsKey(column)) {
			crCellMap.put(column, new ConcurrentHashMap<Integer, ReportCell>());
		}
		crCellMap.get(column).put(row, cell);
		rcCellMap.get(row).put(column, cell);

		// セル設定.
		cellList.add(cell);

	}

	// --------------------------------------------------

	void paint(final Graphics2D graphics2D) {
		JLabel label = new JLabel();
		for (ReportCell cell : cellList) {
			cell.paint1(graphics2D, label);
		}
		for (ReportCell cell : cellList) {
			cell.paint2(graphics2D);
		}
	}

	// --------------------------------------------------
	void revalidate() {
		reportPage.revalidate();
	}
}
