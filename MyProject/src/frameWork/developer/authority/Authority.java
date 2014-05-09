package frameWork.developer.authority;

import java.awt.Desktop;
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

import javax.swing.JButton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import frameWork.developer.ExcelUtil;
import frameWork.developer.SettingPanel;
import frameWork.developer.SrcUtil;

public class Authority extends SettingPanel {
	private static final String FILE_NAME = "authority/権限.xls";
	private static final String SHEET_NAME = "権限";
	
	public static void createFile() {
		new File("authority").mkdirs();
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
		setLayout(new FlowLayout(FlowLayout.LEFT));
		final JButton btnNewButton_1 = new JButton("権限作成");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				create();
			}
		});
		{
			final JButton button = new JButton("権限編集");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					edit();
				}
			});
			add(button);
		}
		add(btnNewButton_1);
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
		new File("src/frameWork/core/authority").mkdirs();
		try (final PrintWriter printWriter = new PrintWriter(new File("src/frameWork/core/authority/Role.java"))) {
			printWriter.println("package frameWork.core.authority;");
			printWriter.println("");
			printWriter.println(SrcUtil.getComment(FILE_NAME));
			printWriter.println("public enum Role {");
			for (final List<String> list : roles) {
				printWriter.println("\t/**" + list.get(0) + "*/");
				printWriter.println("\t" + list.get(1) + ",");
			}
			printWriter.println("\t/**匿名ロール【システムデフォルト】*/");
			printWriter.println("\tANONYMOUS,");
			printWriter.println("\t/**ユーザーロール【システムデフォルト】*/");
			printWriter.println("\tUSER;");
			printWriter.println("}");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getListName() {
		return "権限作成";
	}
}
