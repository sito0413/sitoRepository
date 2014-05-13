package framework2.base.report;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import framework2.base.report.element.CellPanel;

public class ReportP extends Page implements Cloneable {
	public ReportP() {
		super(0, 0);
	}

	// --------------------------------------------------

	/**
	 * コンストラクタ<br>
	 * 
	 * @param width
	 * @param height
	 */
	public ReportP(final int width, final int height) {
		super(width, height);
	}

	// --------------------------------------------------

	/*
	 * レポートオブジェクトのクローンを生成します<br>
	 */
	@Override
	public ReportP clone() throws CloneNotSupportedException {
		// 新規 ReportObject を生成.
		ReportP newReport = new ReportP(this.getWidth(), this.getHeight());

		// ReportObject の余白を複製.
		newReport.setTopMargin(this.getTopMargin());
		newReport.setLeftMargin(this.getLeftMargin());
		newReport.setRightMargin(this.getRightMargin());
		newReport.setBottomMargin(this.getBottomMargin());
		newReport.setScale(this.getScale());
		for (int i = 0; i < list.size(); i++) {
			final int index = i;
			CellPanel newCell = new CellPanel();
			CellPanel cellPanel = list.get(index);
			newCell.copy(cellPanel);
			newReport.add(newCell);
		}
		return newReport;
	}

	// --------------------------------------------------

	/** トップマージン */
	private int topMargin;

	/**
	 * トップマージンを取得します<br>
	 * 
	 * @return
	 */
	public int getTopMargin() {
		return this.topMargin;
	}

	/**
	 * トップマージンを設定します<br>
	 * 
	 * @param margin
	 */
	public void setTopMargin(final int margin) {
		this.topMargin = margin;
	}

	// --------------------------------------------------

	/** レフトマージン */
	private int leftMargin;

	/**
	 * レフトマージンを取得します<br>
	 * 
	 * @return
	 */
	public int getLeftMargin() {
		return this.leftMargin;
	}

	/**
	 * レフトマージンを設定します<br>
	 * 
	 * @param margin
	 */
	public void setLeftMargin(final int margin) {
		this.leftMargin = margin;
	}

	// --------------------------------------------------

	/** ライトマージン */
	private int rightMargin;

	/**
	 * ライトマージンを取得します<br>
	 * 
	 * @return
	 */
	public int getRightMargin() {
		return this.rightMargin;
	}

	/**
	 * ライトマージンを設定します<br>
	 * 
	 * @param margin
	 */
	public void setRightMargin(final int margin) {
		this.rightMargin = margin;
	}

	// --------------------------------------------------

	/** ボトムマージン */
	private int bottomMargin;

	/**
	 * ボトムマージンを取得します<br>
	 * 
	 * @return
	 */
	public int getBottomMargin() {
		return this.bottomMargin;
	}

	/**
	 * ボトムマージンを設定します<br>
	 * 
	 * @param margin
	 */
	public void setBottomMargin(final int margin) {
		this.bottomMargin = margin;
	}

	// --------------------------------------------------
	@Override
	public void paint(final Graphics g) {
		ajastLayout();
		if (((Graphics2D) g).getTransform().getScaleX() != getScale()) {
			((Graphics2D) g).scale(getScale(), getScale());
		}
		super.paint(g);
	}

	public void ajastLayout() {
		getCenterNWPanel().setBounds(getLeftMargin(), getTopMargin(), getWidth() - (getLeftMargin() + getRightMargin()),
		        getHeight() - (getTopMargin() + getBottomMargin()));
	}

	List<CellPanel> list = new CopyOnWriteArrayList<CellPanel>();

	@SuppressWarnings("deprecation")
	public void add(final CellPanel newCellPanel) {
		if (newCellPanel.getObjTextLabel() != null) {
			getCenterNWPanel().add(newCellPanel.getObjTextLabel());
		}
		if (newCellPanel.getBorder() != null) {
			getCenterNWPanel().add(newCellPanel.getBorder());
		}
		list.add(newCellPanel);
		// セル一覧格納.
		setCell(newCellPanel);
		// 変数領域セル起点格納.
		String key = newCellPanel.getKey();
		if ((key != null) && (!key.isEmpty())) {
			setVariableCell(key, newCellPanel);
		}
	}

}