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

import frameWork.architect.ExcelUtil;
import frameWork.architect.Literal;
import frameWork.architect.SrcUtil;
import frameWork.base.database.DatabaseManager;

public class Db {
	private static final String databases = "databases";
	private static final String database = "database";
	private static final String create = "create";
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
		new File(Literal.src + "/" + DatabaseManager.class.getPackage().getName().replace(".", "/")).mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(new File(Literal.src + "/"
		        + DatabaseManager.class.getCanonicalName().replace(".", "/") + ".java"))) {
			printWriter.println("package " + DatabaseManager.class.getPackage().getName() + ";");
			printWriter.println("");
			
			final List<Db> dbs = Db.getDatabase();
			boolean flg = true;
			for (final Db db : dbs) {
				final List<Table> tables = Table.getTable(db.name);
				flg &= tables.isEmpty();
				for (final Table table : tables) {
					printWriter.println("import " + DatabaseManager.class.getCanonicalName() + "." + db.name + "."
					        + table.name + "." + table.name + frameWork.base.database.scheme.Row.class.getSimpleName()
					        + ";");
				}
			}
			
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(Database.ROOT + "配下のファイル群"));
			if (!flg) {
				printWriter.println("@SuppressWarnings(\"hiding\")");
			}
			printWriter.println("public final class " + DatabaseManager.class.getSimpleName() + "{");
			for (final Db db : dbs) {
				db.createClass(printWriter);
			}
			for (final Db db : dbs) {
				printWriter.println("\t/**");
				printWriter.println("\t * " + db.subName);
				printWriter.println("\t */");
				printWriter.println("\tpublic static final " + db.name + " " + db.name + " = new " + db.name + "();");
				printWriter.println("");
			}
			printWriter.println("\tpublic static final "
			        + frameWork.base.database.scheme.Database.class.getCanonicalName() + "[] " + databases + " = new "
			        + frameWork.base.database.scheme.Database.class.getCanonicalName() + "[]{");
			for (final Db db : dbs) {
				printWriter.println("\t\t" + db.name + ",");
			}
			printWriter.println("\t};");
			
			printWriter.println("\tpublic final void " + create + "() {");
			printWriter.println("\t\tfor (final " + frameWork.base.database.scheme.Database.class.getCanonicalName()
			        + " " + database + " : " + databases + ") {");
			printWriter.println("\t\t\t" + database + "." + create + "();");
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
							final Db db = new Db(row.getCell(2).getStringCellValue(), row.getCell(3)
							        .getStringCellValue());
							c.add(db);
							Table.createFile(db.name);
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