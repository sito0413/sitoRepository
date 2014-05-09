package frameWork.base.util.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * CSVファイルの読書クラス
 * 
 * @author 瀬谷
 * @version 2.0.0
 * @since 2012/04/20
 */
@SuppressWarnings("nls")
public class CSVParser {
	
	/*- インスタンス -------------------------------------------------------------------------------------*/
	public CSVParser() {
		this.config = new Config();
	}
	
	/*- フィールド ---------------------------------------------------------------------------------------*/
	public final Config config;
	
	/*- 読込み -------------------------------------------------------------------------------------------*/
	public final void readCSV(final File csvFile, final CSV csv) throws IOException {
		if ((csvFile != null) && (csv != null)) {
			final Config conf = this.config.clone();
			try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),
			        conf.getEncoding()));) {
				csv.read(conf, r);
			}
		}
	}
	
	/*- 書込み -------------------------------------------------------------------------------------------*/
	public final void writeCSV(final File csvFile, final CSV csv) throws IOException {
		if ((csvFile != null) && (csv != null)) {
			final Config conf = this.config.clone();
			try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile),
			        conf.getEncoding()));) {
				w.append(csv.toString(conf));
			}
		}
	}
	
	/*----------------------------------------------------------------------------------------------------*/
	private static CSV create() {
		return new CSV();
	}
	
	/*----------------------------------------------------------------------------------------------------*/
	
	public static void main(final String[] args) throws ClassNotFoundException, InstantiationException,
	        IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final JFileChooser chooser1 = new JFileChooser();
		final CSVParser fileParser = new CSVParser();
		if (chooser1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final long s1 = System.nanoTime();
			final CSV csv = CSVParser.create();
			fileParser.readCSV(chooser1.getSelectedFile(), csv);
			final long f1 = System.nanoTime();
			System.out.println("read : " + (f1 - s1));
			final JFileChooser chooser2 = new JFileChooser();
			if (chooser2.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				final long s2 = System.nanoTime();
				fileParser.writeCSV(chooser2.getSelectedFile(), csv);
				final long f2 = System.nanoTime();
				System.out.println("write : " + (f2 - s2));
			}
		}
		System.out.println("end");
	}
	
}
