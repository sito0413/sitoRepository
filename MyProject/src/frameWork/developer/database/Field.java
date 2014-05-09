package frameWork.developer.database;

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

import frameWork.base.database.scheme.Type;
import frameWork.developer.ExcelUtil;

public class Field {
	private static String FILE_NAME = "/フィールド.xls";
	private static String SHEET_NAME = "フィールド";
	private static int ROW_INDEX = 4;
	
	public static void createFile(final String database, final String table) {
		new File(Database.ROOT + "/" + database + "/" + table).mkdirs();
		final File file = new File(Database.ROOT + "/" + database + "/" + table + FILE_NAME);
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 4000, 2000, 2000, 8000);
				ExcelUtil.setParameter(1, ROW_INDEX - 3, book, sheet, "db", database);
				ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "tb", table);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "フィールド名", "日本語名", "型", "UNIQUE", "NULL",
				        "DEFAULT");
				ExcelUtil.setTable(1, ROW_INDEX, 7, 50, book, sheet);
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
		printWriter.println("\t\t\tpublic static final class " + name + " extends Field<" + ty.getType() + ">{");
		printWriter.println("\t\t\t\t" + name + "(){");
		printWriter.println("\t\t\t\t\tsuper(\"" + database + "." + table + "." + name + "\", false, Type." + ty + ", "
		        + ("○".equals(isUnique)) + ", " + (!"×".equals(isNullable)) + ", "
		        + (defaultValue == null ? "null" : ("\"" + defaultValue + "\"")) + " );");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
	}
	
	static List<Field> getField(final String database, final String table) {
		final List<Field> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = new File(Database.ROOT + "/" + database + "/" + table + FILE_NAME);
		if (file.exists()) {
			try (FileInputStream fi = new FileInputStream(Database.ROOT + "/" + database + "/" + table + FILE_NAME)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!row.getCell(2).getStringCellValue().isEmpty()
						        && hashSet.add(row.getCell(2).getStringCellValue())) {
							final Field field = new Field(row.getCell(2).getStringCellValue(), row.getCell(3)
							        .getStringCellValue(), row.getCell(4).getStringCellValue(), row.getCell(5)
							        .getStringCellValue(), row.getCell(6).getStringCellValue(), row.getCell(7)
							        .getStringCellValue());
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
	
	final String name;
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
	
	public boolean equals(final Db obj) {
		return name.equals(obj.name);
	}
	
	public static void edit(final String database, final String table) {
		try {
			Desktop.getDesktop().edit(new File(Database.ROOT + "/" + database + "/" + table + FILE_NAME));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
}