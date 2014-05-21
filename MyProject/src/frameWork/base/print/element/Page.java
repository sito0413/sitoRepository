package frameWork.base.print.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Page implements Printable {
	
	private final Map<String, Cell> variableCells;
	private final Map<String, Cell> cells;
	private final int leftMargin;
	private final int topMargin;
	private final int rightMargin;
	private final int bottomMargin;
	private final int width;
	private final int height;
	private double scale = 1;
	private File file;
	
	public Page(final int leftMargin, final int topMargin, final int rightMargin, final int bottomMargin,
	        final int width, final int height) {
		this.leftMargin = leftMargin;
		this.topMargin = topMargin;
		this.rightMargin = rightMargin;
		this.bottomMargin = bottomMargin;
		this.width = width;
		this.height = height;
		this.cells = new HashMap<>();
		this.variableCells = new HashMap<>();
	}
	
	/**
	 * 指定された行、列のセルを取得します<br>
	 */
	public Cell getCell(final int rowIndex, final int columnIndex) {
		return cells.get(Cell.getKey(rowIndex, columnIndex));
	}
	
	/**
	 * セルオブジェクトを取得します<br>
	 * offset に、キーセルオブジェクトからの下方向へのオフセットを渡すことで、<br>
	 * キーセルの下のオブジェクトを取得できます<br>
	 */
	public Cell getCell(final String key, final int offset) {
		final Cell originCell = this.variableCells.get(key);
		if (originCell == null) {
			return null;
		}
		return this.getCell((originCell.getRowIndex()) + offset, originCell.getColumnIndex());
	}
	
	@Override
	public int print(final Graphics g, final PageFormat format, final int i) {
		final Graphics2D g2d = (Graphics2D) g;
		g2d.translate(format.getPaper().getImageableX(), format.getPaper().getImageableY());
		g2d.setClip(0, 0, width, height);
		paint(g2d);
		return Printable.PAGE_EXISTS;
	}
	
	public synchronized void paint(final Graphics2D g) {
		g.scale(scale, scale);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.translate(leftMargin, topMargin);
		for (final Cell cell : cells.values()) {
			cell.paint(g);
		}
		g.setColor(Color.white);
		g.translate(-leftMargin, -topMargin);
		g.fillRect(0, 0, leftMargin, height);
		g.fillRect(0, 0, width, topMargin);
		g.fillRect(width - rightMargin, 0, rightMargin, height);
		g.fillRect(0, height - bottomMargin, width, bottomMargin);
		
	}
	
	public int getHeight() {
		return (int) (height * scale);
	}
	
	public int getWidth() {
		return (int) (width * scale);
	}
	
	public synchronized void setScale(final double scale) {
		this.scale = scale;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(final File file) {
		this.file = file;
	}
	
	public void addCell(final Cell cell) {
		cells.put(Cell.getKey(cell.getRowIndex(), cell.getColumnIndex()), cell);
		if (cell.getKey() != null) {
			variableCells.put(cell.getKey(), cell);
		}
	}
}
