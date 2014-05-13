package framework2.base.report;

import java.net.URI;

import framework2.base.report.reader.XMLReportLayoutReader;
import framework2.base.report.util.ReportException;
import framework2.base.report.writer.PrintReportWriter;

public class ReportManager {
	private final XMLReportLayoutReader reader;
	private final PrintReportWriter writer;

	/**
	 * コンストラクタ<br>
	 * 
	 * @throws ParserFailureException
	 */
	public ReportManager() throws ReportException {
		this.reader = new XMLReportLayoutReader();
		this.writer = new PrintReportWriter();
	}

	// --------------------------------------------------

	/**
	 * 帳票を出力します<br>
	 * 
	 * @throws ReportException
	 */
	public void print() throws ReportException {
		print(null);
	}

	/**
	 * 帳票を出力します<br>
	 * 
	 * @throws ReportException
	 */
	public void print(final URI fileURI) throws ReportException {
		writer.writeProcess(fileURI);
	}

	// --------------------------------------------------

	/**
	 * プレビューを表示します<br>
	 * 
	 * @throws ReportException
	 */
	public void preview() throws ReportException {
		preview(null);
	}

	/**
	 * プレビューを表示します<br>
	 * 
	 * @throws ReportException
	 */
	public void preview(final URI fileURI) throws ReportException {
		writer.preview(fileURI);
	}

	// --------------------------------------------------

	/**
	 * シートを読込し、Pageを作成します<br>
	 * 
	 * @param sheetName
	 *            対象シート名<br>
	 * @return
	 * @throws ParserFailureException
	 * @throws ReportException
	 */
	public Page readSheet(final URI uri, final String sheetName) throws ReportException {
		// 印刷ページを追加.
		Page reportPage = reader.readSheet(uri, sheetName);
		writer.addPages(reportPage);
		return reportPage;
	}

	// --------------------------------------------------

}
