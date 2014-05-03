package frameWork.manager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
	
	public static void setColumnWidth(final Sheet sheet, final int... width) {
		for (int i = 0; i < width.length; i++) {
			sheet.setColumnWidth(i, width[i]);
		}
	}
	
	public static void setHeader(final int columnIndex, final int rowIndex, final Workbook book, final Sheet sheet,
	        final String... headerStrings) {
		final CellStyle style = book.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		final Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < headerStrings.length; i++) {
			final Cell cell1 = row.createCell(columnIndex + i);
			cell1.setCellValue(headerStrings[i]);
			cell1.setCellStyle(style);
		}
	}
	
	public static void setTable(final int columnIndex, final int rowIndex, final int columnSize, final int rowSize,
	        final Workbook book, final Sheet sheet) {
		final CellStyle style = book.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		for (int index = rowIndex; index < (rowSize + rowIndex); index++) {
			final Row row1 = sheet.createRow(index);
			for (int i = columnIndex; i <= columnSize; i++) {
				row1.createCell(i).setCellStyle(style);
			}
		}
	}
	
	public static void setParameter(final int i, final int j, final Workbook book, final Sheet sheet,
	        final String string, final String connector) {
		final CellStyle style1 = book.createCellStyle();
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style1.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		final CellStyle style2 = book.createCellStyle();
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		
		final Row row = sheet.createRow(1);
		final Cell cell1 = row.createCell(1);
		cell1.setCellValue("cn");
		cell1.setCellStyle(style1);
		final Cell cell2 = row.createCell(2);
		cell2.setCellValue(connector);
		cell2.setCellStyle(style2);
	}
	
}
