package frameWork.base.print.element;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/** ボーダ情報 */
public class BorderInfo {
	public final LineStyleType lineStyle;
	public final float weight;
	public final PositionType position;
	
	public BorderInfo(final PositionType position, final LineStyleType lineStyle, final LineWeight weight) {
		this.lineStyle = lineStyle;
		this.weight = weight.toFloat();
		this.position = position;
	}
	
	void drowLine(final Graphics2D g2D, final Point start, final Point end) {
		// 線種が指定されていない場合はボーダを描画しない.
		if (lineStyle == null) {
			return;
		}
		// ストローク設定.
		g2D.setStroke(getStroke());
		
		// 描画.
		if (LineStyleType.DOUBLE == lineStyle) {
			drowDoubleLine(g2D, start, end);
		}
		else {
			g2D.drawLine(start.x, start.y, end.x, end.y);
		}
	}
	
	private Stroke getStroke() {
		if (LineStyleType.DOUBLE == lineStyle) {
			return new BasicStroke(LineWeight.THIN.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		}
		else if (LineStyleType.DOTTED == lineStyle) {
			return new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {
				LineWeight.HAIRLINE.toFloat()
			}, 0.0f);
		}
		else {
			return new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
		}
	}
	
	private void drowDoubleLine(final Graphics2D g2D, final Point start, final Point end) {
		for (int i = 0; i < 2; i++) {
			int sx = start.x;
			int sy = start.y;
			int ex = end.x;
			int ey = end.y;
			final int doubleI = i * 2;
			switch ( position ) {
				case TOP :
				case BOTTOM :
					sx += 0;
					sy += doubleI - 1;
					ex += 0;
					ey += doubleI - 1;
					break;
				case LEFT :
				case RIGHT :
					sx += doubleI - 1;
					sy += 0;
					ex += doubleI - 1;
					ey += 0;
					break;
				case DLEFT :
					sx += doubleI;
					sy += (1 - i);
					ex += (1 - i) * -2;
					ey += (i) * -1;
					break;
				case DRIGHT :
					sx += (i) * 2;
					sy += (1 - i) * -1;
					ex += (1 - i) * -2;
					ey += (i);
					break;
				default :
					// 何もしない.
					break;
			}
			g2D.drawLine(sx, sy, ex, ey);
		}
	}
}