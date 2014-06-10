package frameWork.base.print.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

@SuppressWarnings("unused")
public class CellBorder {
	private final List<BorderInfo> borderInfos;
	private final Point[] topDrawPoints;
	private final Point[] leftDrawPoints;
	private final Point[] rightDrawPoints;
	private final Point[] bottomDrawPoints;
	private final Point[] dLeftDrawPoints;
	private final Point[] dRightDrawPoints;
	
	CellBorder(final List<BorderInfo> borderInfos) {
		this.borderInfos = borderInfos;
		topDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
		leftDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
		rightDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
		bottomDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
		dLeftDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
		dRightDrawPoints = new Point[] {
		        new Point(0, 0), new Point(0, 0)
		};
	}
	
	public void add(final BorderInfo borderInfo) {
		this.borderInfos.add(borderInfo);
	}
	
	void paintBorder(final Graphics2D g, final int x, final int y, final int width, final int height) {
		g.setColor(Color.BLACK);
		createDrawPoints(x, y, width, height);
		for (final BorderInfo borderInfo : borderInfos) {
			final Point[] drawPoints;
			switch ( borderInfo.position ) {
				case TOP :
					borderInfo.drowLine(g, topDrawPoints[0], topDrawPoints[1]);
					break;
				case LEFT :
					borderInfo.drowLine(g, leftDrawPoints[0], leftDrawPoints[1]);
					break;
				case RIGHT :
					borderInfo.drowLine(g, rightDrawPoints[0], rightDrawPoints[1]);
					break;
				case BOTTOM :
					borderInfo.drowLine(g, bottomDrawPoints[0], bottomDrawPoints[1]);
					break;
				case DLEFT :
					borderInfo.drowLine(g, dLeftDrawPoints[0], dLeftDrawPoints[1]);
					break;
				case DRIGHT :
					borderInfo.drowLine(g, dRightDrawPoints[0], dRightDrawPoints[1]);
					break;
				default :
					break;
			}
		}
	}
	
	private void createDrawPoints(final int x, final int y, final int width, final int height) {
		topDrawPoints[0].setLocation(x, y);
		topDrawPoints[1].setLocation(x + width, y);
		leftDrawPoints[0].setLocation(x, y);
		leftDrawPoints[1].setLocation(x, y + height);
		rightDrawPoints[0].setLocation(x + width, y);
		rightDrawPoints[1].setLocation(x + width, y + height);
		bottomDrawPoints[0].setLocation(x, y + height);
		bottomDrawPoints[1].setLocation(x + width, y + height);
		dLeftDrawPoints[0].setLocation(x, y);
		dLeftDrawPoints[1].setLocation(x + width, y + height);
		dRightDrawPoints[0].setLocation(x, y + height);
		dRightDrawPoints[1].setLocation(x + width, y);
	}
}