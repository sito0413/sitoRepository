package framework2.base.report.reader;

import java.util.Vector;

import org.w3c.dom.Element;

import framework2.base.report.element.LineStyleType;
import framework2.base.report.element.LineWeight;
import framework2.base.report.element.PositionType;
import framework2.base.report.util.XMLConstants;

/**
 * ボーダ<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/15
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
@SuppressWarnings( {
        "nls", "null"
})
public class XMLBorder {
	private static enum XMLLineStyle {

		/** なし */
		NONE("None"),

		/** 連続 */
		CONTINUOUS("Continuous"),

		/** 破線 */
		DASH("Dash"),

		/** 点線 */
		DOT("Dot"),

		/** 一点鎖線 */
		DASH_DOT("DashDot"),

		/** 二点鎖線 */
		DASH_DOT_DOT("DashDotDot"),

		/** 傾斜一点鎖線 */
		SLANT_DASH_DOT("SlantDashDot"),

		/** 二重線 */
		DOUBLE("Double");

		/**
		 * コンストラクタ<br>
		 * 
		 * @param xmlValue
		 */
		private XMLLineStyle(final String xmlValue) {
			this.setXMLValue(xmlValue);
		}

		/** XMLファイルの値 */
		private String xmlValue;

		/**
		 * XMLファイルの値を取得します<br>
		 * 
		 * @return
		 */
		public String getXMLValue() {
			return this.xmlValue;
		}

		/**
		 * XMLファイルの値を設定します<br>
		 * 
		 * @param xmlValue
		 */
		private void setXMLValue(final String xmlValue) {
			this.xmlValue = xmlValue;
		}

		public LineStyleType toLineStyleType() {
			LineStyleType result;
			switch (this) {
				case CONTINUOUS:
					result = LineStyleType.CONTINUOUS;
					break;
				case DASH:
				case DOT:
				case DASH_DOT:
				case DASH_DOT_DOT:
				case SLANT_DASH_DOT:
					result = LineStyleType.DOTTED;
					break;
				case DOUBLE:
					result = LineStyleType.DOUBLE;
					break;
				case NONE:
				default:
					result = null;
			}
			return result;
		}

		public static XMLLineStyle parse(final Object obj) {
			if (obj == null) {
				return null;
			}
			if (obj instanceof XMLLineStyle) {
				return (XMLLineStyle) obj;
			}
			XMLLineStyle result = null;
			String s = String.valueOf(obj);
			for (XMLLineStyle lineStyle : XMLLineStyle.getList()) {
				if (lineStyle.getXMLValue().equals(s)) {
					result = lineStyle;
					break;
				}
			}
			return result;
		}

		private static Vector<XMLLineStyle> getList() {
			Vector<XMLLineStyle> list = new Vector<XMLLineStyle>();
			list.add(XMLLineStyle.NONE);
			list.add(XMLLineStyle.CONTINUOUS);
			list.add(XMLLineStyle.DASH);
			list.add(XMLLineStyle.DOT);
			list.add(XMLLineStyle.DASH_DOT);
			list.add(XMLLineStyle.DASH_DOT_DOT);
			list.add(XMLLineStyle.SLANT_DASH_DOT);
			list.add(XMLLineStyle.DOUBLE);
			return list;
		}

	}

	/** 線の種類 */
	private LineStyleType lineStyle;

	/** 線の太さ */
	private float lineWeight;

	private PositionType linePosition;

	/**
	 * コンストラクタ<br>
	 * 
	 * @param referBorder
	 */
	public XMLBorder(final XMLBorder referBorder) {
		super();
		this.initialize(referBorder);
	}

	public XMLBorder(final Element borderElement) {
		lineStyle = (XMLLineStyle.NONE.toLineStyleType());
		lineWeight = 0.5f;

		// 位置.
		PositionType position = null;
		String positionAttribute = borderElement.getAttribute(XMLConstants.ATTR_POSITION);
		if ((positionAttribute != null) && (!positionAttribute.isEmpty())) {
			position = parseXMLBorderPosition(positionAttribute);
		}

		// ライン太さ.
		LineWeight weight = LineWeight.HAIRLINE;
		String lineWeightAttribute = borderElement.getAttribute(XMLConstants.ATTR_WEIGHT);
		if ((lineWeightAttribute != null) && (!lineWeightAttribute.isEmpty())) {
			weight = parseXMLLineWeight(lineWeightAttribute);
		}

		// ラインスタイル.
		XMLLineStyle style = XMLLineStyle.NONE;
		String lineStyleAttribute = borderElement.getAttribute(XMLConstants.ATTR_LINESTYLE);
		if ((lineStyleAttribute != null) && (!lineStyleAttribute.isEmpty())) {
			style = XMLLineStyle.parse(lineStyleAttribute);
		}
		if (style != null) {
			switch (style) {
				case CONTINUOUS:
				case DASH:
				case DOT:
				case DASH_DOT:
				case DASH_DOT_DOT:
				case SLANT_DASH_DOT:
					// これらの線種は同じものとして扱う.
					style = XMLLineStyle.CONTINUOUS;
					if (LineWeight.HAIRLINE.equals(weight)) {
						style = XMLLineStyle.DOT;
					}
					break;
				default:
					break;
			}
		}
		if (position != null) {
			this.lineStyle = style.toLineStyleType();
			this.lineWeight = (weight).toFloat();
			this.linePosition = (position);
		}
		else {
			this.lineStyle = (XMLLineStyle.NONE).toLineStyleType();
		}

	}

	public LineWeight parseXMLLineWeight(final String lineWeightAttribute) {
		LineWeight result = LineWeight.HAIRLINE;
		if (lineWeightAttribute == null) {
			return result;
		}

		if ("0".equalsIgnoreCase(lineWeightAttribute)) {
			result = LineWeight.HAIRLINE;
		}
		else if ("1".equalsIgnoreCase(lineWeightAttribute)) {
			result = LineWeight.THIN;
		}
		else if ("2".equalsIgnoreCase(lineWeightAttribute)) {
			result = LineWeight.MEDIUM;
		}
		else if ("3".equalsIgnoreCase(lineWeightAttribute)) {
			result = LineWeight.THICK;
		}
		return result;
	}

	public PositionType parseXMLBorderPosition(final String positionAttribute) {
		PositionType result = PositionType.TOP;
		if (positionAttribute == null) {
			return result;
		}

		if ("Top".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.TOP;
		}
		else if ("Left".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.LEFT;
		}
		else if ("Right".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.RIGHT;
		}
		else if ("Bottom".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.BOTTOM;
		}
		else if ("DiagonalLeft".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.DLEFT;
		}
		else if ("DiagonalRight".equalsIgnoreCase(positionAttribute)) {
			result = PositionType.DRIGHT;
		}

		return result;
	}

	// --------------------------------------------------
	/**
	 * 初期化<br>
	 * 
	 * @param referBorder
	 */
	private void initialize(final XMLBorder referBorder) {
		if (referBorder == null) {
			lineStyle = (XMLLineStyle.NONE.toLineStyleType());
			lineWeight = 0.5f;
		}
		else {
			lineStyle = (referBorder.getLineStyle());
			lineWeight = (referBorder.getLineWeight());
		}
	}

	// --------------------------------------------------
	public LineStyleType getLineStyle() {
		return this.lineStyle;
	}

	// --------------------------------------------------

	public float getLineWeight() {
		return this.lineWeight;
	}

	// --------------------------------------------------

	public PositionType getPosition() {
		return linePosition;
	}

}
