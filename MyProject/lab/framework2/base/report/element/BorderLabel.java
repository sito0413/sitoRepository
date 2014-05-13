package framework2.base.report.element;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JComponent;

import framework2.base.report.element.CellPanel.BorderInfo;


/**
 * ボーダ<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/19
 * @最終更新者 瀬谷<br>
 * @最終更新日 2009/01/20
 */
@SuppressWarnings("unused")
public class BorderLabel extends JComponent {
	public BorderLabel() {
		topDrawPoints = new Vector<Point>();
		leftDrawPoints = new Vector<Point>();
		rightDrawPoints = new Vector<Point>();
		bottomDrawPoints = new Vector<Point>();
		dLeftDrawPoints = new Vector<Point>();
		dRightDrawPoints = new Vector<Point>();

		topDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		topDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		leftDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		leftDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		rightDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		rightDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		bottomDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		bottomDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		dLeftDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		dLeftDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		dRightDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		dRightDrawPoints.add(new Point(BorderLabel.EXPAND_SIZE, BorderLabel.EXPAND_SIZE));
		setOpaque(false);
	}

	/**
	 * このオブジェクトのコピーを作成して、返します<br>
	 * ただし、基準となる CellPanel のクローンは作成されず、<br>
	 * null が設定されます<br>
	 */
	@Override
	public BorderLabel clone() throws CloneNotSupportedException {
		BorderLabel newBorderLabel = new BorderLabel();
		return newBorderLabel;
	}

	// --------------------------------------------------

	/** ボーダ描画のために各方向へ拡張するサイズ */
	private static final int EXPAND_SIZE = LineWeight.THICK.toInteger();

	/** 開始点インデックス */
	private static final int START_POINT_INDEX = 0;

	/** 終了点インデックス */
	private static final int END_POINT_INDEX = 1;

	// --------------------------------------------------

	/*
	 * コンポーネントを描画します<br>
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		// if (getAncher().isVisible()) {
		// Graphics2D g2D = (Graphics2D) g;
		// g2D.setColor(Color.BLACK);
		// createDrawPoints(getAncher().getWidth(), getAncher().getHeight());
		// List<BorderInfo> borderInfos = this.getAncher().getBorderInfos();
		// for (int i = 0; i < borderInfos.size(); i++) {
		// drowLine(g2D, getAncher().getWidth(), getAncher().getHeight(),
		// borderInfos.get(i));
		// }
		// }
	}

	private void drowLine(final Graphics2D g2D, final int width, final int height, final BorderInfo entry) {
		PositionType position = entry.getPosition();
		LineStyleType lineStyle = entry.getStyle();
		float weight = entry.getWeight();
		// 線種が指定されていない場合はボーダを描画しない.
		if (lineStyle == null) {
			return;
		}
		// ストローク設定.
		g2D.setStroke(getStroke(lineStyle, weight));

		// 描画.
		Vector<Point> drawPoints = getDrawPoints(width, height, position);
		Point start = drawPoints.get(BorderLabel.START_POINT_INDEX);
		Point end = drawPoints.get(BorderLabel.END_POINT_INDEX);
		if (LineStyleType.DOUBLE.equals(lineStyle)) {
			drowDoubleLine(g2D, start, end, position);
		}
		else {
			g2D.drawLine(start.x, start.y, end.x, end.y);
		}
	}

	private static void drowDoubleLine(final Graphics2D g2D, final Point start, final Point end, final PositionType position) {
		for (int i = 0; i < 2; i++) {
			int sx = start.x;
			int sy = start.y;
			int ex = end.x;
			int ey = end.y;
			int doubleI = i * 2;
			switch (position) {
				case TOP:
				case BOTTOM:
					sx += 0;
					sy += doubleI - 1;
					ex += 0;
					ey += doubleI - 1;
					break;
				case LEFT:
				case RIGHT:
					sx += doubleI - 1;
					sy += 0;
					ex += doubleI - 1;
					ey += 0;
					break;
				case DLEFT:
					sx += doubleI;
					sy += (1 - i);
					ex += (1 - i) * -2;
					ey += (i) * -1;
					break;
				case DRIGHT:
					sx += (i) * 2;
					sy += (1 - i) * -1;
					ex += (1 - i) * -2;
					ey += (i);
					break;
				default:
					// 何もしない.
			}
			g2D.drawLine(sx, sy, ex, ey);
		}
	}

	// --------------------------------------------------
	private static final Stroke DOUBLELINE_STROKE;
	private static final Stroke HAIRLINE_STROKE;
	private static final Stroke THIN_STROKE;
	private static final Stroke MEDIUM_STROKE;
	private static final Stroke THICK_STROKE;
	private static final Stroke DOTTED_HAIRLINE_STROKE;
	private static final Stroke DOTTED_THIN_STROKE;
	private static final Stroke DOTTED_MEDIUM_STROKE;
	private static final Stroke DOTTED_THICK_STROKE;
	static {
		DOUBLELINE_STROKE = new BasicStroke(LineWeight.THIN.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		HAIRLINE_STROKE = new BasicStroke(LineWeight.HAIRLINE.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		THIN_STROKE = new BasicStroke(LineWeight.THIN.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		MEDIUM_STROKE = new BasicStroke(LineWeight.MEDIUM.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		THICK_STROKE = new BasicStroke(LineWeight.THICK.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		DOTTED_HAIRLINE_STROKE = new BasicStroke(LineWeight.HAIRLINE.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
		        1.0f, new float[] { LineWeight.HAIRLINE.toFloat() }, 0.0f);
		DOTTED_THIN_STROKE = new BasicStroke(LineWeight.THIN.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
		        new float[] { LineWeight.THIN.toFloat() }, 0.0f);
		DOTTED_MEDIUM_STROKE = new BasicStroke(LineWeight.MEDIUM.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
		        new float[] { LineWeight.MEDIUM.toFloat() }, 0.0f);
		DOTTED_THICK_STROKE = new BasicStroke(LineWeight.THICK.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
		        new float[] { LineWeight.THICK.toFloat() }, 0.0f);
	}

	/**
	 * ストロークを取得します<br>
	 * 
	 * @param style
	 * @param weight
	 * @return
	 */
	private static Stroke getStroke(final LineStyleType style, final float weight) {
		float wrkWeight = weight;
		Stroke stroke;

		if (LineStyleType.DOUBLE.equals(style)) {
			stroke = DOUBLELINE_STROKE;
		}
		else if (LineStyleType.DOTTED.equals(style)) {
			stroke = new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
			        new float[] { LineWeight.HAIRLINE.toFloat() }, 0.0f);
		}
		else {
			stroke = new BasicStroke(wrkWeight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		}
		//
		return stroke;
	}

