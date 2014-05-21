package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * カラム<br>
 */
class XMLColumn {
	
	/** 位置 */
	final int index;
	
	/** 繰り返し数 */
	final int span;
	
	/** 幅 */
	final double width;
	
	/** スタイル */
	final String styleId;
	
	/**
	 * 既定値の列情報を取得します<br>
	 */
	XMLColumn(final int index, final int span, final double width) {
		this.index = index;
		this.span = span;
		this.width = width;
		this.styleId = "";
	}
	
	XMLColumn(final Node columnNode, final int index, final double defaultWidth) {
		// 列情報が存在しない場合は、既定値の列情報を返す.
		if (columnNode == null) {
			this.index = index;
			this.span = 0;
			this.width = defaultWidth;
			this.styleId = "";
		}
		else {
			final Element columnElement = (Element) columnNode;
			this.index = (parseInt(columnElement.getAttribute(ATTR_INDEX), index));
			this.span = (parseInt(columnElement.getAttribute(ATTR_SPAN), 0));
			this.width = (parseDouble(columnElement.getAttribute(ATTR_WIDTH), defaultWidth));
			this.styleId = (columnElement.getAttribute(ATTR_STYLEID));
		}
	}
}
