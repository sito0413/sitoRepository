package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * テーブル<br>
 */
class XMLTable {
	
	/** 列情報 */
	final List<XMLColumn> columns;
	/** 行情報 */
	final List<XMLRow> rows;
	
	XMLTable(final Element tableNode) {
		this.columns = new ArrayList<>();
		this.rows = new ArrayList<>();
		
		// 標準列幅.
		final double defaultColumnWidth = (parseDouble(tableNode.getAttribute(ATTR_DEFCOLUMNWIDTH),
		        DEFAULT_COLUMN_WIDTH));
		// 標準行高さ.
		final double defaultRowHeight = (parseDouble(tableNode.getAttribute(ATTR_DEFROWHEIGHT), DEFAULT_ROW_HEIGHT));
		// 列数.
		final int columnCount = (parseInt(tableNode.getAttribute(ATTR_EXCOLUMNCOUNT), 0));
		// 行数.
		final int rowCount = (parseInt(tableNode.getAttribute(ATTR_EXROWCOUNT), 0));
		
		// 列.
		{
			final NodeList columnsNodeList = tableNode.getElementsByTagName(ELM_COLUMN);
			if (columnsNodeList == null) {
				return;
			}
			int index = 0;
			for (int i = 0; i < columnsNodeList.getLength(); i++) {
				// 列情報変換.
				final XMLColumn newColumn = new XMLColumn(columnsNodeList.item(i), index + 1, defaultColumnWidth);
				// 列情報補完.
				// - 列情報が明示的に存在しない場合、列情報を補完する.
				if (index < (newColumn.index - 1)) {
					final int completeIndex = index + 1;
					columns.add(new XMLColumn(completeIndex, newColumn.index - completeIndex - 1, defaultColumnWidth));
				}
				// インデックス更新.
				index = newColumn.index + newColumn.span;
				// 列情報追加.
				columns.add(newColumn);
			}
			// 後方補完.
			// - 列数が table.getColumnCount() に届かない場合、列情報を補完する.
			if (index < columnCount) {
				columns.add(new XMLColumn(index + 1, columnCount - index - 1, defaultColumnWidth));
			}
		}
		
		// 行.
		{
			final NodeList rowsNodeList = tableNode.getElementsByTagName(ELM_ROW);
			if (rowsNodeList == null) {
				return;
			}
			int index = 0;
			for (int i = 0; i < rowsNodeList.getLength(); i++) {
				// 行情報変換.
				final XMLRow newRow = new XMLRow(rowsNodeList.item(i), index + 1, defaultRowHeight);
				// インデックス更新.
				index = newRow.index + newRow.span;
				// 前方補完.
				// - 最初の行情報が 2行目以降 の情報ならば、.
				// 1行目からの行情報を補完する.
				if ((i == 0) && (newRow.index > 1)) {
					rows.add(new XMLRow(1, newRow.index - 2, defaultRowHeight));
				}
				// 行情報追加.
				rows.add(newRow);
			}
			// 後方補完.
			// - 行数が table.getRowCount() に届かない場合、.
			// 最終行までの行情報を補完する.
			if (index < rowCount) {
				rows.add(new XMLRow(index + 1, rowCount - index - 1, defaultRowHeight));
			}
		}
	}
}