	// --------------------------------------------------

	/** 基準セルパネル */
	private CellPanel ancherPanel;

	/**
	 * 基準セルパネルを取得します.
	 * 
	 * @return
	 */
	public CellPanel getAncher() {
		return this.ancherPanel;
	}

	/**
	 * 基準セルパネルを設定します.
	 * 
	 * @param ancher
	 */
	public void setAncher(final CellPanel ancher) {
		this.ancherPanel = ancher;
	}

	// --------------------------------------------------
	private final Vector<Point> topDrawPoints;
	private final Vector<Point> leftDrawPoints;
	private final Vector<Point> rightDrawPoints;
	private final Vector<Point> bottomDrawPoints;
	private final Vector<Point> dLeftDrawPoints;
	private final Vector<Point> dRightDrawPoints;

	private void createDrawPoints(final int width, final int height) {
		int x = BorderLabel.EXPAND_SIZE;
		int y = BorderLabel.EXPAND_SIZE;
		int newX = x + width;
		int newY = y + height;

		topDrawPoints.get(1).setLocation(newX, y);
		leftDrawPoints.get(1).setLocation(x, newY);
		rightDrawPoints.get(0).setLocation(newX, y);
		rightDrawPoints.get(1).setLocation(newX, newY);
		bottomDrawPoints.get(0).setLocation(x, newY);
		bottomDrawPoints.get(1).setLocation(newX, newY);
		dLeftDrawPoints.get(1).setLocation(newX, newY);
		dRightDrawPoints.get(0).setLocation(x, newY);
		dRightDrawPoints.get(1).setLocation(newX, y);
	}

	/**
	 * 描画の始点、終点を取得します<br>
	 * 
	 * @param position
	 * @return
	 */
	private Vector<Point> getDrawPoints(final int width, final int height, final PositionType position) {
		// 描画始点終点算出.
		Vector<Point> drawPoints;
		switch (position) {
			case TOP:
				drawPoints = topDrawPoints;
				break;
			case LEFT:
				drawPoints = leftDrawPoints;
				break;
			case RIGHT:
				drawPoints = rightDrawPoints;
				break;
			case BOTTOM:
				drawPoints = bottomDrawPoints;
				break;
			case DLEFT:
				drawPoints = dLeftDrawPoints;
				break;
			case DRIGHT:
				drawPoints = dRightDrawPoints;
				break;
			default:
				drawPoints = new Vector<Point>();
				drawPoints.add(new Point());
				drawPoints.add(new Point());
				break;
		}

		return drawPoints;
	}

	// --------------------------------------------------

}
