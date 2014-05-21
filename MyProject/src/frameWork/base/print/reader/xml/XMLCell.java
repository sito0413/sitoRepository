package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * セル<br>
 *
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/18
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/22
 */
class XMLCell {
	
	/** 位置 */
	final int index;
	
	/** 下方向への結合数 */
	final int mergeDown;
	
	/** 右方向への結合数 */
	final int mergeAccross;
	
	/** スタイルID */
	final String styleId;
	
	/** セルの値 */
	final String value;
	
	XMLCell(final int idx, final Element cellElement) {
		// インデックス
		index = (parseInt(cellElement.getAttribute(ATTR_INDEX), idx + 1));
		// 縦結合.
		mergeDown = (parseInt(cellElement.getAttribute(ATTR_MERGEDOWN), 0));
		// 横結合.
		mergeAccross = (parseInt(cellElement.getAttribute(ATTR_MERGEACROSS), 0));
		// スタイルID
		styleId = (cellElement.getAttribute(ATTR_STYLEID));
		// 値.
		final NodeList dataNodeList = cellElement.getElementsByTagName(ELM_DATA);
		if (dataNodeList.getLength() > 0) {
			this.value = dataNodeList.item(0).getTextContent();
		}
		else {
			this.value = null;
		}
	}
	
}
