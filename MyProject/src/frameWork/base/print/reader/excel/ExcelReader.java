package frameWork.base.print.reader.excel;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.BorderInfo;
import frameWork.base.print.element.HorizontalAlignment;
import frameWork.base.print.element.LineStyleType;
import frameWork.base.print.element.LineWeight;
import frameWork.base.print.element.Page;
import frameWork.base.print.element.PositionType;
import frameWork.base.print.element.VerticalAlignment;
import frameWork.base.print.reader.Reader;

/**
 * 帳票レイアウトXMLファイル読込<br>
 */
public class ExcelReader implements Reader {
	
	@Override
	public final synchronized Page readSheet(final URI uri, final String sheetName) throws ReportException {
		try (FileInputStream fi = new FileInputStream(new File(uri))) {
			final Workbook book = read(fi);
			final Sheet worksheet = book.getSheet(sheetName);
			if (worksheet == null) {
				throw new ReportException();
			}
			// セル配置.
			return createPage(worksheet);
		}
		catch (final Exception e) {
			throw new ReportException(e);
		}
	}
	
	private HSSFWorkbook read(final FileInputStream fi) throws ReportException {
		try {
			return new HSSFWorkbook(fi);
		}
		catch (final IOException e) {
			throw new ReportException("Excelの読み込み処理に失敗しました", e);
		}
	}
	
	// --------------------------------------------------
	
	// ページコンテンツを生成します<br>
	private Page createPage(final Sheet worksheet) {
		final Dimension pageSize = inquirePageSize(worksheet.getPrintSetup());
		final Page page = new Page((int) Math.ceil(inchesToPixels(worksheet.getMargin(Sheet.LeftMargin))),
		        (int) Math.ceil(inchesToPixels(worksheet.getMargin(Sheet.TopMargin))),
		        (int) Math.ceil(inchesToPixels(worksheet.getMargin(Sheet.RightMargin))),
		        (int) Math.ceil(inchesToPixels(worksheet.getMargin(Sheet.BottomMargin))), pageSize.width,
		        pageSize.height);
		final Map<String, CellRangeAddress> cellRangeAddressMap = new HashMap<>();
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			final CellRangeAddress range = worksheet.getMergedRegion(i);
			cellRangeAddressMap.put(range.getFirstRow() + "-" + range.getFirstColumn(), range);
		}
		
