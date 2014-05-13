package framework2.base.report.book.xml;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.print.Paper;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

import framework2.base.report.ReportCell;
import framework2.base.report.ReportPage;

/**
 * 帳票レイアウトXMLファイル読込<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/20
 */
public class ReportReader {

	static int getintWidth(final int columnIndex, final int iniWidth,
			final int mergeAccross, final Map<Integer, XMLColumn> columnMap,
			final double scaleRate) {
		int width = iniWidth;
		for (int i = 1; i <= mergeAccross; i++) {
			if (columnIndex + i > columnMap.size()) {
				break;
			}
			width += UnitConversion.getWidthPixels(
					columnMap.get(columnIndex + i).getWidth(), scaleRate);
		}
		return width;
	}

	static int getHeight(final int rowIndex, final int iniHeight,
			final int mergeDown, final Map<Integer, XMLRow> rowMap,
			final double scaleRate) {
		int height = iniHeight;
		for (int i = 1; i <= mergeDown; i++) {
			if (rowIndex + i > rowMap.size()) {
				break;
			}
			height += UnitConversion.getHeightPixels(rowMap.get(rowIndex + i)
					.getHeight(), scaleRate);
		}
		return height;
	}

	static XMLStyle getStyle(final Map<String, XMLStyle> styleMap,
			final String cellStyleId, final String rowStyleId,
			final String columnStyleId) {
		// 属性取得 (セル → 行 → 列 → シート(標準) の順に検索).
		XMLStyle style = styleMap.get(cellStyleId);
		if (style == null) {
			style = styleMap.get(rowStyleId);
			if (style == null) {
				style = styleMap.get(columnStyleId);
				if (style == null) {
					style = styleMap.get(XMLConstants.DEFAULTSTYLE_ID);
				}
			}
		}

		return style;
	}

	/**
	 * 印刷可能列確認<br>
	 * 
	 * @param worksheet
	 * @param pagePanel
	 * @return
	 */
	static Map<Integer, XMLColumn> inquirePrintableColumns(
			final Set<XMLColumn> columnSet, final double scaleRate) {
		Map<Integer, XMLColumn> newColumnsMap = new ConcurrentSkipListMap<>();
		int index = 0;
		for (XMLColumn column : columnSet) {
			for (int i = 0; i <= column.getSpan(); i++) {
				newColumnsMap.put(++index, column);
			}
		}
		return newColumnsMap;
	}

	/**
	 * 印刷可能行確認<br>
	 * 
	 * @param worksheet
	 * @param pagePanel
	 * @return
	 */
	static Map<Integer, XMLRow> inquirePrintableRows(final Set<XMLRow> rowSet,
			final double scaleRate) {
		Map<Integer, XMLRow> newRowsMap = new ConcurrentSkipListMap<>();
		int index = 0;
		for (XMLRow row : rowSet) {
			for (int i = 0; i <= row.getSpan(); i++) {
				newRowsMap.put(++index, row);
			}
		}
		return newRowsMap;
	}

	static List<Future<ReportCell>> createFutureList(final XMLSheet worksheet,
			final Map<String, XMLStyle> styleMap, final ReportPage page,
			final double scaleRate) {
		final XMLTable table = worksheet.getTable();
		final Map<Integer, XMLColumn> columnMap = inquirePrintableColumns(
				table.getColumns(), scaleRate);
		final Map<Integer, XMLRow> rowMap = inquirePrintableRows(
				table.getRows(), scaleRate);

		List<Future<ReportCell>> futureList = new CopyOnWriteArrayList<>();
		for (int rowIndex = 1; rowIndex <= rowMap.size(); rowIndex++) {
			for (int columnIndex = 1; columnIndex <= columnMap.size(); columnIndex++) {
				createFuture(styleMap, columnIndex, columnMap, rowIndex,
						rowMap, scaleRate, page);
			}
		}
		return futureList;
	}

	static void createFuture(final Map<String, XMLStyle> styleMap,
			final int columnIndex, final Map<Integer, XMLColumn> columnMap,
			final int rowIndex, final Map<Integer, XMLRow> rowMap,
			final double scaleRate, final ReportPage page) {
		XMLColumn column = columnMap.get(columnIndex);
		int columnWidth = UnitConversion.getWidthPixels(column.getWidth(),
				scaleRate);
		int x = 0;
		for (int i = 1; i < columnIndex; i++) {
			x += UnitConversion.getWidthPixels(columnMap.get(i).getWidth(),
					scaleRate);
		}

		XMLRow row = rowMap.get(rowIndex);
		int rowHeight = UnitConversion.getHeightPixels(row.getHeight(),
				scaleRate);
		int y = 0;
		for (int i = 1; i < rowIndex; i++) {
			y += UnitConversion.getHeightPixels(rowMap.get(i).getHeight(),
					scaleRate);
		}

		XMLCell cell = row.getCell(columnIndex);
		if (cell != null) {
			XMLStyle style = getStyle(styleMap, cell.getStyleId(),
					row.getStyleId(), column.getStyleId());
			ReportCell reportCell = page.getTable().getCellAtIndex(columnIndex,
					rowIndex);
			reportCell.setBounds(new Rectangle(x, y, getintWidth(columnIndex,
					columnWidth, cell.getMergeAccross(), columnMap, scaleRate),
					getHeight(rowIndex, rowHeight, cell.getMergeDown(), rowMap,
							scaleRate)));
			Font font = style.getFont();
			// フォント設定.
			if (scaleRate != 1d) {
				float fontSize = (float) (font.getSize2D() * scaleRate);
				font = font.deriveFont(fontSize);
			}
			reportCell.setFont(font);
			reportCell.setHorizontalAlignment(style.getHorizontalAlignment());
			reportCell.setVerticalAlignment(style.getVerticalAlignment());
			reportCell.setText(cell.getValue());
			// 罫線追加.
			if (style.hasBorder()) {
				for (XMLBorder border : style.getBorders()) {
					switch (border.getPosition()) {
					case TOP:
						reportCell.getBorder().getTop().setVisible(true);
						reportCell.getBorder().getTop()
								.setWidth(border.getLineWeight());
						break;
					case LEFT:
						reportCell.getBorder().getLeft().setVisible(true);
						reportCell.getBorder().getLeft()
								.setWidth(border.getLineWeight());
						break;
					case BOTTOM:
						reportCell.getBorder().getBottom().setVisible(true);
						reportCell.getBorder().getBottom()
								.setWidth(border.getLineWeight());
						break;
					case RIGHT:
						reportCell.getBorder().getRight().setVisible(true);
						reportCell.getBorder().getRight()
								.setWidth(border.getLineWeight());
						break;
					default:
						break;
					}
				}
			}
		}
	}

	// ------------------------------------------------------
	public final synchronized static ReportPage readSheet(final URI uri,
			final String sheetName, final ReportPage page) {
		XMLBook book = new XMLBook(uri);
		XMLSheet worksheet = book.getWorksheet(sheetName);
		Dimension size = UnitConversion.inquirePageSize(worksheet);
		double scaleRate = worksheet.getScale();
		Insets insets = UnitConversion.getMarginPixels(worksheet, scaleRate);
		Paper paper = new Paper();
		paper.setSize(size.width, size.height);
		paper.setImageableArea(insets.top, insets.left, size.width
				- (insets.left + insets.right), size.height
				- (insets.top + insets.bottom));
		page.getPageFormat().setPaper(paper);
		page.getTable().setSize(new Dimension(size.width, size.height));
		createFutureList(worksheet, book.getStylesMap(), page, scaleRate);
		return page;

	}
}
