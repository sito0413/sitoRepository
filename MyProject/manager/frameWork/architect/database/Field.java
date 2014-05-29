package frameWork.architect.database;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import frameWork.ExcelUtil;
import frameWork.base.database.scheme.Type;
import frameWork.developer.Constant;

public class Field {
	private static int ROW_INDEX = 4;
	
	public static void createFile(final String database, final String table) {
		final File file = Constant.getFieldFile(database, table);
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(Constant.FIELD_SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 4000, 2000, 2000, 8000, 15000);
				ExcelUtil.setParameter(1, ROW_INDEX - 3, book, sheet, "db", database);
				ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "tb", table);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "フィールド名", "日本語名", "型", "UNIQUE", "NULL",
				        "DEFAULT", "備考");
				ExcelUtil.setTable(1, ROW_INDEX, 8, 50, book, sheet);
				ExcelUtil.setListInput(4, ROW_INDEX, sheet, Type.TEXT.toString(), Type.INTEGER.toString(),
				        Type.DOUBLE.toString());
				ExcelUtil.setListInput(5, ROW_INDEX, sheet, "○", "×");
				ExcelUtil.setListInput(6, ROW_INDEX, sheet, "○", "×");
				book.write(fo);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		else {
			getField(database, table);
		}
	}
	
	public void createClass(final String database, final String table, final PrintWriter printWriter) {
		final Type ty = Type.parse(type);
		printWriter.println("");
		printWriter.println("\t\t\t/**");
		printWriter.println("\t\t\t * " + subName);
		printWriter.println("\t\t\t */");
		printWriter.println("\t\t\tpublic static final class " + name + " extends "
		        + frameWork.base.database.scheme.Field.class.getCanonicalName() + "<" + ty.getType() + ">{");
		printWriter.println("\t\t\t\t" + name + "(){");
		printWriter.println("\t\t\t\t\tsuper(\"" + database + "\", \"" + table + "\", \"" + name + "\", false, "
		        + Type.class.getCanonicalName() + "." + ty + ", " + ("○".equals(isUnique)) + ", "
		        + (!"×".equals(isNullable)) + ", " + (defaultValue == null ? "null" : ("\"" + defaultValue + "\""))
		        + " );");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
	}
	
	public static List<Field> getField(final String database, final String table) {
		final List<Field> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = Constant.getFieldFile(database, table);
		if (file.exists()) {
			try (FileInputStream fi = new FileInputStream(file)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(Constant.FIELD_SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!ExcelUtil.getStringCellValue(row.getCell(2)).isEmpty()
						        && hashSet.add(ExcelUtil.getStringCellValue(row.getCell(2)))) {
							final Field field = new Field(ExcelUtil.getStringCellValue(row.getCell(2)),
							        ExcelUtil.getStringCellValue(row.getCell(3)), ExcelUtil.getStringCellValue(row
							                .getCell(4)), ExcelUtil.getStringCellValue(row.getCell(5)),
							        ExcelUtil.getStringCellValue(row.getCell(6)), ExcelUtil.getStringCellValue(row
							                .getCell(7)));
							c.add(field);
						}
						else {
							break;
						}
						rowIndex++;
					}
					catch (final NullPointerException e) {
						break;
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public final String name;
	final String subName;
	final String type;
	final String isUnique;
	final String isNullable;
	final String defaultValue;
	
	public Field(final String name, final String subName, final String type, final String isUnique,
	        final String isNullable, final String defaultValue) {
		this.name = name;
		this.subName = subName;
		this.type = type;
		this.isUnique = isUnique;
		this.isNullable = isNullable;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean equals(final Field obj) {
		return name.equals(obj.name);
	}
	
	public static void edit(final String database, final String table) {
		try {
			Desktop.getDesktop().edit(Constant.getFieldFile(database, table));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}