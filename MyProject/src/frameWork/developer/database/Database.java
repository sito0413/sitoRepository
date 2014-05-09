package frameWork.manager.database;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import frameWork.manager.ExcelUtil;
import frameWork.manager.SettingPanel;
import frameWork.manager.SrcUtil;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Database extends SettingPanel {
	private static String ROOT = "database";
	
	public static class Connector {
		private static String FILE_NAME = "/DBコネクター.xls";
		private static String SHEET_NAME = "DBコネクター";
		private static int ROW_INDEX = 2;
		
		private static void createFile() {
			new File(ROOT).mkdirs();
			final File file = new File(ROOT + FILE_NAME);
			if (!file.exists()) {
				try (FileOutputStream fo = new FileOutputStream(ROOT + FILE_NAME)) {
					final Workbook book = new HSSFWorkbook();
					final Sheet sheet = book.createSheet(SHEET_NAME);
					ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 8000, 8000, 8000);
					ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "コネクター名", "ユーザー", "パスワード", "URL", "ドライバ名");
					ExcelUtil.setTable(1, ROW_INDEX, 6, 3, book, sheet);
					book.write(fo);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
			else {
				getConnectors();
			}
		}
		
		public static List<Connector> getConnectors() {
			final List<Connector> c = new ArrayList<>();
			final Set<String> hashSet = new HashSet<>();
			int rowIndex = ROW_INDEX;
			final File file = new File(ROOT + FILE_NAME);
			if (file.exists()) {
				try (FileInputStream fi = new FileInputStream(ROOT + FILE_NAME)) {
					final Workbook book = new HSSFWorkbook(fi);
					final Sheet sheet = book.getSheet(SHEET_NAME);
					while (true) {
						try {
							final Row row = sheet.getRow(rowIndex);
							if (!row.getCell(2).getStringCellValue().isEmpty()
							        && hashSet.add(row.getCell(2).getStringCellValue())) {
								final Connector connector = new Connector(row.getCell(2).getStringCellValue(), row
								        .getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), row
								        .getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue());
								c.add(connector);
								Db.createFile(connector.name);
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
		
		private static void create() {
			new File("src/frameWork/database/connector").mkdirs();
			final List<Connector> connectors = getConnectors();
			try (final PrintWriter printWriter = new PrintWriter(new File(
			        "src/frameWork/database/connector/DatabaseConnector.java"))) {
				printWriter.println("package frameWork.database.connector;");
				printWriter.println("");
				printWriter.println("import java.util.Properties;");
				printWriter.println("import frameWork.database.connector.pool.ConnectorPool;");
				printWriter.println("");
				printWriter.println(SrcUtil.getComment(ROOT + FILE_NAME));
				printWriter.println("public enum DatabaseConnector {");
				printWriter.println("");
				for (final Connector connector : connectors) {
					printWriter.println(connector.toSrc());
				}
				
				printWriter.println("\t;");
				printWriter.println("\tprivate final ConnectorPool connectorPool;");
				printWriter.println("\tprivate final Properties properties;");
				printWriter.println("\tprivate final String url;");
				printWriter.println("\tprivate final String driverClassName;");
				printWriter
				        .println("\tprivate DatabaseConnector(final String username, final String password, final String url, final String driverClassName) {");
				printWriter.println("\t\tthis.url = url;");
				printWriter.println("\t\tthis.driverClassName = driverClassName;");
				printWriter.println("\t\tthis.properties = new Properties();");
				printWriter.println("\t\tif (username != null) {");
				printWriter.println("\t\t\tthis.properties.setProperty(\"user\", username);");
				printWriter.println("\t\t}");
				printWriter.println("\t\tif (password != null) {");
				printWriter.println("\t\t\tthis.properties.setProperty(\"password\", password);");
				printWriter.println("\t\t}");
				printWriter.println("");
				printWriter.println("\t\tconnectorPool = new ConnectorPool(this);");
				printWriter.println("\t}");
				printWriter.println("");
				printWriter.println("\tpublic synchronized DatabaseController getController() {");
				printWriter.println("\t\treturn connectorPool.getConnector();");
				printWriter.println("\t}");
				printWriter.println("");
				printWriter.println("\tpublic String getUrl() {");
				printWriter.println("\t\treturn url;");
				printWriter.println("\t}");
				printWriter.println("");
				printWriter.println("\tpublic Properties getProperties() {");
				printWriter.println("\t\treturn properties;");
				printWriter.println("\t}");
				printWriter.println("");
				printWriter.println("\tpublic String getDriverClassName() {");
				printWriter.println("\t\treturn driverClassName;");
				printWriter.println("\t}");
				printWriter.println("}");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		private final String name;
		private final String user;
		private final String pass;
		private final String url;
		private final String driver;
		
		public Connector(final String name, final String user, final String pass, final String url, final String driver) {
			this.name = name;
			this.user = user;
			this.pass = pass;
			this.url = url;
			this.driver = driver;
		}
		
		private String toSrc() {
			return "\t" + name + "(" + (user.isEmpty() ? "null" : ("\"" + user + "\"")) + ", "
			        + (pass.isEmpty() ? "null" : ("\"" + pass + "\"")) + ", " + ("\"" + url + "\"") + ", "
			        + ("\"" + driver + "\"") + "),";
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public boolean equals(final Connector obj) {
			return name.equals(obj.name);
		}
		
		public static void edit() {
			try {
				Desktop.getDesktop().edit(new File(ROOT + "/" + FILE_NAME));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Db {
		private static String FILE_NAME = "/データベース.xls";
		private static String SHEET_NAME = "データベース";
		private static int ROW_INDEX = 3;
		
		public static void createFile(final String connector) {
			new File(ROOT + "/" + connector).mkdirs();
			final File file = new File(ROOT + "/" + connector + FILE_NAME);
			if (!file.exists()) {
				try (FileOutputStream fo = new FileOutputStream(file)) {
					final Workbook book = new HSSFWorkbook();
					final Sheet sheet = book.createSheet(SHEET_NAME);
					ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000);
					ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "cn", connector);
					ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "データベース名", "日本語名");
					ExcelUtil.setTable(1, ROW_INDEX, 3, 5, book, sheet);
					book.write(fo);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
			else {
				getDatabase(connector);
			}
		}
		
		public void createClass(final String connector) {
			new File("src/frameWork/database/db/_" + name).mkdirs();
			final List<Table> tables = Table.getTable(connector, name);
			try (final PrintWriter printWriter = new PrintWriter(
			        new File("src/frameWork/database/db/" + name + ".java"))) {
				printWriter.println("package frameWork.database.db;");
				printWriter.println("");
				printWriter.println("import frameWork.database.connector.DatabaseConnector;");
				printWriter.println("import frameWork.database.Database;");
				for (final Table table : tables) {
					table.createClass(connector, name);
					printWriter.println("import frameWork.database.db._" + name + "." + table.name + ";");
				}
				printWriter.println("");
				printWriter.println(SrcUtil.getComment(ROOT + "/" + connector + FILE_NAME));
				printWriter.println("/**");
				printWriter.println(subName);
				printWriter.println(" */");
				printWriter.println("public final class " + name + " extends Database{");
				for (final Table table : tables) {
					printWriter.println("\t/**");
					printWriter.println(table.subName);
					printWriter.println("\t */");
					printWriter.println("\tpublic final " + table.name + " " + table.name + " = new " + table.name
					        + "();");
				}
				printWriter.println("\t@Override");
				printWriter.println("\tpublic DatabaseConnector getConnector(){");
				printWriter.println("\t\treturn DatabaseConnector." + connector + ";");
				printWriter.println("\t}");
				printWriter.println("}");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		private static List<Db> getDatabase(final String connector) {
			final List<Db> c = new ArrayList<>();
			final Set<String> hashSet = new HashSet<>();
			final File file = new File(ROOT + "/" + connector + FILE_NAME);
			if (file.exists()) {
				try (final FileInputStream fi = new FileInputStream(ROOT + "/" + connector + FILE_NAME)) {
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
								Table.createFile(connector, database.name);
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
		
		private final String name;
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
		
		public static void edit(final String connector) {
			try {
				Desktop.getDesktop().edit(new File(ROOT + "/" + connector + FILE_NAME));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Table {
		private static String FILE_NAME = "/テーブル.xls";
		private static String SHEET_NAME = "テーブル";
		private static int ROW_INDEX = 4;
		
		public static void createFile(final String connector, final String database) {
			new File(ROOT + "/" + connector + "/" + database).mkdirs();
			final File file = new File(ROOT + "/" + connector + "/" + database + FILE_NAME);
			if (!file.exists()) {
				try (FileOutputStream fo = new FileOutputStream(file)) {
					final Workbook book = new HSSFWorkbook();
					final Sheet sheet = book.createSheet(SHEET_NAME);
					ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000);
					ExcelUtil.setParameter(1, ROW_INDEX - 3, book, sheet, "cn", connector);
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
				getTable(connector, database);
			}
		}
		
		public void createClass(final String connector, final String database) {
			final List<Field> fields = Field.getField(connector, database, name);
			try (final PrintWriter printWriter = new PrintWriter(new File("src/frameWork/database/db/_" + database
			        + "/" + name + ".java"))) {
				printWriter.println("package frameWork.database.db._" + database + ";");
				printWriter.println("");
				printWriter.println("import frameWork.database.Table;");
				printWriter.println("import frameWork.database.Field;");
				printWriter.println("");
				printWriter.println(SrcUtil.getComment(ROOT + "/" + connector + "/" + database + FILE_NAME));
				printWriter.println("/**");
				printWriter.println(subName);
				printWriter.println(" */");
				printWriter.println("public class " + name + " extends Table{");
				for (final Field field : fields) {
					printWriter.println("\t/**");
					printWriter.println(field.subName);
					printWriter.println("\t */");
					printWriter.println("\tpublic final Field " + field.name + " = new Field(\"" + database + "."
					        + name + "." + field.name + "\");");
				}
				printWriter.println("}");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		private static List<Table> getTable(final String connector, final String database) {
			final List<Table> c = new ArrayList<>();
			final Set<String> hashSet = new HashSet<>();
			final File file = new File(ROOT + "/" + connector + "/" + database + FILE_NAME);
			if (file.exists()) {
				try (FileInputStream fi = new FileInputStream(ROOT + "/" + connector + "/" + database + FILE_NAME)) {
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
								Field.createFile(connector, database, table.name);
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
		
		private final String name;
		private final String subName;
		
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
		
		public static void edit(final String connector, final String database) {
			try {
				Desktop.getDesktop().edit(new File(ROOT + "/" + connector + "/" + database + FILE_NAME));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Field {
		private static String FILE_NAME = "/フィールド.xls";
		private static String SHEET_NAME = "フィールド";
		private static int ROW_INDEX = 5;
		
		public static void createFile(final String connector, final String database, final String table) {
			new File(ROOT + "/" + connector + "/" + database + "/" + table).mkdirs();
			final File file = new File(ROOT + "/" + connector + "/" + database + "/" + table + FILE_NAME);
			if (!file.exists()) {
				try (FileOutputStream fo = new FileOutputStream(file)) {
					final Workbook book = new HSSFWorkbook();
					final Sheet sheet = book.createSheet(SHEET_NAME);
					ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000);
					ExcelUtil.setParameter(1, ROW_INDEX - 4, book, sheet, "cn", connector);
					ExcelUtil.setParameter(1, ROW_INDEX - 3, book, sheet, "db", database);
					ExcelUtil.setParameter(1, ROW_INDEX - 2, book, sheet, "tb", table);
					ExcelUtil.setHeader(1, ROW_INDEX - 1, book, sheet, "No", "フィールド名", "日本語名");
					ExcelUtil.setTable(1, ROW_INDEX, 3, 20, book, sheet);
					book.write(fo);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
			else {
				getField(connector, database, table);
			}
		}
		
		private static List<Field> getField(final String connector, final String database, final String table) {
			final List<Field> c = new ArrayList<>();
			final Set<String> hashSet = new HashSet<>();
			final File file = new File(ROOT + "/" + connector + "/" + database + "/" + table + FILE_NAME);
			if (file.exists()) {
				try (FileInputStream fi = new FileInputStream(ROOT + "/" + connector + "/" + database + "/" + table
				        + FILE_NAME)) {
					final Workbook book = new HSSFWorkbook(fi);
					int rowIndex = ROW_INDEX;
					final Sheet sheet = book.getSheet(SHEET_NAME);
					while (true) {
						try {
							final Row row = sheet.getRow(rowIndex);
							if (!row.getCell(2).getStringCellValue().isEmpty()
							        && hashSet.add(row.getCell(2).getStringCellValue())) {
								final Field field = new Field(row.getCell(2).getStringCellValue(), row.getCell(3)
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
		
		private final String name;
		private final String subName;
		
		public Field(final String name, final String subName) {
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
		
		public static void edit(final String connector, final String database, final String table) {
			try {
				Desktop.getDesktop().edit(new File(ROOT + "/" + connector + "/" + database + "/" + table + FILE_NAME));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createFile() {
		Connector.createFile();
	}
	
	private JComboBox connectorComboBox;
	private JComboBox databaseComboBox;
	private JComboBox tableComboBox;
	
	public Database() {
		initialize();
	}
	
	private void initialize() {
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {
		        0, 0
		};
		gridBagLayout.rowHeights = new int[] {
		        0, 0, 0, 0, 0
		};
		gridBagLayout.columnWeights = new double[] {
		        1.0, Double.MIN_VALUE
		};
		gridBagLayout.rowWeights = new double[] {
		        0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE
		};
		setLayout(gridBagLayout);
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			final GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.VERTICAL;
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			add(panel, gbc_panel);
			{
				final JButton btnDb = new JButton("DBコネクター編集");
				btnDb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						Connector.edit();
					}
				});
				panel.add(btnDb);
			}
			final JButton btnNewButton_1 = new JButton("DBコネクター作成");
			panel.add(btnNewButton_1);
			{
				final JButton button = new JButton("Class作成");
				panel.add(button);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final List<Connector> connectors = Connector.getConnectors();
						try (final PrintWriter printWriter = new PrintWriter(new File(
						        "src/frameWork/database/DatabaseManager.java"))) {
							printWriter.println("package frameWork.database;");
							printWriter.println("");
							for (final Connector connector : connectors) {
								final List<Db> databases = Db.getDatabase(connector.name);
								for (final Db database : databases) {
									database.createClass(connector.name);
									printWriter.println("import frameWork.database.db." + database.name + ";");
								}
							}
							printWriter.println("");
							printWriter.println(SrcUtil.getComment(ROOT + "配下のファイル群"));
							printWriter.println("public interface DatabaseManager{");
							for (final Connector connector : connectors) {
								final List<Db> databases = Db.getDatabase(connector.name);
								for (final Db database : databases) {
									printWriter.println("\t/**");
									printWriter.println(database.subName);
									printWriter.println("\t */");
									printWriter.println("\t" + database.name + " " + database.name + " = new "
									        + database.name + "();");
								}
							}
							printWriter.println("}");
						}
						catch (final IOException exp) {
							exp.printStackTrace();
						}
						update();
					}
				});
			}
			btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					Connector.create();
					update();
				}
			});
		}
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			final GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.VERTICAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			add(panel, gbc_panel);
			{
				connectorComboBox = new JComboBox();
				this.connectorComboBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							final Connector c = (Connector) e.getItem();
							if (c == null) {
								databaseComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
								tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
							}
							else {
								databaseComboBox.setModel(new DefaultComboBoxModel(new Vector<>(Db.getDatabase(c.name))));
								tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
							}
						}
					}
				});
				panel.add(connectorComboBox);
			}
			{
				final JButton button_1 = new JButton("データベース編集");
				panel.add(button_1);
				button_1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final Connector c = (Connector) connectorComboBox.getSelectedItem();
						if (c != null) {
							Db.edit(c.name);
						}
					}
				});
			}
		}
		{
			final JPanel panel = new JPanel();
			final FlowLayout fl_panel = (FlowLayout) panel.getLayout();
			fl_panel.setAlignment(FlowLayout.LEFT);
			final GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.VERTICAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			add(panel, gbc_panel);
			{
				databaseComboBox = new JComboBox();
				this.databaseComboBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							final Connector c = (Connector) connectorComboBox.getSelectedItem();
							final Db d = (Db) e.getItem();
							if ((c == null) || (d == null)) {
								tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
							}
							else {
								tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>(Table.getTable(c.name,
								        d.name))));
							}
						}
					}
				});
				panel.add(databaseComboBox);
			}
			{
				final JButton button = new JButton("テーブル編集");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final Connector c = (Connector) connectorComboBox.getSelectedItem();
						if (c != null) {
							final Db d = (Db) databaseComboBox.getSelectedItem();
							if (d != null) {
								Table.edit(c.name, d.name);
							}
						}
					}
				});
				panel.add(button);
			}
		}
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			final GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.fill = GridBagConstraints.VERTICAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 3;
			add(panel, gbc_panel);
			{
				tableComboBox = new JComboBox();
				panel.add(tableComboBox);
			}
			{
				final JButton button = new JButton("フィールド編集");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final Connector c = (Connector) connectorComboBox.getSelectedItem();
						if (c != null) {
							final Db d = (Db) databaseComboBox.getSelectedItem();
							if (d != null) {
								final Table t = (Table) tableComboBox.getSelectedItem();
								if (t != null) {
									Field.edit(c.name, d.name, t.name);
								}
							}
						}
					}
				});
				panel.add(button);
			}
		}
	}
	
	@Override
	public void update() {
		connectorComboBox.setModel(new DefaultComboBoxModel(new Vector<>(Connector.getConnectors())));
		final Connector c = (Connector) connectorComboBox.getItemAt(0);
		if (c == null) {
			databaseComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
			tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
		}
		else {
			databaseComboBox.setModel(new DefaultComboBoxModel(new Vector<>(Db.getDatabase(c.name))));
			final Db d = (Db) databaseComboBox.getItemAt(0);
			if (d == null) {
				tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>()));
			}
			else {
				tableComboBox.setModel(new DefaultComboBoxModel(new Vector<>(Table.getTable(c.name, d.name))));
			}
		}
	}
	
	@Override
	public String getListName() {
		return "データベース";
	}
}
