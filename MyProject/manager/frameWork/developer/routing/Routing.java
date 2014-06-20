package frameWork.developer.routing;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import frameWork.SrcUtil;
import frameWork.architect.Literal;
import frameWork.base.ExcelUtil;
import frameWork.base.core.authority.Authority;
import frameWork.base.core.routing.Router;
import frameWork.base.core.routing.RoutingHandler;
import frameWork.base.core.state.State;
import frameWork.developer.Constant;
import frameWork.manager.tab.TabPanel;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Routing extends TabPanel {
	
	private List<String> __list = new ArrayList<>();
	private JList list_1;
	private JButton button;
	private JButton btnNewButton;
	private String systemID;
	
	public static void createFile() {
		final File file = Constant.getRoutingFile();
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(Constant.ROUTING_SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 15000);
				ExcelUtil.setHeader(1, 1, book, sheet, "No", "画面名", "URL", "備考");
				ExcelUtil.setTable(1, 2, 4, 10, book, sheet);
				book.write(fo);
				
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Routing() {
		initialize();
	}
	
	private void initialize() {
		{
			setLayout(new BorderLayout(10, 0));
		}
		{
			final JPanel panel = new JPanel();
			final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			add(panel, BorderLayout.NORTH);
			btnNewButton = new JButton("経路情報作成");
			this.btnNewButton.setIcon(new ImageIcon(Routing.class.getResource("/frameWork/architect/cog.png")));
			panel.add(btnNewButton);
			button = new JButton("経路情報編集");
			this.button.setIcon(new ImageIcon(Routing.class.getResource("/frameWork/architect/pencil.png")));
			panel.add(button);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					edit();
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
				
			});
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					create();
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
				
			});
		}
		{
			final JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			{
				list_1 = new JList();
				list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list_1.setSelectedIndex(0);
				scrollPane.setViewportView(list_1);
			}
		}
		{
			final JLabel label = new JLabel("");
			add(label, BorderLayout.WEST);
		}
	}
	
	public List<List<String>> getRouting() {
		final List<List<String>> roles = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		try (FileInputStream fi = new FileInputStream(Constant.getRoutingFile())) {
			final Workbook book = new HSSFWorkbook(fi);
			int rowIndex = 2;
			final Sheet sheet = book.getSheet(Constant.ROUTING_SHEET_NAME);
			while (true) {
				try {
					final Row row = sheet.getRow(rowIndex);
					if (!ExcelUtil.getStringCellValue(row.getCell(3)).isEmpty()
					        && hashSet.add(ExcelUtil.getStringCellValue(row.getCell(3)))) {
						final List<String> role = new ArrayList<>();
						role.add(ExcelUtil.getStringCellValue(row.getCell(2)));
						role.add(ExcelUtil.getStringCellValue(row.getCell(3)));
						roles.add(role);
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
		return roles;
	}
	
	private void edit() {
		try {
			Desktop.getDesktop().edit(Constant.getRoutingFile());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public void create() {
		final List<List<String>> roles = getRouting();
		final File file = new File(Literal.system + "/" + systemID + "/" + Literal.config, Literal.routing_xml);
		final Properties properties = new Properties();
		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.loadFromXML(fileInputStream);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		else {
			file.getParentFile().mkdirs();
		}
		for (final List<String> list : roles) {
			final String[] value = Router.getClassName(list.get(1), '/');
			if (value == null) {
				continue;
			}
			final String packageName = Literal.packageName + "/" + systemID + value[0];
			try {
				final File src = new File(Literal.src + "/" + packageName + "/" + value[1] + ".java");
				if (!src.exists()) {
					src.getParentFile().mkdirs();
					try (final PrintWriter printWriter = new PrintWriter(src)) {
						
						printWriter.println("package " + packageName.replace('/', '.') + ";");
						printWriter.println("");
						printWriter.println("import " + Authority.class.getCanonicalName() + ";");
						printWriter.println("import " + State.class.getCanonicalName() + ";");
						printWriter.println("import " + RoutingHandler.class.getCanonicalName() + ";");
						printWriter.println("");
						printWriter.println(SrcUtil.getComment());
						printWriter.println("@" + Authority.class.getSimpleName());
						printWriter.println("/** " + list.get(1) + " */ ");
						printWriter.println("public class " + value[1] + " implements "
						        + RoutingHandler.class.getSimpleName() + "{");
						printWriter.println("\t@" + Authority.class.getSimpleName());
						printWriter.println("\t@Override");
						printWriter.println("\tpublic void get(" + State.class.getSimpleName() + " "
						        + State.class.getSimpleName().toLowerCase() + "){");
						printWriter.println("\t\t//NOOP");
						printWriter.println("\t}");
						printWriter.println("\t@" + Authority.class.getSimpleName());
						printWriter.println("\t@Override");
						printWriter.println("\tpublic void post(" + State.class.getSimpleName() + " "
						        + State.class.getSimpleName().toLowerCase() + "){");
						printWriter.println("\t\t//NOOP");
						printWriter.println("\t}");
						printWriter.println("}");
						properties.setProperty("/" + systemID + value[0] + "/" + value[1],
						        "." + systemID + value[0].replace('/', '.') + "." + value[1]);
						
					}
				}
				final File view = new File(Literal.system + "/" + systemID + "/" + Literal.viewer + "/" + systemID
				        + value[0] + "/" + value[1] + ".jsp");
				
				if (!view.exists()) {
					view.getParentFile().mkdirs();
					try (final PrintWriter printWriter = new PrintWriter(view, "utf-8")) {
						printWriter
						        .println("<%@ page language=\"java\" contentType=\"text/html;charset=utf-8\" pageEncoding=\"utf-8\"%>");
						printWriter.println("<!DOCTYPE html>");
						printWriter.println("<html lang=\"ja\">");
						printWriter.println("\t<head>");
						printWriter.println("\t\t<meta charset=\"utf-8\">");
						printWriter.println("\t\t<title>タイトル</title>");
						printWriter.println("\t</head>");
						printWriter.println("\t<body>");
						printWriter.println("\t</body>");
						printWriter.println("</html>");
					}
				}
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			properties.storeToXML(fileOutputStream, "");
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		final File file = new File(Constant.InfoFile);
		if (file.exists()) {
			setEnabled(true);
			button.setEnabled(true);
			btnNewButton.setEnabled(true);
			
			try {
				try (FileInputStream fileInputStream1 = new FileInputStream(file)) {
					final Properties properties = new Properties();
					properties.loadFromXML(fileInputStream1);
					if ((properties.getProperty(Literal.SystemID) == null)
					        || properties.getProperty(Literal.SystemID).isEmpty()) {
						systemID = Literal.Temp;
					}
					else {
						systemID = properties.getProperty(Literal.SystemID);
					}
				}
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			
			__list = new ArrayList<>();
			for (final List<String> list : getRouting()) {
				final String[] value = Router.getClassName(list.get(1), '.');
				if (value == null) {
					continue;
				}
				
				__list.add(list.get(1) + " -> " + list.get(0) + " " + Literal.packageName + "." + systemID + value[0]
				        + "." + value[1]);
			}
			list_1.setModel(new AbstractListModel() {
				
				@Override
				public int getSize() {
					return __list.size();
				}
				
				@Override
				public Object getElementAt(final int index) {
					return __list.get(index);
				}
			});
		}
		else {
			setEnabled(false);
			button.setEnabled(false);
			btnNewButton.setEnabled(false);
		}
	}
	
	@Override
	public String getTabName() {
		return "URL定義";
	}
	
}