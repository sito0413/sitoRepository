package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import org.w3c.dom.Element;

/**
 * ワークシート<br>
 */
class XMLSheet {
	/** 用紙サイズ */
	final MediaSizeName paperSize;
	/** 用紙方向 */
	final OrientationRequested orientation;
	/** トップマージン(インチ数) */
	final double topMargin;
	/** レフトマージン(インチ数) */
	final double leftMargin;
	/** ライトマージン(インチ数) */
	final double rightMargin;
	/** ボトムマージン(インチ数) */
	final double bottomMargin;
	/** スケール */
	final double scale;
	/** テーブル情報 */
	final XMLTable table;
	
	@SuppressWarnings("hiding")
	XMLSheet(final Element worksheetElement) {
		MediaSizeName paperSize = (DEFAULT_PAPER_SIZE);
		OrientationRequested orientation = (DEFAULT_ORIENTATION);
		double topMargin = 0;
		double leftMargin = 0;
		double rightMargin = 0;
		double bottomMargin = 0;
		double scale = (DEFAULT_SCALE);
		
		// テーブル生成＆属性設定.
		this.table = new XMLTable((Element) worksheetElement.getElementsByTagName(ELM_TABLE).item(0));
		final Element optionsElement = (Element) worksheetElement.getElementsByTagName(ELM_WSOPTIONS).item(0);
		if (optionsElement != null) {
			// --------------------------------------------------
			// note
			// ELMOptionsオブジェクトを生成時に、各メンバ.
			// 既定値が設定されているので、数値項目変換時の.
			// 既定値として、それぞれの現在値を使用する.
			// --------------------------------------------------
			// ページセットアップ (用紙方向 & 各マージン).
			final Element pageSetupElement = (Element) optionsElement.getElementsByTagName(ELM_PAGESETUP).item(0);
			if (pageSetupElement != null) {
				// 用紙方向.
				final Element layoutElement = (Element) pageSetupElement.getElementsByTagName(ELM_LAYOUT).item(0);
				if (layoutElement != null) {
					final String orientationBuffer = layoutElement.getAttribute(ATTR_ORIENTATION);
					if (orientationBuffer.equalsIgnoreCase("Portrait")) {
						orientation = OrientationRequested.PORTRAIT;
					}
					else if (orientationBuffer.equalsIgnoreCase("Landscape")) {
						orientation = OrientationRequested.LANDSCAPE;
						
					}
				}
				// 印刷範囲マージン.
				final Element marginsElement = (Element) pageSetupElement.getElementsByTagName(ELM_PAGEMARGINS).item(0);
				if (marginsElement != null) {
					// トップマージン.
					topMargin = (parseDouble(marginsElement.getAttribute(ATTR_MARGINTOP), DEFAULT_TOP_MARGIN));
					// レフトマージン.
					leftMargin = (parseDouble(marginsElement.getAttribute(ATTR_MARGINLEFT), DEFAULT_LEFT_MARGIN));
					// ライトマージン.
					rightMargin = (parseDouble(marginsElement.getAttribute(ATTR_MARGINRIGHT), DEFAULT_RIGHT_MARGIN));
					// ボトムマージン.
					bottomMargin = (parseDouble(marginsElement.getAttribute(ATTR_MARGINBOTTOM), DEFAULT_BOTTOM_MARGIN));
				}
			}
			// 印刷設定 (用紙サイズ & スケール).
			final Element printElement = (Element) optionsElement.getElementsByTagName(ELM_PRINT).item(0);
			if (printElement != null) {
				// 用紙サイズ.
				final Element paperSizeElement = (Element) printElement.getElementsByTagName(ELM_PAPERSIZE).item(0);
				final String text = paperSizeElement.getTextContent();
				if (text != null) {
					try {
						final int i = Integer.parseInt(String.valueOf(text));
						if (A4 == i) {
							paperSize = MediaSizeName.ISO_A4;
						}
						else if (A3 == i) {
							paperSize = MediaSizeName.ISO_A3;
						}
					}
					catch (final NumberFormatException exp) {
						// NOOPexp.printStackTrace();
					}
				}
				// スケール.
				final Element scaleElementBuffer = (Element) printElement.getElementsByTagName(ELM_SCALE).item(0);
				if (scaleElementBuffer != null) {
					scale = (parseDouble(scaleElementBuffer.getTextContent(), scale));
				}
			}
		}
		this.paperSize = paperSize;
		this.orientation = orientation;
		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.rightMargin = rightMargin;
		this.bottomMargin = bottomMargin;
		this.scale = scale;
	}
	
}