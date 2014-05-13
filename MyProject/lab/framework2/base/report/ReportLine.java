package framework2.base.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ReportLine {
	private final ReportBorder reportBorder;
	private int width;
	private BasicStroke stroke;
	private Color color;
	private boolean isVisible;
	private int x;
	private int y;
	private int w;
	private int h;
	private int s;
	private int a;

	ReportLine(final ReportBorder reportBorder) {
		this.reportBorder = reportBorder;
		this.width = 1;
		this.color = Color.BLACK;
		this.isVisible = false;
		this.stroke = createStroke();
	}

	public int getWidth() {
		if (isVisible) {
			return this.width;
		}
		return 0;
	}

	public void setWidth(final int width) {
		this.width = width;
		this.stroke = createStroke();
		this.reportBorder.revalidate();

	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(final Color color) {
		this.color = color;
		this.reportBorder.revalidate();
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
		this.reportBorder.revalidate();
	}

	private BasicStroke createStroke() {
		return new BasicStroke(width, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 1.0f);
	}

	void paint(final Graphics2D g) {
		if (this.isVisible) {
			g.setColor(this.color);
			g.setStroke(this.stroke);
			if (s == -1 && a == -1) {
				g.drawLine(x, y, w, h);
			}
			else {
				g.drawArc(x, y, w, h, s, a);
			}
		}
	}

	@SuppressWarnings("hiding")
	void validate(final int x, final int y, final int w, final int h,
			final int s, final int a) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.s = s;
		this.a = a;
	}

}
