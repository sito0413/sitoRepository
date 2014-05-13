package framework2.base.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

public class ReportPage implements Printable {

	private final ReportTable reportTable;
	private final ReportBook reportBook;
	private PageFormat pageFormat;

	// -------------------------------------------------------------
	ReportPage(final ReportBook reportBook, final Dimension size) {
		this.reportBook = reportBook;
		this.reportTable = new ReportTable(this, size);
		this.pageFormat = PrinterJob.getPrinterJob().defaultPage();
	}

	public ReportTable getTable() {
		return reportTable;
	}

	// -----------------------------------------------------
	@Override
	public int print(final Graphics g, final PageFormat format, final int i) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setClip(0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight());
		g2d.translate(pageFormat.getPaper().getImageableX(), pageFormat.getPaper().getImageableY());
		paint(g);
		return Printable.PAGE_EXISTS;
	}

	// --------------------------------------------------

	public PageFormat getPageFormat() {
		return pageFormat;
	}

	public void setPageFormat() {
		pageFormat = PrinterJob.getPrinterJob().pageDialog(pageFormat);
		reportBook.revalidate();
	}

	public Printable getPrintable() {
		return this;
	}

	// --------------------------------------------------

	void revalidate() {
		reportBook.revalidate();
	}

	void paint(final Graphics g) {

		g.setColor(Color.WHITE);
		Paper paper = pageFormat.getPaper();
		if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
			g.fillRect(0, 0, (int) paper.getHeight(), (int) paper.getWidth());
			Graphics2D graphics2D = (Graphics2D) g.create((int) paper.getImageableY(), (int) paper.getImageableX(), (int) paper
			        .getImageableHeight(), (int) paper.getImageableWidth());
			reportTable.paint(graphics2D);
			graphics2D.dispose();
		}
		else if (pageFormat.getOrientation() == PageFormat.REVERSE_LANDSCAPE) {
			g.fillRect(0, 0, (int) paper.getHeight(), (int) paper.getWidth());
			Graphics2D graphics2D = (Graphics2D) g.create((int) paper.getImageableY(), (int) paper.getImageableX(), (int) paper
			        .getImageableHeight(), (int) paper.getImageableWidth());
			reportTable.paint(graphics2D);
			graphics2D.dispose();
		}
		else if (pageFormat.getOrientation() == PageFormat.PORTRAIT) {
			g.fillRect(0, 0, (int) paper.getWidth(), (int) paper.getHeight());
			Graphics2D graphics2D = (Graphics2D) g.create((int) paper.getImageableX(), (int) paper.getImageableY(), (int) paper
			        .getImageableWidth(), (int) paper.getImageableHeight());
			reportTable.paint(graphics2D);
			graphics2D.dispose();

		}
	}

}
