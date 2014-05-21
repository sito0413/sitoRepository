package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.UnitConversion.*;
import static frameWork.base.print.reader.xml.XMLConstants.*;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.BorderInfo;
import frameWork.base.print.element.Cell;
import frameWork.base.print.element.Page;
import frameWork.base.print.reader.Reader;

/**
 * 帳票レイアウトXMLファイル読込<br>
 */
public class XMLReader implements Reader {
	/** 列(セル)幅算出係数 */
	private static final double WIDTH_COEFFICIENT = 1.0d;
	/** 行(セル)高さ算出係数 */
	private static final double HEIGHT_COEFFICIENT = 0.9d;
	
	@Override
	public final synchronized Page readSheet(final URI uri, final String sheetName) throws ReportException {
		final XMLBook book = read(uri);
		final XMLSheet worksheet = book.worksheetsMap.get(sheetName);
		if (worksheet == null) {
			throw new ReportException();
		}
		
		// セル配置.
		return createPage(worksheet, book.stylesMap);
		
	}
	
	private XMLBook read(final URI url) throws ReportException {
		try {
			return new XMLBook(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(url)));
		}
		catch (SAXException | ParserConfigurationException | IOException e) {
			throw new ReportException("XMLの読み込み処理に失敗しました", e);
		}
	}
	
	// --------------------------------------------------
	
	//ページサイズ確認<br>
	private Dimension inquirePageSize(final XMLSheet worksheets) {
		// 定数定義.
		final int shortEdgeIndex = 0;
		final int longEdgeIndex = 1;
		final int unit = Size2DSyntax.MM;
		// 用紙辺長さ取得.
		final MediaSize paperSize = MediaSize.getMediaSizeForName(worksheets.paperSize);
		final float[] edgeLength = paperSize.getSize(unit);
		final int shortEdge = getPaperEdgePixels(edgeLength[shortEdgeIndex], unit);
		final int longEdge = getPaperEdgePixels(edgeLength[longEdgeIndex], unit);
		// ページサイズ算出.
		int width;
		int height;
		if (OrientationRequested.LANDSCAPE.equals(worksheets.orientation)) {
			width = longEdge;
			height = shortEdge;
		}
		else {
			width = shortEdge;
			height = longEdge;
		}
		//
		return new Dimension(width, height);
	}
	
	// 用紙の辺の長さをピクセル数で取得します<br>
	// 用紙サイズは拡大縮小率の影響を受けない.
	private static int getPaperEdgePixels(final double length, final int unit) {
		return (int) Math.floor(unit == Size2DSyntax.MM ? millimetersToPixels(length) : inchesToPixels(length));
	}
	
	// 用紙のマージンをピクセル数で取得します
	// 帳票レイアウト XML ファイルでのマージンは、.
	// インチ単位で記述されている.
	// 拡大縮小率によって変更される.
	private int getMarginPixels(final double length, final double scaleRate) {
		return (int) Math.ceil(inchesToPixels(length, scaleRate));
	}
	
	// 印刷可能列確認<br>
	private Map<Integer, XMLColumn> inquirePrintableColumns(final XMLSheet worksheet, final Page pagePanel,
	        final double widthCoefficient) {
		final Map<Integer, XMLColumn> newColumnsMap = new ConcurrentSkipListMap<>();
		int index = 0;
		int totalWidth = 0;
		for (final XMLColumn column : worksheet.table.columns) {
			// 列幅算出.
			final int width = getWidthPixels(column.width, widthCoefficient);
			// 繰り返し列処理.
			for (int i = -1; i < column.span; i++) {
				// 列インデックス更新.
				index++;
				// 列情報追加.
				newColumnsMap.put(index, column);
				// 幅確認.
				totalWidth += width;
				if (totalWidth >= pagePanel.getWidth()) {
					break;
				}
			}
			//
			if (totalWidth >= pagePanel.getWidth()) {
				break;
			}
		}
		//
		return newColumnsMap;
	}
	
	// 印刷可能行確認<br>
	private Map<Integer, XMLRow> inquirePrintableRows(final XMLSheet worksheet, final Page pagePanel,
	        final double heightCoefficient) {
		final Map<Integer, XMLRow> newRowsMap = new ConcurrentSkipListMap<>();
		int index = 0;
		int totalHeight = 0;
		for (final XMLRow row : worksheet.table.rows) {
			// 行高さ算出.
			final int height = getHeightPixels(row.height, heightCoefficient);
			// 繰り返し処理.
			for (int i = -1; i < row.span; i++) {
				// 行インデックス更新.
				index++;
				// 行情報追加.
				newRowsMap.put(index, row);
				// 高さ確認.
				totalHeight += height;
				if (totalHeight >= pagePanel.getHeight()) {
					break;
				}
			}
			if (totalHeight >= pagePanel.getHeight()) {
				break;
			}
		}
		return newRowsMap;
	}
	
	// ページコンテンツを生成します<br>
	private Page createPage(final XMLSheet worksheet, final Map<String, XMLStyle> styles) {
		// スケールの保持.
		final double scaleRate = worksheet.scale / 100d;
		final double heightCoefficient = HEIGHT_COEFFICIENT * scaleRate;
		final double widthCoefficient = WIDTH_COEFFICIENT * scaleRate;
		final Dimension pageSize = inquirePageSize(worksheet);
		
		final Page page = new Page(getMarginPixels(worksheet.leftMargin, scaleRate), getMarginPixels(
		        worksheet.topMargin, scaleRate), getMarginPixels(worksheet.rightMargin, scaleRate), getMarginPixels(
		        worksheet.bottomMargin, scaleRate), pageSize.width, pageSize.height);
		final Map<Integer, XMLColumn> printableColumns = inquirePrintableColumns(worksheet, page, widthCoefficient);
		final Map<Integer, XMLRow> printableRows = inquirePrintableRows(worksheet, page, heightCoefficient);
		// セルパネル配置原点.
		int top = 0;
		final int rowMax = printableRows.size();
		final int colMax = printableColumns.size();
		for (int rowIndex = 1; rowIndex <= rowMax; rowIndex++) {
			final XMLRow row = printableRows.get(rowIndex);
			final int rowHeight = getHeightPixels(row.height, heightCoefficient);
			int left = 0;
			
			for (int columnIndex = 1; columnIndex <= colMax; columnIndex++) {
				final XMLColumn column = printableColumns.get(columnIndex);
				final int columnWidth = getWidthPixels(column.width, widthCoefficient);
				final XMLCell cell = row.getCell(columnIndex);
				if (cell != null) {
					// セル情報あり.
					// セル幅算出.
					int width = columnWidth;
					for (int i = 1; i <= cell.mergeAccross; i++) {
						if ((columnIndex + i) > printableColumns.size()) {
							break;
						}
						width += getWidthPixels(printableColumns.get(columnIndex + i).width, widthCoefficient);
					}
					// セル高さ算出.
					int height = rowHeight;
					for (int i = 1; i <= cell.mergeDown; i++) {
						if ((rowIndex + i) > printableRows.size()) {
							break;
						}
						height += getHeightPixels(printableRows.get(rowIndex + i).height, heightCoefficient);
					}
					// 属性取得 (セル → 行 → 列 → シート(標準) の順に検索).
					XMLStyle style = styles.get(cell.styleId);
					if (style == null) {
						style = styles.get(row.styleId);
						if (style == null) {
							style = styles.get(column.styleId);
							if (style == null) {
								style = styles.get(DEFAULTSTYLE_ID);
							}
						}
					}
					// セルパネル生成.
					final List<BorderInfo> borderInfos = new ArrayList<>();
					// 罫線追加.
					for (final XMLBorder border : style.borders) {
						borderInfos.add(new BorderInfo(border.linePosition, border.lineStyle, border.lineWeight));
					}
					page.addCell(new Cell(rowIndex - 1, columnIndex - 1, left, top, width, height, width, height,
					        style.font, style.horizontalAlignment, style.verticalAlignment, cell.value, borderInfos,
					        scaleRate));
				}
				// レフト位置更新.
				left += columnWidth;
			}
			// トップ位置更新.
			top += rowHeight;
		}
		return page;
	}
	
	// 帳票レイアウト XML ファイルでの列(セル)の幅は、.
	// ポイント数で記述されている.
	// 拡大縮小率によって変更される.
	private int getWidthPixels(final double width, final double widthCoefficient) {
		return (int) Math.floor(pointsToPixels(width, widthCoefficient));
	}
	
	// 帳票レイアウト XML ファイルでの行(セル)の高さは、.
	// ポイント数で記述されている.
	// 拡大縮小率によって変更される.
	private int getHeightPixels(final double height, final double heightCoefficient) {
		return (int) Math.floor(pointsToPixels(height, heightCoefficient));
	}
}
