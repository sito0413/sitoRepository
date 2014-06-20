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

import frameWork.SrcUtil;
import frameWork.architect.Literal;
import frameWork.base.ExcelUtil;
import frameWork.base.database.DatabaseManager;
import frameWork.developer.Constant;

public class Database {
	private static final String str_databases = "databases";
	private static final String str_database = "database";
	private static final String create = "create";
	private static int ROW_INDEX = 2;
	
	public static void createFile() {
		final File file = Constant.getDatabaseFile();
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(Constant.DATABASE_SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 15000);
				ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "データベース名", "日本語名", "備考");
				ExcelUtil.setTable(1, ROW_INDEX, 4, 5, book, sheet);
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
		final File file = new File(Literal.src + "/" + DatabaseManager.class.getCanonicalName().replace(".", "/")
		        + ".java");
		file.getParentFile().mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.println("package " + DatabaseManager.class.getPackage().getName() + ";");
			printWriter.println("");
			
			final List<Database> databases = Database.getDatabase();
			boolean flg = true;
			for (final Database database : databases) {
				final List<Table> tables = Table.getTable(database.name);
				flg &= tables.isEmpty();
				for (final Table table : tables) {
					printWriter.println("import " + DatabaseManager.class.getCanonicalName() + "." + database.name
					        + "." + table.name + "." + table.name
					        + frameWork.base.database.scheme.Row.class.getSimpleName() + ";");
				}
			}
			
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(Literal.database + "配下のファイル群"));
			if (!flg) {
				printWriter.println("@SuppressWarnings(\"hiding\")");
			}
			printWriter.println("public final class " + DatabaseManager.class.getSimpleName() + "{");
			for (final Database database : databases) {
				database.createClass(printWriter);
			}
			for (final Database database : databases) {
				printWriter.println("\t/**");
				printWriter.println("\t * " + database.subName);
				printWriter.println("\t */");
				printWriter.println("\tpublic static final " + database.name + " " + database.name + " = new "
				        + database.name + "();");
				printWriter.println("");
			}
			printWriter.println("\tpublic static final "
			        + frameWork.base.database.scheme.Database.class.getCanonicalName() + "[] " + str_databases
			        + " = new " + frameWork.base.database.scheme.Database.class.getCanonicalName() + "[]{");
			for (final Database database : databases) {
				printWriter.println("\t\t" + database.name + ",");
			}
			printWriter.println("\t};");
			
			printWriter.println("\tpublic final void " + create + "() {");
			printWriter.println("\t\tfor (final " + frameWork.base.database.scheme.Database.class.getCanonicalName()
			        + " " + str_database + " : " + str_databases + ") {");
			printWriter.println("\t\t\t" + str_database + "." + create + "();");
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
		printWriter.println("\tpublic static final class " + name + " extends "
		        + frameWork.base.database.scheme.Database.class.getCanonicalName() + "{");
		printWriter.println("\t\t" + name + "(){");
		printWriter.println("\t\t\tsuper(\"" + name + "\");");
		printWriter.println("\t\t}");
		
		for (final Table table : tables) {
			table.createClass(name, printWriter);
		}
		for (final Table table : tables) {
			printWriter.println("\t\t/**");
			printWriter.println("\t\t *" + table.subName);
			printWriter.println("\t\t */");
			printWriter.println("\t\tpublic final " + table.name + " " + table.name + " = new " + table.name + "();");
		}
		
		printWriter.println("\t\t@Override");
		printWriter.println("\t\tpublic final " + frameWork.base.database.scheme.Table.class.getCanonicalName()
		        + "<?>[] getTables(){");
		printWriter.println("\t\t\treturn new " + frameWork.base.database.scheme.Table.class.getCanonicalName()
		        + "<?>[]{");
		for (final Table table : tables) {
			printWriter.println("\t\t\t\t" + table.name + ",");
		}
		printWriter.println("\t\t\t};");
		printWriter.println("\t\t}");
		
		printWriter.println("\t}");
	}
	
	public static List<Database> getDatabase() {
		final List<Database> c = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		final File file = Constant.getDatabaseFile();
		if (file.exists()) {
			try (final FileInputStream fi = new FileInputStream(file)) {
				final Workbook book = new HSSFWorkbook(fi);
				int rowIndex = ROW_INDEX;
				final Sheet sheet = book.getSheet(Constant.DATABASE_SHEET_NAME);
				while (true) {
					try {
						final Row row = sheet.getRow(rowIndex);
						if (!ExcelUtil.getStringCellValue(row.getCell(2)).isEmpty()
						        && hashSet.add(ExcelUtil.getStringCellValue(row.getCell(2)))) {
							final Database database = new Database(ExcelUtil.getStringCellValue(row.getCell(2)),
							        ExcelUtil.getStringCellValue(row.getCell(3)));
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
	
	public final String name;
	private final String subName;
	
	public Database(final String name, final String subName) {
		this.name = name;
		this.subName = subName;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean equals(final Database obj) {
		return name.equals(obj.name);
	}
	
	public static void edit() {
		try {
			Desktop.getDesktop().edit(Constant.getDatabaseFile());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}