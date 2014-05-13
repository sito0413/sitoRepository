package framework2.base.report;

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

public class ReportBorder {
	private final ReportCell reportCell;
	private final ReportLine top;
	private final ReportLine topLeft;
	private final ReportLine left;
	private final ReportLine bottomLeft;
	private final ReportLine bottom;
	private final ReportLine topRight;
	private final ReportLine right;
	private final ReportLine bottomRight;
	private Insets insets;
	private boolean isVisible;

	ReportBorder(final ReportCell reportCell) {
		this.insets = new Insets(0, 0, 0, 0);
		this.isVisible = true;
		this.reportCell = reportCell;
		this.top = new ReportLine(this);
		this.topLeft = new ReportLine(this);
		this.left = new ReportLine(this);
		this.bottomLeft = new ReportLine(this);
		this.bottom = new ReportLine(this);
		this.topRight = new ReportLine(this);
		this.right = new ReportLine(this);
		this.bottomRight = new ReportLine(this);
		validate(reportCell.getBounds());
	}

	public ReportLine getTop() {
		return top;
	}

	public ReportLine getLeft() {
		return left;
	}

	public ReportLine getBottom() {
		return bottom;
	}

	public ReportLine getRight() {
		return right;
	}

	public ReportLine getTopLeft() {
		return topLeft;
	}

	public ReportLine getTopRight() {
		return topRight;
	}

	public ReportLine getBottomLeft() {
		return bottomLeft;
	}

	public ReportLine getBottomRight() {
		return bottomRight;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
		revalidate();
	}

	// -----------------------------------------------
	Insets getInsets() {
		if (isVisible) {
			return insets;
		}
		return new Insets(0, 0, 0, 0);
	}

	void paint(final Graphics2D g) {
		if (isVisible) {
			ReportLine[] lines = new ReportLine[] { right, left, bottom, top,
					topLeft, bottomLeft, topRight, bottomRight };
			for (ReportLine line : lines) {
				line.paint(g);
			}
		}
	}

	void validate(final Rectangle bounds) {
		right.validate(bounds.width + bounds.x, bounds.y, bounds.width
				+ bounds.x, bounds.height + bounds.y, -1, -1);
		left.validate(bounds.x, bounds.y, bounds.x, bounds.height + bounds.y,
				-1, -1);
		bottom.validate(bounds.x, bounds.height + bounds.y, bounds.width
				+ bounds.x, bounds.height + bounds.y, -1, -1);
		top.validate(bounds.x, bounds.y, bounds.width + bounds.x, bounds.y, -1,
				-1);
		topLeft.validate(bounds.x, bounds.y, bounds.width * 2,
				bounds.height * 2, 180, -90);
		bottomLeft.validate(bounds.x, bounds.y - bounds.height,
				bounds.width * 2, bounds.height * 2, 180, 90);
		topRight.validate(bounds.x - bounds.width, bounds.y, bounds.width * 2,
				bounds.height * 2, 0, 90);
		bottomRight.validate(bounds.x - bounds.width, bounds.y - bounds.height,
				bounds.width * 2, bounds.height * 2, 0, -90);
	}

	void revalidate() {
		insets = new Insets(top.getWidth(), left.getWidth(), bottom.getWidth(),
				right.getWidth());
		reportCell.revalidate();
	}
}
