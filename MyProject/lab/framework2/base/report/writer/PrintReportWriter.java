package framework2.base.report.writer;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import framework2.base.report.Page;
import framework2.base.report.preview.ReportPreview;
import framework2.base.report.util.ExceptionType;
import framework2.base.report.util.ReportException;

public class PrintReportWriter {
	private final List<PageObject> pages;

	public PrintReportWriter() {
		pages = new ArrayList<PageObject>();
	}

	public void write(final URI fileURI) throws ReportException {
		List<List<PageObject>> pagesList = new Vector<List<PageObject>>();
		pagesList.add(this.getPages());

		// ダミー表示用ダイアログ生成.
		JDialog dummyDialog = new JDialog();
		JPanel dummyPanel = new JPanel();
		// フレームは非表示.
		dummyDialog.setUndecorated(true);
		// 画面の外で表示する.
		dummyDialog.setBounds(-1, -1, 0, 0);
		// ダイアログクローズ時にウィンドウ破棄.
		dummyDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dummyDialog.setContentPane(dummyPanel);
		// 印刷.
		// クライアント実行時.
		try {
			this.clientPrintout(pagesList, dummyDialog, dummyPanel);
		}
		catch (PrinterException exp) {
			new ReportException(ExceptionType.NULL, exp);
		}
		finally {
			// ダイアログ破棄.
			dummyDialog.dispose();
		}
	}

	/**
	 * クライアント実行時の印刷処理<br>
	 * 
	 * @param pagesList
	 * @param dummyDialog
	 * @param dummyPanel
	 * @throws PrinterException
	 */
	private void clientPrintout(final List<List<PageObject>> pagesList, final JDialog dummyDialog, final JPanel dummyPanel)
	        throws PrinterException {
		// 印刷ページ情報生成.
		Book book = new Book();
		for (@SuppressWarnings("hiding")
		List<? extends PageObject> pages : pagesList) {
			for (PageObject page : pages) {
				dummyPanel.add(page);
				book.append(page, this.getPageFormat(page));
			}
		}
		// 印刷.
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPageable(book);
		if (printerJob.printDialog()) {
			dummyDialog.setVisible(true);
			printerJob.print();
			dummyDialog.setVisible(false);
		}
	}

	// --------------------------------------------------

	/**
	 * 印刷設定を取得します<br>
	 * 
	 * @param page
	 * @return
	 */
	private PageFormat getPageFormat(final PageObject page) {
		// 用紙設定取得.
		int width = page.getWidth();
		int height = page.getHeight();
		int orientation = (width <= height ? PageFormat.PORTRAIT : PageFormat.LANDSCAPE);
		// 用紙情報生成 (必ず短辺を横方向として指定する).
		Paper newPaper = new Paper();
		if (orientation == PageFormat.PORTRAIT) {
			newPaper.setSize(width, height);
			newPaper.setImageableArea(0, 0, width, height);
		}
		else {
			newPaper.setSize(height, width);
			newPaper.setImageableArea(0, 0, height, width);
		}
		// 印刷情報生成.
		PageFormat newFormat = new PageFormat();
		// 用紙方向.
		newFormat.setOrientation(orientation);
		// 用紙.
		newFormat.setPaper(newPaper);
		//
		return newFormat;
	}

	// --------------------------------------------------
	public final void writeProcess(final URI fileURI) throws ReportException {
		// 印刷ページ有無確認.
		if (this.getPages().isEmpty()) {
			throw new ReportException(ExceptionType.NOT_HAS_PAGES);
		}
		// 印刷.
		for (PageObject object : this.getPages()) {
			object.setScale(1);
		}

		write(fileURI);
	}

	public final void preview(final URI fileURI) throws ReportException {
		// 印刷ページ有無確認.
		if (this.getPages().isEmpty()) {
			throw new ReportException(ExceptionType.NOT_HAS_PAGES);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// プレビュー表示.
				new ReportPreview(PrintReportWriter.this).show(fileURI);
			}
		});
	}

	// --------------------------------------------------
	protected final List<PageObject> getPages() {
		return this.pages;
	}

	public final PageObject getPage(final int index) {
		return this.getPages().get(index);
	}

	public final void addPages(final Page reportPage) {
		this.getPages().add(reportPage);
	}

	public final int getPageSize() {
		return this.getPages().size();
	}

}