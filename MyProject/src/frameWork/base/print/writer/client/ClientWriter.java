package frameWork.base.print.writer.client;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.SwingUtilities;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.Page;
import frameWork.base.print.writer.Writer;

public class ClientWriter extends Writer {
	@Override
	public final void print() throws ReportException {
		final Book book = new Book();
		for (final Page page : this) {
			page.setScale(1);
			
			// 用紙設定取得.
			final int width = page.getWidth();
			final int height = page.getHeight();
			// 用紙情報生成 (必ず短辺を横方向として指定する).
			final Paper paper = new Paper();
			paper.setSize(width, height);
			paper.setImageableArea(0, 0, width, height);
			final PageFormat format = new PageFormat();
			format.setOrientation(PageFormat.PORTRAIT);
			format.setPaper(paper);
			
			book.append(page, format);
		}
		// 印刷.
		final PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPageable(book);
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			}
			catch (final PrinterException e) {
				throw new ReportException(e);
			}
		}
	}
	
	@Override
	public final void preview() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Preview(ClientWriter.this).setVisible(true);
			}
		});
	}
}