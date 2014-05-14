package frameWork.developer.authority;

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
import java.util.Set;

import javax.swing.AbstractListModel;
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

import frameWork.architect.ExcelUtil;
import frameWork.architect.Literal;
import frameWork.architect.SettingPanel;
import frameWork.architect.SrcUtil;
import frameWork.base.core.authority.Role;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Authority extends SettingPanel {
	private static final String FILE_NAME = Literal.authority + "/権限.xls";
	private static final String SHEET_NAME = "権限";
	private List<String> __list = new ArrayList<>();
	private JList list_1;
	
	public static void createFile() {
		new File(Literal.authority).mkdirs();
		final File file = new File(FILE_NAME);
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 15000);
				ExcelUtil.setHeader(1, 1, book, sheet, "No", "権限名", "ロール名", "備考");
				ExcelUtil.setTable(1, 2, 4, 10, book, sheet);
				book.write(fo);
				
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Authority() {
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
			final JButton btnNewButton_1 = new JButton("権限作成");
			panel.add(btnNewButton_1);
			final JButton button = new JButton("権限編集");
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
			btnNewButton_1.addActionListener(new ActionListener() {
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
	
	public List<List<String>> getRoles() {
		final List<List<String>> roles = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		try (FileInputStream fi = new FileInputStream(FILE_NAME)) {
			final Workbook book = new HSSFWorkbook(fi);
			int rowIndex = 2;
			final Sheet sheet = book.getSheet(SHEET_NAME);
			while (true) {
				try {
					final Row row = sheet.getRow(rowIndex);
					if (!row.getCell(3).getStringCellValue().isEmpty()
					        && hashSet.add(row.getCell(3).getStringCellValue())) {
						final List<String> role = new ArrayList<>();
						role.add(row.getCell(2).getStringCellValue());
						role.add(row.getCell(3).getStringCellValue());
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
			Desktop.getDesktop().edit(new File(FILE_NAME));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		final List<List<String>> roles = getRoles();
		createSrc(roles);
	}
	
	private void createSrc(final List<List<String>> roles) {
		new File(Literal.src + "/" + Role.class.getPackage().getName().replace(".", "/")).mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(new File(Literal.src + "/"
		        + Role.class.getCanonicalName().replace(".", "/") + ".java"))) {
			printWriter.println("package " + Role.class.getPackage().getName() + ";");
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(FILE_NAME));
			printWriter.println("public enum " + Role.class.getSimpleName() + " {");
			for (final List<String> list : roles) {
				printWriter.println("\t/**" + list.get(0) + "*/");
				printWriter.println("\t" + list.get(1) + ",");
			}
			printWriter.println("\t/**匿名ロール【システムデフォルト】*/");
			printWriter.println("\t" + Role.ANONYMOUS + ",");
			printWriter.println("\t/**ユーザーロール【システムデフォルト】*/");
			printWriter.println("\t" + Role.USER + ";");
			printWriter.println("}");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		__list = new ArrayList<>();
		for (final List<String> role : getRoles()) {
			__list.add(role.get(0) + " - " + role.get(1));
		}
		__list.add("匿名ロール【システムデフォルト】 - " + Role.ANONYMOUS);
		__list.add("ユーザーロール【システムデフォルト】 - " + Role.USER);
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
	
	@Override
	public String getListName() {
		return "権限作成";
	}
}
