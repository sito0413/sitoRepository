package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.XMLConstants.*;

import org.w3c.dom.Element;

import frameWork.base.print.element.LineStyleType;
import frameWork.base.print.element.LineWeight;
import frameWork.base.print.element.PositionType;

/**
 * ボーダ<br>
 */
class XMLBorder {
	/** 線の種類 */
	final LineStyleType lineStyle;
	
	/** 線の太さ */
	final LineWeight lineWeight;
	
	/** 線の位置 */
	final PositionType linePosition;
	
	@SuppressWarnings("hiding")
	XMLBorder(final Element borderElement) {
		LineStyleType lineStyle = null;
		LineWeight lineWeight = LineWeight.HAIRLINE;
		PositionType linePosition = null;
		
		// 位置.
		PositionType position = null;
		final String positionAttribute = borderElement.getAttribute(ATTR_POSITION);
		if ((positionAttribute != null) && (!positionAttribute.isEmpty())) {
			if ("Top".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.TOP;
			}
			else if ("Left".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.LEFT;
			}
			else if ("Right".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.RIGHT;
			}
			else if ("Bottom".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.BOTTOM;
			}
			else if ("DiagonalLeft".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.DLEFT;
			}
			else if ("DiagonalRight".equalsIgnoreCase(positionAttribute)) {
				position = PositionType.DRIGHT;
			}
			else {
				position = PositionType.TOP;
			}
		}
		
		// ライン太さ.
		LineWeight weight = LineWeight.HAIRLINE;
		final String lineWeightAttribute = borderElement.getAttribute(ATTR_WEIGHT);
		if ((lineWeightAttribute != null) && (!lineWeightAttribute.isEmpty())) {
			if ("0".equalsIgnoreCase(lineWeightAttribute)) {
				weight = LineWeight.HAIRLINE;
			}
			else if ("1".equalsIgnoreCase(lineWeightAttribute)) {
				weight = LineWeight.THIN;
			}
			else if ("2".equalsIgnoreCase(lineWeightAttribute)) {
				weight = LineWeight.MEDIUM;
			}
			else if ("3".equalsIgnoreCase(lineWeightAttribute)) {
				weight = LineWeight.THICK;
			}
			else {
				weight = LineWeight.HAIRLINE;
			}
		}
		
		// ラインスタイル.
		LineStyleType style = null;
		final String lineStyleAttribute = borderElement.getAttribute(ATTR_LINESTYLE);
		if ((lineStyleAttribute != null) && (!lineStyleAttribute.isEmpty())) {
			if (!NONE.equals(lineStyleAttribute)) {
				if ((CONTINUOUS.equals(lineStyleAttribute)) || (DASH.equals(lineStyleAttribute))
				        || (DOT.equals(lineStyleAttribute)) || (DASH_DOT.equals(lineStyleAttribute))
				        || (DASH_DOT_DOT.equals(lineStyleAttribute)) || (SLANT_DASH_DOT.equals(lineStyleAttribute))) {
					
					if (LineWeight.HAIRLINE.equals(weight)) {
						style = LineStyleType.DOTTED;
					}
					else {
						style = LineStyleType.CONTINUOUS;
					}
				}
				else if (DOUBLE.equals(lineStyleAttribute)) {
					style = LineStyleType.DOUBLE;
				}
			}
		}
		if (position != null) {
			lineStyle = style;
			lineWeight = weight;
			linePosition = position;
		}
		this.lineStyle = lineStyle;
		this.lineWeight = lineWeight;
		this.linePosition = linePosition;
	}
}
