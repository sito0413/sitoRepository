package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 行情報<br>
 */
@SuppressWarnings("nls")
class XMLRow {
	
	/** 行インデックス */
	final int index;
	
	/** 行高さ */
	final double height;
	
	/** 繰り返し数 */
	final int span;
	
	/** スタイルID */
	final String styleId;
	
	/** セル情報 */
	final List<XMLCell> cells;
	
	XMLRow(final int index, final int span, final double height) {
		this.index = index;
		this.span = span;
		this.height = height;
		this.styleId = "";
		// セル.
		this.cells = new ArrayList<>();
		
	}
	
	/**
	 * 行情報を変換します<br>
	 */
	XMLRow(final Node rowNode, final int index, final double defaultHeight) {
		// 行情報が存在しない場合は、既定値の行情報を返す.
		if (rowNode == null) {
			this.index = index;
			this.span = 0;
			this.height = 0;
			this.styleId = "";
			this.cells = new ArrayList<>();
		}
		else {
			// 行情報変換.
			final Element rowElement = (Element) rowNode;
			this.index = (parseInt(rowElement.getAttribute(ATTR_INDEX), index));
			this.span = (parseInt(rowElement.getAttribute(ATTR_SPAN), 0));
			this.height = (parseDouble(rowElement.getAttribute(ATTR_HEIGHT), defaultHeight));
			this.styleId = (rowElement.getAttribute(ATTR_STYLEID));
			this.cells = new ArrayList<>();
			final NodeList cellsNodeList = rowElement.getElementsByTagName(ELM_CELL);
			if (cellsNodeList != null) {
				int idx = 0;
				for (int i = 0; i < cellsNodeList.getLength(); i++) {
					final Element cellElement = (Element) cellsNodeList.item(i);
					final XMLCell newCell = new XMLCell(idx, cellElement);
					while (newCell.index >= cells.size()) {
						cells.add(null);
					}
					// セル追加.
					cells.set(newCell.index, newCell);
					// インデックス更新
					idx = newCell.index + newCell.mergeAccross;
				}
			}
		}
	}
	
	XMLCell getCell(final int colIndex) {
		while (colIndex >= cells.size()) {
			cells.add(null);
		}
		return cells.get(colIndex);
	}
}