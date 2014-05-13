package framework2.base.report;

import java.awt.Dimension;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ReportBook implements Pageable {
	private final String name;
	private final ArrayList<ReportPage> pages;
	PreviewView preview;

	public ReportBook(final String name) {
		this.name = name;
		this.pages = new ArrayList<>();
	}

	public ReportPage get(final int i) {
		return pages.get(i);
	}

	public ReportPage create(final Dimension size) {
		ReportPage page = new ReportPage(this, size);
		pages.add(page);
		return page;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("nls")
	public void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		try {
			if (pages.isEmpty()) {
				JOptionPane.showConfirmDialog(null, "帳票の出力に失敗しました");
			}
			printerJob.setPageable(this);
			printerJob.setJobName(name);
			if (printerJob.printDialog()) {
				printerJob.print();
			}
		}
		catch (PrinterException exp) {
			JOptionPane.showConfirmDialog(null, "帳票の出力に失敗しました");
		}

	}

	public void preview() {
		// 印刷ページ有無確認.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// プレビュー表示.
				preview = new PreviewView(ReportBook.this);
				preview.setVisible(true);
				preview = null;
			}
		});
	}

	@Override
	public int getNumberOfPages() {
		return pages.size();
	}

	@Override
	public PageFormat getPageFormat(final int pageIndex)
			throws IndexOutOfBoundsException {
		return pages.get(pageIndex).getPageFormat();
	}

	@Override
	public Printable getPrintable(final int pageIndex)
			throws IndexOutOfBoundsException {
		return pages.get(pageIndex).getPrintable();
	}

	public void revalidate() {
		if (preview != null) {
			preview.revalidate();
		}
	}
}