		// セルパネル配置原点.
		int top = 0;
		final int rowMax = worksheet.getLastRowNum();
		for (int rowIndex = 0; rowIndex <= rowMax; rowIndex++) {
			final Row row = worksheet.getRow(rowIndex);
			int rowHeight = pointsToPixelsHeight(worksheet.getDefaultRowHeight());
			if (row != null) {
				rowHeight = pointsToPixelsHeight(row.getHeight());
				int left = 0;
				final int colMax = row.getLastCellNum();
				for (int columnIndex = 0; columnIndex <= colMax; columnIndex++) {
					final int columnWidth = pointsToPixelsWidth(worksheet.getColumnWidth(columnIndex));
					final Cell cell = row.getCell(columnIndex);
					if (cell != null) {
						// セル情報あり.
						final CellRangeAddress address = cellRangeAddressMap.get(rowIndex + "-" + columnIndex);
						// セル幅算出.
						int width = columnWidth;
						if (address != null) {
							width = 0;
							for (int i = address.getFirstColumn(); i <= address.getLastColumn(); i++) {
								width += pointsToPixelsWidth(worksheet.getColumnWidth(i));
							}
						}
						// セル高さ算出.
						int height = rowHeight;
						if (address != null) {
							height = 0;
							for (int i = address.getFirstRow(); i <= address.getLastRow(); i++) {
								if (worksheet.getRow(i) != null) {
									height += pointsToPixelsHeight(worksheet.getRow(i).getHeight());
								}
								else {
									height += pointsToPixelsHeight(worksheet.getDefaultRowHeight());
								}
							}
						}
						// 属性取得 (セル → 行 → 列 → シート(標準) の順に検索).
						final CellStyle style = cell.getCellStyle();
						// セルパネル生成.
						final List<BorderInfo> borderInfos = new ArrayList<>();
						// 罫線追加.
						createBorderInfo(borderInfos, PositionType.TOP, style.getBorderTop());
						createBorderInfo(borderInfos, PositionType.LEFT, style.getBorderLeft());
						createBorderInfo(borderInfos, PositionType.RIGHT, style.getBorderRight());
						createBorderInfo(borderInfos, PositionType.BOTTOM, style.getBorderBottom());
						
						page.addCell(new frameWork.base.print.element.Cell(rowIndex, columnIndex, left, top,
						        columnWidth, rowHeight, width, height, createFont(worksheet.getWorkbook().getFontAt(
						                style.getFontIndex())), createHorizontalAlignment(style.getAlignment()),
						        createVerticalAlignment(style.getVerticalAlignment()), createCellValue(cell),
						        borderInfos, 1));
					}
					// レフト位置更新.
					left += columnWidth;
				}
			}
			// トップ位置更新.
			top += rowHeight;
		}
		return page;
	}
	
	private String createCellValue(final Cell cell) {
		switch ( cell.getCellType() ) {
			case Cell.CELL_TYPE_STRING :
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_NUMERIC :
				if (DateUtil.isCellDateFormatted(cell)) {
					return ("" + cell.getDateCellValue());
				}
				return ("" + cell.getNumericCellValue());
			case Cell.CELL_TYPE_BOOLEAN :
				return ("" + cell.getBooleanCellValue());
			case Cell.CELL_TYPE_FORMULA :
				return cell.getCellFormula();
			case Cell.CELL_TYPE_ERROR :
				return ("Error:" + cell.getErrorCellValue());
			case Cell.CELL_TYPE_BLANK :
				return "";
		}
		return null;
	}
	
	private java.awt.Font createFont(final Font fontAt) {
		int style = java.awt.Font.PLAIN;
		if (fontAt.getBoldweight() == Font.BOLDWEIGHT_BOLD) {
			style |= java.awt.Font.BOLD;
		}
		if (fontAt.getItalic()) {
			style |= java.awt.Font.ITALIC;
		}
		return new java.awt.Font(fontAt.getFontName(), style, pointsToPixels(fontAt.getFontHeight()));
	}
	
	private VerticalAlignment createVerticalAlignment(final short alignment) {
		switch ( alignment ) {
			case CellStyle.VERTICAL_CENTER :
			case CellStyle.VERTICAL_JUSTIFY :
				return VerticalAlignment.CENTER;
			case CellStyle.VERTICAL_TOP :
				return VerticalAlignment.TOP;
			case CellStyle.VERTICAL_BOTTOM :
				return VerticalAlignment.BOTTOM;
			default :
				return VerticalAlignment.CENTER;
		}
	}
	
	private HorizontalAlignment createHorizontalAlignment(final short alignment) {
		switch ( alignment ) {
			case CellStyle.ALIGN_CENTER :
			case CellStyle.ALIGN_CENTER_SELECTION :
			case CellStyle.ALIGN_JUSTIFY :
				return HorizontalAlignment.CENTER;
			case CellStyle.ALIGN_LEFT :
				return HorizontalAlignment.LEFT;
			case CellStyle.ALIGN_RIGHT :
				return HorizontalAlignment.RIGHT;
			case CellStyle.ALIGN_GENERAL :
			case CellStyle.ALIGN_FILL :
			default :
				return HorizontalAlignment.LEFT;
		}
	}
	
	private void createBorderInfo(final List<BorderInfo> borderInfos, final PositionType position, final short border) {
		switch ( border ) {
			case CellStyle.BORDER_NONE :
				break;
			case CellStyle.BORDER_THIN :
				borderInfos.add(new BorderInfo(position, LineStyleType.CONTINUOUS, LineWeight.THIN));
				break;
			case CellStyle.BORDER_MEDIUM :
				borderInfos.add(new BorderInfo(position, LineStyleType.CONTINUOUS, LineWeight.MEDIUM));
				break;
			case CellStyle.BORDER_DASHED :
				borderInfos.add(new BorderInfo(position, LineStyleType.CONTINUOUS, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_HAIR :
				borderInfos.add(new BorderInfo(position, LineStyleType.CONTINUOUS, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_THICK :
				borderInfos.add(new BorderInfo(position, LineStyleType.CONTINUOUS, LineWeight.THICK));
				break;
			case CellStyle.BORDER_DOUBLE :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOUBLE, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_DOTTED :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_MEDIUM_DASHED :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_DASH_DOT :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_MEDIUM_DASH_DOT :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_DASH_DOT_DOT :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_MEDIUM_DASH_DOT_DOT :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			case CellStyle.BORDER_SLANTED_DASH_DOT :
				borderInfos.add(new BorderInfo(position, LineStyleType.DOTTED, LineWeight.HAIRLINE));
				break;
			default :
				break;
		}
	}
	
	private Dimension inquirePageSize(final PrintSetup printSetup) {
		MediaSizeName mediaSizeName = MediaSizeName.ISO_A4;
		if (printSetup.getPaperSize() == PrintSetup.A4_PAPERSIZE) {
			mediaSizeName = MediaSizeName.ISO_A4;
		}
		if (printSetup.getPaperSize() == PrintSetup.A3_PAPERSIZE) {
			mediaSizeName = MediaSizeName.ISO_A3;
		}
		
		// 定数定義.
		final int shortEdgeIndex = 0;
		final int longEdgeIndex = 1;
		final int unit = Size2DSyntax.MM;
		// 用紙辺長さ取得.
		final MediaSize paperSize = MediaSize.getMediaSizeForName(mediaSizeName);
		final float[] edgeLength = paperSize.getSize(unit);
		final int shortEdge = (int) Math.floor(millimetersToPixels(edgeLength[shortEdgeIndex]));
		final int longEdge = (int) Math.floor(millimetersToPixels(edgeLength[longEdgeIndex]));
		// ページサイズ算出.
		int width;
		int height;
		if (printSetup.getLandscape()) {
			width = longEdge;
			height = shortEdge;
		}
		else {
			width = shortEdge;
			height = longEdge;
		}
		return new Dimension(width, height);
	}
	
	static double inchesToPixels(final double inches) {
		return inches * 72;
	}
	
	private int pointsToPixelsWidth(final int width) {
		return width / 40;
	}
	
	static int pointsToPixelsHeight(final int height) {
		return height / 20;
	}
	
	static int pointsToPixels(final int height) {
		return height / 20;
	}
	
	static double millimetersToPixels(final double millimeters) {
		return (millimeters * 72) / 25.4d;
	}
	
}
