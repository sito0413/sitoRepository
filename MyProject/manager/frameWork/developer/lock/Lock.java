package frameWork.developer.lock;

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
import frameWork.base.lock.LockKey;
import frameWork.developer.Constant;
import frameWork.manager.tab.TabPanel;

@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class Lock extends TabPanel {
	private List<String> __list = new ArrayList<>();
	private JList list_1;
	private JButton button;
	private JButton btnNewButton;
	
	public static void createFile() {
		final File file = Constant.getLockFile();
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			try (FileOutputStream fo = new FileOutputStream(file)) {
				final Workbook book = new HSSFWorkbook();
				final Sheet sheet = book.createSheet(Constant.LOCK_SHEET_NAME);
				ExcelUtil.setColumnWidth(sheet, 800, 1500, 8000, 8000, 15000);
				ExcelUtil.setHeader(1, 1, book, sheet, "No", "ロックキー", "備考");
				ExcelUtil.setTable(1, 2, 3, 10, book, sheet);
				book.write(fo);
				
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Lock() {
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
			btnNewButton = new JButton("ロック作成");
			this.btnNewButton.setIcon(new ImageIcon(Lock.class.getResource("/frameWork/architect/cog.png")));
			panel.add(btnNewButton);
			button = new JButton("ロック編集");
			this.button.setIcon(new ImageIcon(Lock.class.getResource("/frameWork/architect/pencil.png")));
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
	
	public List<String> getKey() {
		final List<String> keyList = new ArrayList<>();
		final Set<String> hashSet = new HashSet<>();
		try (FileInputStream fi = new FileInputStream(Constant.getLockFile())) {
			final Workbook book = new HSSFWorkbook(fi);
			int rowIndex = 2;
			final Sheet sheet = book.getSheet(Constant.LOCK_SHEET_NAME);
			while (true) {
				try {
					final Row row = sheet.getRow(rowIndex);
					if (!ExcelUtil.getStringCellValue(row.getCell(2)).isEmpty()
					        && hashSet.add(ExcelUtil.getStringCellValue(row.getCell(2)))) {
						keyList.add(ExcelUtil.getStringCellValue(row.getCell(2)));
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
		return keyList;
	}
	
	private void edit() {
		try {
			Desktop.getDesktop().edit(Constant.getLockFile());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public void create() {
		final List<String> keyList = getKey();
		final File file = new File(Literal.src + "/" + LockKey.class.getCanonicalName().replace(".", "/") + ".java");
		file.getParentFile().mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.println("package " + LockKey.class.getPackage().getName() + ";");
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(Constant.getLockFile().getPath()));
			printWriter.println("public enum " + LockKey.class.getSimpleName() + " {");
			for (final String key : keyList) {
				printWriter.println("\t" + key + ",");
			}
			printWriter.println("}");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		if (new File(Constant.InfoFile).exists()) {
			setEnabled(true);
			button.setEnabled(true);
			btnNewButton.setEnabled(true);
			__list = new ArrayList<>();
			for (final String key : getKey()) {
				__list.add(key);
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
		return "ロック作成";
	}
}
