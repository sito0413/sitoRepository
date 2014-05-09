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

import frameWork.developer.ExcelUtil;
import frameWork.developer.SrcUtil;

public class Db {
	private static String FILE_NAME = "/データベース.xls";
	private static String SHEET_NAME = "データベース";
	private static int ROW_INDEX = 2;
	
	public static void createFile() {
		new File(Database.ROOT).mkdirs();
		final File file = new File(Database.ROOT + FILE_NAME);
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "データベース名", "日本語名");
				ExcelUtil.setTable(1, ROW_INDEX, 3, 5, book, sheet);
				book.write(fo);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		else {
			getDatabase();
		}
	}
	
	public static void createJavaFile() {
		new File("src/frameWork/base/database").mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(new File(
		        "src/frameWork/base/database/DatabaseManager.java"))) {
			printWriter.println("package frameWork.base.database;");
			printWriter.println("");
			
			printWriter.println("import java.math.BigDecimal;");
			printWriter.println("import java.math.BigInteger;");
			final List<Db> databases = Db.getDatabase();
			for (final Db database : databases) {
				final List<Table> tables = Table.getTable(database.name);
				for (final Table table : tables) {
					printWriter.println("import frameWork.base.database.DatabaseManager." + database.name + "."
					        + table.name + "." + table.name + "Row;");
				}
			}
			printWriter.println("import frameWork.base.database.scheme.*;");
			
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(Database.ROOT + "配下のファイル群"));
			printWriter.println("@SuppressWarnings(\"hiding\")");
			printWriter.println("public final class DatabaseManager{");
			for (final Db database : databases) {
				database.createClass(printWriter);
			}
			for (final Db database : databases) {
				printWriter.println("\t/**");
				printWriter.println("\t * " + database.subName);
				printWriter.println("\t */");
				printWriter.println("\tpublic static final " + database.name + " " + database.name + " = new "
				        + database.name + "();");
				printWriter.println("");
			}
			printWriter.println("\tpublic static final Database[] databases = new Database[]{");
			for (final Db database : databases) {
				printWriter.println("\t\t" + database.name + ",");
			}
			printWriter.println("\t};");
			
			printWriter.println("\tpublic final void create() {");
			printWriter.println("\t\tfor (final Database database : databases) {");
			printWriter.println("\t\t\tdatabase.create();");
			printWriter.println("\t\t}");
			printWriter.println("\t}");
			
			printWriter.println("}");
		}
		catch (final IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void createClass(final PrintWriter printWriter) {
		final List<Table> tables = Table.getTable(name);
		printWriter.println("");
		printWriter.println("\t/**");
		printWriter.println("\t * " + subName);
		printWriter.println("\t */");
		printWriter.println("\tpublic static final class " + name + " extends Database{");
		printWriter.println("\t\t" + name + "(){");
		printWriter.println("\t\t\tsuper(\"" + name + "\");");
		printWriter.println("\t\t}");
		
		for (final Table table : tables) {
			table.createClass(name, printWriter);//@Override
		}
		for (final Table table : tables) {
			printWriter.println("\t\t/**");
			printWriter.println(table.subName);
			printWriter.println("\t\t */");
			printWriter.println("\t\tpublic static final " + table.name + " " + table.name + " = new " + table.name
			        + "();");
		}
		
		printWriter.println("\t\t@Override");
		printWriter.println("\t\tpublic final Table<?>[] getTables(){");
		printWriter.println("\t\t\treturn new Table<?>[]{");
		for (final Table table : tables) {
			printWriter.println("\t\t\t\t" + table.name + ",");
		}
		printWriter.println("\t\t\t};");
		printWriter.println("\t\t}");
		
		printWriter.println("\t}");
	}
	
	static List<Db> getDatabase() {
		final List<Db> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = new File(Database.ROOT + FILE_NAME);
		if (file.exists()) {
			try (final FileInputStream fi = new FileInputStream(Database.ROOT + FILE_NAME)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!row.getCell(2).getStringCellValue().isEmpty()
						        && hashSet.add(row.getCell(2).getStringCellValue())) {
							final Db database = new Db(row.getCell(2).getStringCellValue(), row.getCell(3)
							        .getStringCellValue());
							c.add(database);
							Table.createFile(database.name);
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
	private final String subName;
	
	public Db(final String name, final String subName) {
		this.name = name;
		this.subName = subName;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean equals(final Db obj) {
		return name.equals(obj.name);
	}
	
	public static void edit() {
		try {
			Desktop.getDesktop().edit(new File(Database.ROOT + FILE_NAME));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}