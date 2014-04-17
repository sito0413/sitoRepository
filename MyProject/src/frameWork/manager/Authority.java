package frameWork.manager;

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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import frameWork.manager.core.SettingPanel;

public class Authority extends SettingPanel {
	
	public static void createFile() {
		new File("authority").mkdirs();
		try (FileOutputStream fo = new FileOutputStream("authority\\権限.xls")) {
			final Workbook book = new HSSFWorkbook();
			book.createSheet("権限");
			final Sheet sheet = book.getSheet("権限");
			sheet.setColumnWidth(0, 800);
			sheet.setColumnWidth(1, 1500);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 8000);
			sheet.setColumnWidth(4, 15000);
			{
				final CellStyle style = book.createCellStyle();
				style.setBorderTop(CellStyle.BORDER_THIN);
				style.setBorderLeft(CellStyle.BORDER_THIN);
				style.setBorderRight(CellStyle.BORDER_THIN);
				style.setBorderBottom(CellStyle.BORDER_THIN);
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
				
				final Row row = sheet.createRow(1);
				final Cell cell1 = row.createCell(1);
				cell1.setCellValue("No");
				cell1.setCellStyle(style);
				final Cell cell2 = row.createCell(2);
				cell2.setCellValue("権限名");
				cell2.setCellStyle(style);
				final Cell cell3 = row.createCell(3);
				cell3.setCellValue("ロール名");
				cell3.setCellStyle(style);
				final Cell cell4 = row.createCell(4);
				cell4.setCellValue("備考");
				cell4.setCellStyle(style);
			}
			{
				final CellStyle style = book.createCellStyle();
				style.setBorderTop(CellStyle.BORDER_THIN);
				style.setBorderLeft(CellStyle.BORDER_THIN);
				style.setBorderRight(CellStyle.BORDER_THIN);
				style.setBorderBottom(CellStyle.BORDER_THIN);
				
				for (int index = 2; index < 12; index++) {
					final Row row1 = sheet.createRow(index);
					for (int i = 1; i <= 4; i++) {
						row1.createCell(i).setCellStyle(style);
					}
				}
			}
			book.write(fo);
			
		}
		catch (final IOException e) {
			e.printStackTrace();
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
		add(btnNewButton_1);
	}
	
	private void create() {
		new File("src/frameWork/core/authority").mkdirs();
		final Set<String> hashSet = new HashSet<>();
		final List<List<String>> roles = new ArrayList<>();
		try (FileInputStream fi = new FileInputStream("authority/権限.xls")) {
			final Workbook book = new HSSFWorkbook(fi);
			int rowIndex = 2;
			final Sheet sheet = book.getSheet("権限");
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
		try (final PrintWriter printWriter = new PrintWriter(new File("src/frameWork/core/authority/Role.java"))) {
			printWriter.println("package frameWork.core.authority;");
			printWriter.println("");
			printWriter.println("//このソースはauthority/権限.xlsから自動生成されました。原則このソースは修正しないでください");
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
