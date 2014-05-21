package frameWork.base.print;

import java.net.URI;

import frameWork.base.print.element.Page;
import frameWork.base.print.reader.Reader;
import frameWork.base.print.reader.xml.XMLReader;
import frameWork.base.print.writer.Writer;
import frameWork.base.print.writer.client.ClientWriter;

public class ReportManager {
	private final Reader reader;
	private final Writer writer;
	
	public ReportManager() {
		this.reader = new XMLReader();
		this.writer = new ClientWriter();
	}
	
	/**
	 * 帳票を出力します<br>
	 */
	public void print() throws ReportException {
		writer.print();
	}
	
	/**
	 * プレビューを表示します<br>
	 */
	public void preview() throws ReportException {
		writer.preview();
	}
	
	/**
	 * シートを読込し、Pageを作成します<br>
	 */
	public Page readSheet(final URI uri, final String sheetName) throws ReportException {
		final Page page = reader.readSheet(uri, sheetName);
		if (this.writer.add(page)) {
			return page;
		}
		return null;
	}
}