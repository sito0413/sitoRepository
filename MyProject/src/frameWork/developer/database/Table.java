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

public class Table {
	private static String FILE_NAME = "/テーブル.xls";
	private static String SHEET_NAME = "テーブル";
	private static int ROW_INDEX = 3;
	
	public static void createFile(final String database) {
		new File(Database.ROOT + "/" + database).mkdirs();
		final File file = new File(Database.ROOT + "/" + database + FILE_NAME);
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000);
				ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "db", database);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "テーブル名", "日本語名");
				ExcelUtil.setTable(1, ROW_INDEX, 3, 10, book, sheet);
				book.write(fo);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		else {
			getTable(database);
		}
	}
	
	public void createClass(final String database, final PrintWriter printWriter) {
		final List<Field> fields = Field.getField(database, name);
		printWriter.println("");
		printWriter.println("\t\t/**");
		printWriter.println("\t\t * " + subName);
		printWriter.println("\t\t */");
		printWriter.println("\t\tpublic static final class " + name + " extends Table<" + name + "Row >{");
		printWriter.println("\t\t\t" + name + "(){");
		printWriter.println("\t\t\t\tsuper(\"" + database + "." + name + "\");");
		printWriter.println("\t\t\t}");
		
		printWriter.println("");
		printWriter.println("\t\t\t/**");
		printWriter.println("\t\t\t * " + subName);
		printWriter.println("\t\t\t */");
		printWriter.println("\t\t\tpublic static final class id extends Field<Integer>{");
		printWriter.println("\t\t\t\tid(){");
		printWriter.println("\t\t\t\t\tsuper(\"" + database + "." + name + ".id\", true, Type." + Type.INTEGER
		        + ", true, false, \"0\");");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
		
		for (final Field field : fields) {
			field.createClass(database, name, printWriter);
		}
		
		printWriter.println("\t\t\tpublic static final class " + name + "Row extends Row{");
		printWriter.println("\t\t\t\t/**");
		printWriter.println("\t\t\t\t * id");
		printWriter.println("\t\t\t\t */");
		printWriter.println("\t\t\t\tpublic static final id id = new id();");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t/**");
			printWriter.println(field.subName);
			printWriter.println("\t\t\t\t */");
			printWriter.println("\t\t\t\tpublic static final " + field.name + " " + field.name + " = new " + field.name
			        + "();");
		}
		
		printWriter.println("\t\t\t\t@Override");
		printWriter.println("\t\t\t\tpublic final Field<?>[] getFields(){");
		printWriter.println("\t\t\t\t\treturn new Field<?>[]{");
		printWriter.println("\t\t\t\t\t\tid,");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t\t\t" + field.name + ",");
		}
		printWriter.println("\t\t\t\t\t};");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t\t/**id*/");
		printWriter.println("\t\t\tpublic static final id id = new id();");
		for (final Field field : fields) {
			printWriter.println("\t\t\t/**");
			printWriter.println(field.subName);
			printWriter.println("\t\t\t */");
			printWriter.println("\t\t\tpublic static final " + field.name + " " + field.name + " = new " + field.name
			        + "();");
		}
		
		printWriter.println("\t\t\t@Override");
		printWriter.println("\t\t\tpublic final Field<?>[] getFields(){");
		printWriter.println("\t\t\t\treturn new Field<?>[]{");
		printWriter.println("\t\t\t\t\tid,");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t\t" + field.name + ",");
		}
		printWriter.println("\t\t\t\t};");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t\t@Override");
		printWriter.println("\t\t\tpublic final " + name + "Row  createRow(){");
		printWriter.println("\t\t\t\treturn new " + name + "Row ();");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t}");
	}
	
	static List<Table> getTable(final String database) {
		final List<Table> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = new File(Database.ROOT + "/" + database + FILE_NAME);
		if (file.exists()) {
			try (FileInputStream fi = new FileInputStream(Database.ROOT + "/" + database + FILE_NAME)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!row.getCell(2).getStringCellValue().isEmpty()
						        && hashSet.add(row.getCell(2).getStringCellValue())) {
							final Table table = new Table(row.getCell(2).getStringCellValue(), row.getCell(3)
							        .getStringCellValue());
							c.add(table);
							Field.createFile(database, table.name);
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
	
	public Table(final String name, final String subName) {
		this.name = name;
		this.subName = subName;
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean equals(final Table obj) {
		return name.equals(obj.name);
	}
	
	public static void edit(final String database) {
		try {
			Desktop.getDesktop().edit(new File(Database.ROOT + "/" + database + FILE_NAME));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}