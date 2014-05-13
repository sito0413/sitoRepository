package framework2.base;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;

import framework2.base.report.Page;
import framework2.base.report.ReportManager;
import framework2.base.report.element.CellPanel;
import framework2.base.report.util.ReportException;


@SuppressWarnings("nls")
public class PrintTest {
	public static long time = 0l;
	public static long timeBuffer = 0l;

	public static void main(final String[] args) throws IOException, InstantiationException, IllegalAccessException,
	        ReportException, InterruptedException {
		System.out.println("\t " + Runtime.getRuntime().totalMemory() + " " + Runtime.getRuntime().freeMemory());
		time = System.currentTimeMillis();
		timeBuffer = System.currentTimeMillis();
		ReportManager reportManager = new ReportManager();
		printTime("reportManager");
		for (int i = 0; i < 5; i++) {
			Page report = reportManager.readSheet(new File("C:\\C007.xml").toURI(), "Sheet1");
			printTime("readSheet 1-" + i + " ");
			int maxRow = report.getCell("**kari_kubun").getIntMaxRow();
			for (int j = 0; j < maxRow; j++) {
				JPanel button = new JPanel() {
					@Override
					public void paintComponent(final Graphics g) {
						g.drawArc(0, 0, getWidth(), getHeight(), 0, 360);
					}
				};
				button.setSize(100, 100);
				button.setLocation(0, 0);
				report.getCenterNWPanel().add(button);
				int k = 0;
				CellPanel cellPanel = report.getCell("**kari_kubun", j);
				cellPanel.setText(i + "-" + j + "-" + k++);
				button.setSize(cellPanel.getWidth(), cellPanel.getHeight());
				button.setLocation(cellPanel.getX(), cellPanel.getY());
				report.getCell("**shiharai_kubun", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**shiharai_name", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kamoku_cd", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kamoku_name", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**busho_cd", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kouji_no", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**meisai_no", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**hinmei", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kikaku", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**suryo", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**tanni", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**tanka", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kingaku", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**zeiku", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kei_shohizei", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**gokei", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**shu_shohizei", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**go_shohizei", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**go_shiharai", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**seikyu_nen", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**kenpin_nen", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**tyumon_bi", j).setText(i + "-" + j + "-" + k++);
				report.getCell("**biko", j).setText(i + "-" + j + "-" + k++);
			}
			printTime("createSheet ");
			report = reportManager.readSheet(new File("C:\\P015.xml").toURI(), "Sheet1");
			JButton button = new JButton("test");
			button.setSize(100, 100);
			button.setLocation(0, 0);
			report.getCenterNWPanel().add(button);
			printTime("readSheet 2-" + i + " ");
			System.out.println("\n");
		}
		// reportManager.readSheet(new File("C:\\P0115.xml").toURI(), "Sheet1");
		printTime("帳票作成");
		reportManager.preview();
		printTime("終了");
		System.out.println("\t " + Runtime.getRuntime().totalMemory() + " " + Runtime.getRuntime().freeMemory());
		for (Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
			if (!entry.getKey().isDaemon()) {
				System.out.println("\t " + entry.getKey().getId() + "\t : \t" + entry.getKey().getName());
			}
		}
	}

	public static void printTime(final String str) {
		System.out.println(str + "\t " + (System.currentTimeMillis() - time) + " " + (System.currentTimeMillis() - timeBuffer)
		        + " " + Runtime.getRuntime().totalMemory() + " " + Runtime.getRuntime().freeMemory());
		timeBuffer = System.currentTimeMillis();

	}
}