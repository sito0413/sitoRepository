package framework2.base.report.writer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JPanel;

/**
 * 印刷対象の1ページ分のオブジェクト<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
public class PageObject extends JPanel implements Printable {

	/** ページの幅 */
	private final int width;
	/** ページの高さ */
	private final int height;

	private double scale = 1;

	/**
	 * コンストラクタ<br>
	 * 
	 * @param pageWidth
	 *            ページの幅<br>
	 * @param pageHeight
	 *            ページの高さ<br>
	 */
	public PageObject(final int pageWidth, final int pageHeight) {
		this.width = pageWidth;
		this.height = pageHeight;
	}

	/*
	 * 指定されたインデックスにあるページを、指定された書式で、<br> 指定された Graphics コンテキストに印刷します。<br>
	 */
	@Override
	public int print(final Graphics g, final PageFormat format, final int i) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setClip(0, 0, width, height);
		g2d.dispose();
		g2d.translate(format.getPaper().getImageableX(), format.getPaper().getImageableY());
		this.print(g2d);
		return Printable.PAGE_EXISTS;
	}

	/**
	 * ページの幅を取得します<br>
	 * 
	 * @return
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * ページの高さを取得します<br>
	 * 
	 * @return
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	public synchronized double getScale() {
		return scale;
	}

	public synchronized void setScale(final double scale) {
		this.scale = scale;
	}

}
