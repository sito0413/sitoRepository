package framework2.base.report.book.xml;

import java.awt.Font;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * スタイル<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/15
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
public class XMLStyle {
	static final boolean DEFAULT_SHRINK_TO_FIT = false;
	@SuppressWarnings("nls")
	static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 10);
	@SuppressWarnings("nls")
	private static final Font ARIAL = new Font("Arial", 10, Font.PLAIN);

	/** 横位置 */
	private int horizontalAlignment;
	/** 縦位置を取得します */
	private int verticalAlignment;

	/** 縮小表示フラグ */
	private boolean shrinkToFit;

	/** ボーダマップを取得します */
	private List<XMLBorder> borders;

	/** フォント */
	private Font font;

	// ---[ Note ]---------------------------------------
	// Style タグにAlignment, Border, Font の各タグがな.
	// い場合は、それぞれ Default スタイルから設定内容を.
	// 継承する.
	// 各タグがあって、その属性(子要素)がない場合は XML .
	// ファイルで定義されている既定値を使用する.
	// --------------------------------------------------
	/**
	 * コンストラクタ<br>
	 * 
	 * @param referStyle
	 */
	public XMLStyle(final XMLStyle referStyle, final Element styleElement) {
		super();
		this.initialize(referStyle);
		this.parseAndSetAlignments((Element) styleElement.getElementsByTagName(
				XMLConstants.STYLE_ALIGNMENT).item(0));
		this.parseAndSetBorders(styleElement
				.getElementsByTagName(XMLConstants.STYLE_BORDER));
		this.parseAndSetFont((Element) styleElement.getElementsByTagName(
				XMLConstants.STYLE_FONT).item(0));
	}

	// --------------------------------------------------

	/**
	 * 初期化<br>
	 * 
	 * @param referStyle
	 */
	private void initialize(final XMLStyle referStyle) {
		if (referStyle == null) {
			// 初期化
			horizontalAlignment = XMLHorizontalAlignment.getDefaultValue();
			verticalAlignment = XMLVerticalAlignment.getDefaultValue();
			shrinkToFit = XMLStyle.DEFAULT_SHRINK_TO_FIT;
			borders = new CopyOnWriteArrayList<>();
			font = XMLStyle.DEFAULT_FONT;
		}
		else {
			// 継承元スタイルから値をコピー.
			horizontalAlignment = referStyle.getHorizontalAlignment();
			verticalAlignment = referStyle.getVerticalAlignment();
			shrinkToFit = referStyle.isShrinkToFit();
			borders = new CopyOnWriteArrayList<>(referStyle.getBorders());
			font = referStyle.getFont().deriveFont(
					referStyle.getFont().getStyle());
		}
	}

	// --------------------------------------------------

	/**
	 * 横位置を取得します<br>
	 * 
	 * @return
	 */
	public int getHorizontalAlignment() {
		return this.horizontalAlignment;
	}

	// --------------------------------------------------

	/**
	 * 縦位置を取得します<br>
	 * 
	 * @return verticalAlignment
	 */
	public int getVerticalAlignment() {
		return this.verticalAlignment;
	}

	// --------------------------------------------------

	/**
	 * 縮小表示フラグを取得します<br>
	 * 
	 * @return
	 */
	public boolean isShrinkToFit() {
		return this.shrinkToFit;
	}

	// --------------------------------------------------

	/**
	 * ボーダマップを取得します<br>
	 * 
	 * @return borders
	 */
	public List<XMLBorder> getBorders() {
		return this.borders;
	}

	public boolean hasBorder() {
		return (getBorders() != null) && (getBorders().size() > 0);
	}

	// --------------------------------------------------

	/**
	 * フォントを取得します<br>
	 * 
	 * @return font
	 */
	public Font getFont() {
		return this.font;
	}

	// --------------------------------------------------

	/**
	 * アラインメントを変換し設定します<br>
	 * 
	 * @param style
	 * @param alignmentsNode
	 */
	private void parseAndSetAlignments(final Element alignmentsElement) {
		if (alignmentsElement == null) {
			return;
		}
		// 横位置.
		horizontalAlignment = XMLHorizontalAlignment.getDefaultValue();
		String horizontalAttribute = alignmentsElement
				.getAttribute(XMLConstants.ATTR_HORIZONAL);
		if ((horizontalAttribute != null) && (!horizontalAttribute.isEmpty())) {
			horizontalAlignment = XMLHorizontalAlignment
					.parse(alignmentsElement
							.getAttribute(XMLConstants.ATTR_HORIZONAL));
		}

		// 縦位置.
		verticalAlignment = XMLVerticalAlignment.getDefaultValue();
		String verticalAttribute = alignmentsElement
				.getAttribute(XMLConstants.ATTR_VERTICAL);
		if ((verticalAttribute != null) && (!verticalAttribute.isEmpty())) {
			verticalAlignment = XMLVerticalAlignment.parse(alignmentsElement
					.getAttribute(XMLConstants.ATTR_VERTICAL));
		}

		// 縮小表示.
		shrinkToFit = false;
		String shrinkToFitAttribute = alignmentsElement
				.getAttribute(XMLConstants.ATTR_SHRINKTOFIT);
		if ((shrinkToFitAttribute != null) && (!shrinkToFitAttribute.isEmpty())) {
			Object object = alignmentsElement
					.getAttribute(XMLConstants.ATTR_SHRINKTOFIT);
			if (object != null) {
				shrinkToFit = Boolean.parseBoolean(object.toString());
			}
			else {
				shrinkToFit = false;
			}
		}
	}

	/**
	 * ボーダを変換し設定します.
	 * 
	 * @param style
	 * @param bordersNodeList
	 */
	private void parseAndSetBorders(final NodeList bordersNodeList) {
		if (bordersNodeList == null) {
			return;
		}
		// ボーダ追加.
		for (int i = 0; i < bordersNodeList.getLength(); i++) {
			Element borderElement = (Element) bordersNodeList.item(i);
			XMLBorder newBorder = new XMLBorder(borderElement);
			// ボーダ追加.
			if (!LineStyleType.NONE.equals(newBorder.getLineStyle())) {
				borders.add(newBorder);
			}
		}
	}

	/**
	 * フォントを変換し設定します.
	 * 
	 * @param style
	 * @param alignmentsNode
	 */
	private void parseAndSetFont(final Element fontElement) {
		if (fontElement == null) {
			return;
		}
		// フォント名.
		String fontName = XMLStyle.ARIAL.getName();
		String fontNamettribute = fontElement
				.getAttribute(XMLConstants.ATTR_FONTNAME);
		if ((fontNamettribute != null) && (!fontNamettribute.isEmpty())) {
			fontName = fontNamettribute;
		}

		// ボールド.
		boolean bold = XMLStyle.ARIAL.isBold();
		String boldAttribute = fontElement.getAttribute(XMLConstants.ATTR_BOLD);
		if ((boldAttribute != null) && (!boldAttribute.isEmpty())) {
			bold = Boolean.parseBoolean(boldAttribute);
		}

		// イタリック.
		boolean italic = XMLStyle.ARIAL.isItalic();
		String italicAttribute = fontElement
				.getAttribute(XMLConstants.ATTR_ITALIC);
		if ((italicAttribute != null) && (!italicAttribute.isEmpty())) {
			italic = Boolean.parseBoolean(italicAttribute);
		}

		// フォントサイズ.
		float fontSize = -1f;
		String fontSizeAttribute = fontElement
				.getAttribute(XMLConstants.ATTR_SIZE);
		if ((fontSizeAttribute != null) && (!fontSizeAttribute.isEmpty())) {
			try {
				fontSize = Float.parseFloat(fontSizeAttribute);
			}
			catch (NumberFormatException exp) {
				exp.printStackTrace();
			}
		}
		if (fontSize < 0f) {
			fontSize = XMLStyle.ARIAL.getSize();
		}
		// 新しいフォントの生成.
		Font newFont = Font.decode(fontName).deriveFont(
				(bold ? Font.BOLD : Font.PLAIN)
						+ (italic ? Font.ITALIC : Font.PLAIN), fontSize);
		// フォントの更新.
		if (!getFont().equals(newFont)) {
			font = newFont;
		}
	}

}
