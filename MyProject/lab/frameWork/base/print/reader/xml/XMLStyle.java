package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.XMLConstants.*;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import frameWork.base.print.element.HorizontalAlignment;
import frameWork.base.print.element.LineStyleType;
import frameWork.base.print.element.VerticalAlignment;

/**
 * スタイル<br>
 */
class XMLStyle {
	/** 横位置 */
	final HorizontalAlignment horizontalAlignment;
	/** 縦位置 */
	final VerticalAlignment verticalAlignment;
	
	/** 縮小表示フラグ */
	//	private final boolean shrinkToFit;
	
	/** ボーダマップを取得します */
	final List<XMLBorder> borders;
	
	/** フォント */
	final Font font;
	
	// ---[ Note ]---------------------------------------
	// Style タグにAlignment, Border, Font の各タグがな.
	// い場合は、それぞれ Default スタイルから設定内容を.
	// 継承する.
	// 各タグがあって、その属性(子要素)がない場合は XML .
	// ファイルで定義されている既定値を使用する.
	// --------------------------------------------------
	@SuppressWarnings("hiding")
	public XMLStyle(final XMLStyle referStyle, final Element styleElement) {
		super();
		HorizontalAlignment horizontalAlignment;
		VerticalAlignment verticalAlignment;
		List<XMLBorder> borders;
		Font font;
		if (referStyle == null) {
			// 初期化
			horizontalAlignment = HorizontalAlignment.DefaultValue;
			verticalAlignment = VerticalAlignment.DefaultValue;
			borders = new ArrayList<>();
			font = DEFAULT_FONT;
		}
		else {
			// 継承元スタイルから値をコピー.
			horizontalAlignment = referStyle.horizontalAlignment;
			verticalAlignment = referStyle.verticalAlignment;
			borders = new ArrayList<>(referStyle.borders);
			font = referStyle.font.deriveFont(referStyle.font.getStyle());
		}
		final Element alignmentsElement = (Element) styleElement.getElementsByTagName(STYLE_ALIGNMENT).item(0);
		if (alignmentsElement != null) {
			// 横位置.
			horizontalAlignment = HorizontalAlignment.DefaultValue;
			final String horizontalAttribute = alignmentsElement.getAttribute(ATTR_HORIZONAL);
			if ((horizontalAttribute != null) && (!horizontalAttribute.isEmpty())) {
				if ((CENTER.equalsIgnoreCase(horizontalAttribute)) || (JUSTIFY.equalsIgnoreCase(horizontalAttribute))
				        || (CENTER_ACCROSS_SELECTION.equalsIgnoreCase(horizontalAttribute))
				        || (DISTRIBUTED.equalsIgnoreCase(horizontalAttribute))
				        || (JUSTIFY_DISTRIBUTED.equalsIgnoreCase(horizontalAttribute))) {
					horizontalAlignment = HorizontalAlignment.CENTER;
				}
				else if (RIGHT.equalsIgnoreCase(horizontalAttribute)) {
					horizontalAlignment = HorizontalAlignment.RIGHT;
				}
				else {
					horizontalAlignment = HorizontalAlignment.LEFT;
				}
			}
			
			// 縦位置.
			verticalAlignment = VerticalAlignment.DefaultValue;
			final String verticalAttribute = alignmentsElement.getAttribute(ATTR_VERTICAL);
			if ((verticalAttribute != null) && (!verticalAttribute.isEmpty())) {
				if ((CENTER.equals(verticalAttribute)) || (JUSTIFY.equals(verticalAttribute))
				        || (DISTRIBUTED.equals(verticalAttribute)) || (JUSTIFY_DISTRIBUTED.equals(verticalAttribute))) {
					verticalAlignment = VerticalAlignment.CENTER;
				}
				else if (BOTTOM.equals(verticalAttribute)) {
					verticalAlignment = VerticalAlignment.BOTTOM;
				}
				else {
					verticalAlignment = VerticalAlignment.TOP;
				}
			}
		}
		final NodeList bordersNodeList = styleElement.getElementsByTagName(STYLE_BORDER);
		if (bordersNodeList != null) {
			// ボーダ追加.
			for (int i = 0; i < bordersNodeList.getLength(); i++) {
				final Element borderElement = (Element) bordersNodeList.item(i);
				final XMLBorder newBorder = new XMLBorder(borderElement);
				// ボーダ追加.
				if (LineStyleType.NONE != newBorder.lineStyle) {
					borders.add(newBorder);
				}
			}
		}
		final Element fontElement = (Element) styleElement.getElementsByTagName(STYLE_FONT).item(0);
		if (fontElement != null) {
			// フォント名.
			String fontName = DEFAULT_FONT.getName();
			final String fontNamettribute = fontElement.getAttribute(ATTR_FONTNAME);
			if ((fontNamettribute != null) && (!fontNamettribute.isEmpty())) {
				fontName = fontNamettribute;
			}
			
			// ボールド.
			boolean bold = DEFAULT_FONT.isBold();
			final String boldAttribute = fontElement.getAttribute(ATTR_BOLD);
			if ((boldAttribute != null) && (!boldAttribute.isEmpty())) {
				bold = Boolean.parseBoolean(boldAttribute);
			}
			
			// イタリック.
			boolean italic = DEFAULT_FONT.isItalic();
			final String italicAttribute = fontElement.getAttribute(ATTR_ITALIC);
			if ((italicAttribute != null) && (!italicAttribute.isEmpty())) {
				italic = Boolean.parseBoolean(italicAttribute);
			}
			
			// フォントサイズ.
			float fontSize = -1f;
			final String fontSizeAttribute = fontElement.getAttribute(ATTR_SIZE);
			if ((fontSizeAttribute != null) && (!fontSizeAttribute.isEmpty())) {
				try {
					fontSize = Float.parseFloat(fontSizeAttribute);
				}
				catch (final NumberFormatException exp) {
					exp.printStackTrace();
				}
			}
			if (fontSize < 0f) {
				fontSize = DEFAULT_FONT.getSize();
			}
			// 新しいフォントの生成.
			final Font newFont = Font.decode(fontName).deriveFont(
			        (bold ? Font.BOLD : Font.PLAIN) + (italic ? Font.ITALIC : Font.PLAIN), fontSize);
			// フォントの更新.
			if (!font.equals(newFont)) {
				font = newFont;
			}
		}
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		this.borders = borders;
		this.font = font;
	}
}
