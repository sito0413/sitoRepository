package framework2.base.report.preview;

import java.net.URI;

import framework2.base.report.util.ReportException;
import framework2.base.report.writer.PageObject;
import framework2.base.report.writer.PrintReportWriter;

/**
 * 帳票コントロール
 */
public class ReportPreview {

	/** 印刷ページなし時のインデックス */
	private static final int NO_PAGES_INDEX = -1;
	private final PrintReportWriter writer;
	private int pageIndex;
	private URI uri;
	private double scale;

	/**
	 * コンストラクタ
	 */
	public ReportPreview(final PrintReportWriter reportWriter) {
		this.scale = 1;
		this.writer = reportWriter;// ビュー生成.
	}

	// --------------------------------------------------
	PageObject getActivePage() {
		if (this.pageIndex == NO_PAGES_INDEX) {
			// 印刷ページなし.
			return null;
		}
		return this.writer.getPage(this.pageIndex);
	}

	int getPageIndex() {
		return pageIndex;
	}

	private void setPageIndex(final int index) {
		int validatedIndex = NO_PAGES_INDEX;
		if (index > NO_PAGES_INDEX) {
			validatedIndex = (index > this.getMaxPageIndex() ? this.getMaxPageIndex() : index);
		}
		this.pageIndex = validatedIndex;
	}

	int getMaxPageIndex() {
		int maxIndex = NO_PAGES_INDEX;
		maxIndex = this.writer.getPageSize() - 1;
		return maxIndex;
	}

	// --------------------------------------------------

	/**
	 * プレビューを表示します<br>
	 */
	public void show(final URI fileURI) {
		uri = fileURI;
		Preview view = new Preview(this);
		view.setVisible(true);
	}

	// --------------------------------------------------

	/**
	 * 前ページに移動<br>
	 */
	void previousPage() {
		// ページインデックス更新.
		this.setPageIndex(this.pageIndex - 1);
	}

	// --------------------------------------------------

	/**
	 * 次ページに移動<br>
	 */
	void nextPage() {
		// ページインデックス更新.
		this.setPageIndex(this.pageIndex + 1);
	}

	// --------------------------------------------------

	/**
	 * 印刷処理<br>
	 * 
	 * @throws PrinterException
	 */
	void printAction() throws ReportException {
		this.writer.writeProcess(uri);
	}

	// --------------------------------------------------

	synchronized double getScale() {
		return scale;
	}

	synchronized void setScale(final double scale) {
		this.scale = scale;
		for (int i = 0; i < this.writer.getPageSize(); i++) {
			this.writer.getPage(i).setScale(scale);
		}
	}

	// --------------------------------------------------

}