package frameWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
	public static class Field {
		public static Field create(final String string,
				final boolean isQuotationBlock, final char quotation) {
			final StringBuilder buffer = new StringBuilder();
			if (!string.isEmpty()) {
				final int loopMaxCount = string.length() - 1;
				boolean isQuotationBlockEscape = false;
				if (isQuotationBlock) {
					for (int i = 1; i < loopMaxCount; i++) {
						final char analyzedChar = string.charAt(i);
						if (analyzedChar == quotation) {
							isQuotationBlockEscape = !isQuotationBlockEscape;
							if (isQuotationBlockEscape) {
								buffer.append(analyzedChar);
							}
						} else {
							buffer.append(analyzedChar);
						}
					}
				} else {
					buffer.append(string);
				}
			}
			return new Field(buffer.toString());
		}

		private final String string;

		public Field(final String string) {
			this.string = string;
		}

		@Override
		public String toString() {
			return string;
		}
	}
	
	public static class Record extends ArrayList<Field> {
		public static Record create(final String lineString,
				final char quotation, final char separator) {
			final int loopMaxCount = lineString.length();
			final Record record = new Record();
			for (int i = 0; i < loopMaxCount; i++) {
				boolean isQuotationBlock = false;
				boolean isQuotationBlockEscape = false;
				final StringBuilder buffer = new StringBuilder();
				char analyzedChar = lineString.charAt(i);
				if (analyzedChar == quotation) {
					isQuotationBlock = true;
				} else if (analyzedChar == separator) {
					record.add(Field.create(buffer.toString(),
							isQuotationBlock, quotation));
					continue;
				}
				buffer.append(analyzedChar);
				for (i++; i < loopMaxCount; i++) {
					analyzedChar = lineString.charAt(i);
					if (analyzedChar == separator) {
						if (isQuotationBlock && !isQuotationBlockEscape) {
							buffer.append(analyzedChar);
						} else {
							break;
						}
					} else {
						buffer.append(analyzedChar);
						if (analyzedChar == quotation && isQuotationBlock) {
							isQuotationBlockEscape = !isQuotationBlockEscape;
						}
					}
				}
				record.add(Field.create(buffer.toString(),
						isQuotationBlock, quotation));
			}
			return record;
		}
	}

	public static class CSV extends ArrayList<Record> {

		public CharSequence toString(final Config config) {
			final StringBuilder builder = new StringBuilder();
			for (final Record record : this) {
				for (final Field field : record) {
					if (field.toString().contains(
							String.valueOf(config.getQuotation()))
							|| field.toString().contains(
									String.valueOf(config.getSeparator()))
							|| field.toString().contains(
									config.getLineSeparator())) {
						builder.append(config.getQuotation())
								.append(field.toString().replace(
										String.valueOf(config.getQuotation()),
										config.getEscape()))
								.append(config.getQuotation())
								.append(config.getSeparator());
					} else {
						builder.append(field.toString()).append(
								config.getSeparator());
					}
				}
				builder.append(config.getLineSeparator());
			}
			return builder;
		}

		public boolean readLine(final Config config, final String readLine,
				final StringBuilder buffer, final boolean isQuotationBlock) {
			boolean result = isQuotationBlock;
			int quotationCount = 0;
			for (int i = 0; i < readLine.length(); i++) {
				if (readLine.charAt(i) == config.getQuotation()) {
					quotationCount++;
				}
			}
			if (isQuotationBlock) {
				if ((quotationCount % 2) == 0) {
					buffer.append(readLine).append(config.getLineSeparator());
				} else {
					add(Record.create(buffer.append(readLine).toString(),
							config.getQuotation(), config.getSeparator()));
					buffer.delete(0, buffer.length());
					result = false;
				}
			} else {
				if ((quotationCount % 2) == 0) {
					add(Record.create(readLine, config.getQuotation(),
							config.getSeparator()));
				} else {
					buffer.append(readLine).append(config.getLineSeparator());
					result = true;
				}
			}
			return result;
		}

		public void read(final Config config, final BufferedReader reader)
				throws IOException {
			boolean isQuotationBlock = false;
			final StringBuilder buffer = new StringBuilder();
			// 最終行まで読み込む
			while (reader.ready()) {
				isQuotationBlock = readLine(config, reader.readLine(), buffer,
						isQuotationBlock);
			}
		}
	}

	public static class Config {
		private char separator;
		private char quotation;
		private String encoding;
		private String lineSeparator;
		private String escape;

		public Config() {
			this.quotation = '"';
			this.separator = ',';
			this.encoding = "MS932";
			this.lineSeparator = "\r\n";
			this.escape = "\"\"";
		}

		public char getSeparator() {
			return separator;
		}

		public synchronized void setSeparator(final char separator) {
			this.separator = separator;
		}

		public char getQuotation() {
			return quotation;
		}

		public synchronized void setQuotation(final char quotation) {
			this.quotation = quotation;
		}

		public String getEncoding() {
			return encoding;
		}

		public synchronized void setEncoding(final String encoding) {
			this.encoding = encoding;
		}

		public String getLineSeparator() {
			return lineSeparator;
		}

		public synchronized void setLineSeparator(final String lineSeparator) {
			this.lineSeparator = lineSeparator;
		}

		public String getEscape() {
			return escape;
		}

		public synchronized void setEscape(final String escape) {
			this.escape = escape;
		}

		@Override
		public synchronized Config clone() {
			final Config config = new Config();
			config.separator = separator;
			config.encoding = encoding;
			config.lineSeparator = lineSeparator;
			config.quotation = quotation;
			return config;
		}
	}

	/*- インスタンス -------------------------------------------------------------------------------------*/
	public CSVParser() {
		this.config = new Config();
	}

	/*- フィールド ---------------------------------------------------------------------------------------*/
	public final Config config;

	/*- 読込み -------------------------------------------------------------------------------------------*/
	public final void readCSV(final File csvFile, final CSV csv)
			throws IOException {
		if (csvFile != null && csv != null) {
			final Config conf = this.config.clone();
			try (BufferedReader r = new BufferedReader(new InputStreamReader(
					new FileInputStream(csvFile), conf.getEncoding()));) {
				csv.read(conf, r);
			}
		}
	}

	/*- 書込み -------------------------------------------------------------------------------------------*/
	public final void writeCSV(final File csvFile, final CSV csv)
			throws IOException {
		if (csvFile != null && csv != null) {
			final Config conf = this.config.clone();
			try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), conf.getEncoding()));) {
				w.append(csv.toString(conf));
			}
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private static CSV create() {
		return new CSV();
	}

	/*----------------------------------------------------------------------------------------------------*/

	public static void main(final String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, IOException {
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
