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

public class Table {
	private static final String id = "id";
	private static final String getFields = "getFields";
	private static final String createRow = "createRow";
	
	private static int ROW_INDEX = 3;
	
	public static void createFile(final String database) {
		final File file = Constant.getTableFile(database);
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(Constant.TABLE_SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 15000);
				ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "db", database);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "テーブル名", "日本語名", "備考");
				ExcelUtil.setTable(1, ROW_INDEX, 4, 10, book, sheet);
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
		printWriter.println("\t\tpublic static final class " + name + " extends "
		        + frameWork.base.database.scheme.Table.class.getCanonicalName() + "<" + name
		        + frameWork.base.database.scheme.Row.class.getSimpleName() + " >{");
		printWriter.println("\t\t\t" + name + "(){");
		printWriter.println("\t\t\t\tsuper(\"" + database + "\", \"" + name + "\");");
		printWriter.println("\t\t\t}");
		
		printWriter.println("");
		printWriter.println("\t\t\t/**");
		printWriter.println("\t\t\t * " + subName);
		printWriter.println("\t\t\t */");
		printWriter.println("\t\t\tpublic static final class id extends "
		        + frameWork.base.database.scheme.Field.class.getCanonicalName() + "<" + Type.INTEGER.getType() + ">{");
		printWriter.println("\t\t\t\tid(){");
		printWriter.println("\t\t\t\t\tsuper(\"" + database + "\", \"" + name + "\", \"id\", true, "
		        + Type.class.getCanonicalName() + "." + Type.INTEGER + ", false, true, \"0\");");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
		
		for (final Field field : fields) {
			field.createClass(database, name, printWriter);
		}
		
		printWriter.println("\t\t\tpublic static final class " + name
		        + frameWork.base.database.scheme.Row.class.getSimpleName() + " extends "
		        + frameWork.base.database.scheme.Row.class.getCanonicalName() + "{");
		printWriter.println("\t\t\t\t/**");
		printWriter.println("\t\t\t\t * " + id);
		printWriter.println("\t\t\t\t */");
		printWriter.println("\t\t\t\tpublic static final " + id + " " + id + " = new " + id + "();");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t/**");
			printWriter.println(field.subName);
			printWriter.println("\t\t\t\t */");
			printWriter.println("\t\t\t\tpublic static final " + field.name + " " + field.name + " = new " + field.name
			        + "();");
		}
		
		printWriter.println("\t\t\t\t@Override");
		printWriter.println("\t\t\t\tpublic final " + frameWork.base.database.scheme.Field.class.getCanonicalName()
		        + "<?>[] " + getFields + "(){");
		printWriter.println("\t\t\t\t\treturn new " + frameWork.base.database.scheme.Field.class.getCanonicalName()
		        + "<?>[]{");
		printWriter.println("\t\t\t\t\t\t" + id + ", ");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t\t\t" + field.name + ", ");
		}
		printWriter.println("\t\t\t\t\t};");
		printWriter.println("\t\t\t\t}");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t\t/**" + id + "*/");
		printWriter.println("\t\t\tpublic static final " + id + " " + id + " = new " + id + "();");
		for (final Field field : fields) {
			printWriter.println("\t\t\t/**");
			printWriter.println("\t\t\t *" + field.subName);
			printWriter.println("\t\t\t */");
			printWriter.println("\t\t\tpublic static final " + field.name + " " + field.name + " = new " + field.name
			        + "();");
		}
		
		printWriter.println("\t\t\t@Override");
		printWriter.println("\t\t\tpublic final " + frameWork.base.database.scheme.Field.class.getCanonicalName()
		        + "<?>[] " + getFields + "(){");
		printWriter.println("\t\t\t\treturn new " + frameWork.base.database.scheme.Field.class.getCanonicalName()
		        + "<?>[]{");
		printWriter.println("\t\t\t\t\t" + id + ", ");
		for (final Field field : fields) {
			printWriter.println("\t\t\t\t\t" + field.name + ", ");
		}
		printWriter.println("\t\t\t\t};");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t\t@Override");
		printWriter.println("\t\t\tpublic final " + name + frameWork.base.database.scheme.Row.class.getSimpleName()
		        + "  " + createRow + "(){");
		printWriter.println("\t\t\t\treturn new " + name + frameWork.base.database.scheme.Row.class.getSimpleName()
		        + "();");
		printWriter.println("\t\t\t}");
		
		printWriter.println("\t\t}");
	}
	
	public static List<Table> getTable(final String database) {
		final List<Table> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = Constant.getTableFile(database);
		if (file.exists()) {
			try (FileInputStream fi = new FileInputStream(file)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(Constant.TABLE_SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!ExcelUtil.getStringCellValue(row.getCell(2)).isEmpty()
						        && hashSet.add(ExcelUtil.getStringCellValue(row.getCell(2)))) {
							final Table table = new Table(ExcelUtil.getStringCellValue(row.getCell(2)),
							        ExcelUtil.getStringCellValue(row.getCell(3)));
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
	
	public final String name;
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
			Desktop.getDesktop().edit(Constant.getTableFile(database));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}