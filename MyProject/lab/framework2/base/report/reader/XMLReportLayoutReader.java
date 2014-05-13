package framework2.base.report.reader;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import framework2.base.report.Page;
import framework2.base.report.ReportP;
import framework2.base.report.element.BorderLabel;
import framework2.base.report.element.CellPanel;
import framework2.base.report.element.CopyOfReport;
import framework2.base.report.util.ExceptionType;
import framework2.base.report.util.ReportException;
import framework2.base.report.util.UnitConversion;
import framework2.base.report.util.XMLConstants;

/**
 * 帳票レイアウトXMLファイル読込<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/20
 */
@SuppressWarnings("nls")
public class XMLReportLayoutReader {

	/** エンコードマップ */
	private static final Map<Character, String> encodingMap;
	static {
		encodingMap = new ConcurrentHashMap<Character, String>();
		encodingMap.put('\n', "<br>"); // 復帰コード.
		encodingMap.put('\r', ""); // 改行コード.
		encodingMap.put('<', "&lt;"); // 不等号(より小).
		encodingMap.put('>', "&gt;"); // 不等号(より大).
		encodingMap.put('&', "&amp;"); // 復帰コード.
		encodingMap.put('\"', "&quot;"); // 二重引用符.
	}

	/** 列(セル)幅算出係数 */
	private static final double WIDTH_COEFFICIENT = 1.0d;
	/** 行(セル)高さ算出係数 */
	private static final double HEIGHT_COEFFICIENT = 0.9d;

	// ------------------------------------------------------
	/**
	 * コンストラクタ<br>
	 */
	public XMLReportLayoutReader() {
		super();
	}

	public XMLBook read(final URI url) throws ReportException {
		Document document = null;
		try {
			// XMLファイル読込.
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(url));
		}
		catch (ParserConfigurationException exp) {
			throw new ReportException("XMLの読み込み処理に失敗しました", exp);
		}
		catch (FileNotFoundException exp) {
			throw new ReportException("XMLの読み込み処理に失敗しました", exp);
		}
		catch (SAXException exp) {
			throw new ReportException("XMLの読み込み処理に失敗しました", exp);
		}
		catch (IOException exp) {
			throw new ReportException("XMLの読み込み処理に失敗しました", exp);
		}
		// ワークブックオブジェクト生成.
		XMLBook book = new XMLBook(document);
		return book;
	}

	// ------------------------------------------------------

	public final synchronized Page readSheet(final URI uri, final String sheetName) throws ReportException {
		if (sheetName == null) {
			throw new ReportException(ExceptionType.NULL_ARGUMENT);
		}
		ReportP templatePage = createTemplatePage(uri, sheetName);
		// テンプレートから印刷ページを生成.
		return new CopyOfReport(templatePage);
	}

	// --------------------------------------------------

	/**
	 * ReportObject を生成します<br>
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws ReportException
	 */
	private ReportP createTemplatePage(final URI uri, final String currentSheetName2) throws ReportException {
		final XMLBook book = read(uri);
		final XMLSheet worksheet = book.getWorksheet(currentSheetName2);
		final Map<String, XMLStyle> styles = book.getStylesMap();
		if (worksheet == null) {
			throw new ReportException(ExceptionType.NULL_ARGUMENT);
		}
		// スケールの保持.
		double scaleRate = worksheet.getScale() / 100d;
		double heightCoefficient = HEIGHT_COEFFICIENT * scaleRate;
		double widthCoefficient = WIDTH_COEFFICIENT * scaleRate;
		// ページのベースを作成.
		Dimension pageSize = inquirePageSize(worksheet);
		ReportP newPagePanel = new ReportP(pageSize.width, pageSize.height);
		newPagePanel.setPreferredSize(new Dimension(pageSize));
		newPagePanel.setSize(new Dimension(pageSize));
		// 余白設定.
		newPagePanel.setTopMargin(getMarginPixels(worksheet.getTopMargin(), scaleRate));
		newPagePanel.setLeftMargin(getMarginPixels(worksheet.getLeftMargin(), scaleRate));
		newPagePanel.setRightMargin(getMarginPixels(worksheet.getRightMargin(), scaleRate));
		newPagePanel.setBottomMargin(getMarginPixels(worksheet.getBottomMargin(), scaleRate));

		// 印刷可能列確認.
		Map<Integer, XMLColumn> printableColumns = inquirePrintableColumns(worksheet, newPagePanel, widthCoefficient);
		// 印刷可能行確認.
		Map<Integer, XMLRow> printableRows = inquirePrintableRows(worksheet, newPagePanel, heightCoefficient);
		// セル配置.
		createPageCell(newPagePanel, worksheet, printableColumns, printableRows, styles, scaleRate, heightCoefficient,
		        widthCoefficient);

		return newPagePanel;
	}

	// --------------------------------------------------

	/**
	 * ページサイズ確認<br>
	 * 
	 * @param options
	 * @return
	 */
	private Dimension inquirePageSize(final XMLSheet worksheets) {
		// 定数定義.
		final int shortEdgeIndex = 0;
		final int longEdgeIndex = 1;
		final int unit = Size2DSyntax.MM;
		// 用紙辺長さ取得.
		MediaSize paperSize = MediaSize.getMediaSizeForName(worksheets.getPaperSize().toMediaSizename());
		float[] edgeLength = paperSize.getSize(unit);
		int shortEdge = getPaperEdgePixels(edgeLength[shortEdgeIndex], unit);
		int longEdge = getPaperEdgePixels(edgeLength[longEdgeIndex], unit);
		// ページサイズ算出.
		int width;
		int height;
		if (OrientationRequested.LANDSCAPE.equals(worksheets.getOrientation())) {
			width = longEdge;
			height = shortEdge;
		}
		else {
			width = shortEdge;
			height = longEdge;
		}
		//
		return new Dimension(width, height);
	}

	/**
	 * 用紙の辺の長さをピクセル数で取得します<br>
	 * 
	 * @param length
	 *            用紙の辺の長さ<br>
	 * @param unit
	 *            長さの単位<br>
	 *            (Size2DSyntax.MM or Size2DSyntax.INCH)<br>
	 * @return
	 */
	private static int getPaperEdgePixels(final double length, final int unit) {
		// 用紙サイズは拡大縮小率の影響を受けない.
		return (int) Math.floor(unit == Size2DSyntax.MM ? UnitConversion.millimetersToPixels(length) : UnitConversion
		        .inchesToPixels(length));

	}

	/**
	 * 用紙のマージンをピクセル数で取得します<br>
	 * 
	 * @param length
	 *            マージンの長さ (インチ指定)<br>
	 * @return
	 */
	private int getMarginPixels(final double length, final double scaleRate) {
		// 帳票レイアウト XML ファイルでのマージンは、.
		// インチ単位で記述されている.
		// 拡大縮小率によって変更される.
		return (int) Math.ceil(UnitConversion.inchesToPixels(length, scaleRate));
	}

	// --------------------------------------------------

	/**
	 * 印刷可能列確認<br>
	 * 
	 * @param worksheet
	 * @param pagePanel
	 * @return
	 */
	private Map<Integer, XMLColumn> inquirePrintableColumns(final XMLSheet worksheet, final ReportP pagePanel,
	        final double widthCoefficient) {
		Map<Integer, XMLColumn> newColumnsMap = new ConcurrentSkipListMap<Integer, XMLColumn>();
		int index = 0;
		int totalWidth = 0;
		for (XMLColumn column : worksheet.getTable().getColumns()) {
			// 列幅算出.
			int width = getWidthPixels(column.getWidth(), widthCoefficient);
			// 繰り返し列処理.
			for (int i = -1; i < column.getSpan(); i++) {
				// 列インデックス更新.
				index++;
				// 列情報追加.
				newColumnsMap.put(index, column);
				// 幅確認.
				totalWidth += width;
				if (totalWidth >= pagePanel.getWidth()) {
					break;
				}
			}
			//
			if (totalWidth >= pagePanel.getWidth()) {
				break;
			}
		}
		//
		return newColumnsMap;
	}

	/**
	 * 印刷可能行確認<br>
	 * 
	 * @param worksheet
	 * @param pagePanel
	 * @return
	 */
	private Map<Integer, XMLRow> inquirePrintableRows(final XMLSheet worksheet, final ReportP pagePanel,
	        final double heightCoefficient) {
		Map<Integer, XMLRow> newRowsMap = new ConcurrentSkipListMap<Integer, XMLRow>();
		int index = 0;
		int totalHeight = 0;
		for (XMLRow row : worksheet.getTable().getRows()) {
			// 行高さ算出.
			int height = getHeightPixels(row.getHeight(), heightCoefficient);
			// 繰り返し処理.
			for (int i = -1; i < row.getSpan(); i++) {
				// 行インデックス更新.
				index++;
				// 行情報追加.
				newRowsMap.put(index, row);
				// 高さ確認.
				totalHeight += height;
				if (totalHeight >= pagePanel.getHeight()) {
					break;
				}
			}
			//
			if (totalHeight >= pagePanel.getHeight()) {
				break;
			}
		}
		//
		return newRowsMap;
	}

	// --------------------------------------------------

	/**
	 * ページコンテンツを生成します<br>
	 * 
	 * @param report
	 * @param worksheet
	 * @param printableColumns
	 * @param printableRows
	 * @param styles
	 */
	private void createPageCell(final ReportP report, final XMLSheet worksheet, final Map<Integer, XMLColumn> printableColumns,
	        final Map<Integer, XMLRow> printableRows, final Map<String, XMLStyle> styles, final double scaleRate,
	        final double heightCoefficient, final double widthCoefficient) {
		// セルパネル配置原点.
		// ※ 上と左の罫線が描画範囲からはみ出さないように.
		// 1 pixel オフセットする.
		// セルパネル生成.
		int top = 1;// TODO オフセット
		List<Future<CellPanel>> list = new CopyOnWriteArrayList<Future<CellPanel>>();
		int rowMax = printableRows.size();
		int colMax = printableColumns.size();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int rowIdx = 1; rowIdx <= rowMax; rowIdx++) {
			final XMLRow row = printableRows.get(rowIdx);
			final int rowHeight = getHeightPixels(row.getHeight(), heightCoefficient);
			int left = 1;// TODO オフセット
			final int rowIndex = rowIdx;
			final int topBuffer = top;

			for (int colIdx = 1; colIdx <= colMax; colIdx++) {
				final int colIndex = colIdx;
				final int leftBuffer = left;
				final XMLColumn column = printableColumns.get(colIdx);
				final int columnWidth = getWidthPixels(column.getWidth(), widthCoefficient);
				Future<CellPanel> worker = executorService.submit(new Callable<CellPanel>() {
					public CellPanel call() throws Exception {
						XMLCell cell = row.getCell(colIndex);
						if (cell != null) {
							// セル情報あり.
							// セル幅算出.
							int width = columnWidth;
							for (int i = 1; i <= cell.getMergeAccross(); i++) {
								if (colIndex + i > printableColumns.size()) {
									break;
								}
								width += getWidthPixels(printableColumns.get(colIndex + i).getWidth(), widthCoefficient);
							}
							// セル高さ算出.
							int height = rowHeight;
							for (int i = 1; i <= cell.getMergeDown(); i++) {
								if (rowIndex + i > printableRows.size()) {
									break;
								}
								height += getHeightPixels(printableRows.get(rowIndex + i).getHeight(), heightCoefficient);
							}
							// 属性取得 (セル → 行 → 列 → シート(標準) の順に検索).
							XMLStyle style = styles.get(cell.getStyleId());
							if (style == null) {
								style = styles.get(row.getStyleId());
								if (style == null) {
									style = styles.get(column.getStyleId());
									if (style == null) {

										style = styles.get(XMLConstants.DEFAULTSTYLE_ID);
									}
								}
							}
							return createCellPanel(rowIndex, colIndex, leftBuffer, topBuffer, width, height, cell, style,
							        scaleRate);
						}
						return null;
					}
				});
				list.add(worker);
				// レフト位置更新.
				left += columnWidth;
			}
			// トップ位置更新.
			top += rowHeight;
		}
		for (Future<CellPanel> swingWorker : list) {
			CellPanel newCellPanel;
			try {
				newCellPanel = swingWorker.get();
				if (newCellPanel != null) {
					// System.out.println(newCellPanel.getText());
					report.add(newCellPanel);
				}
			}
			catch (InterruptedException exp) {
				exp.printStackTrace();
			}
			catch (ExecutionException exp) {
				exp.printStackTrace();
			}
		}
		executorService.shutdown();
	}

	/**
	 * セルパネルを生成します<br>
	 * 
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @param cell
	 * @param style
	 * @return
	 */
	CellPanel createCellPanel(final int rowIndex, final int columnIndex, final int left, final int top, final int width,
	        final int height, final XMLCell cell, final XMLStyle style, final double scaleRate) {
		// セルパネル生成.
		CellPanel newCellPanel = new CellPanel();
		newCellPanel.setSize(width, height);
		newCellPanel.setLocation(left, top);
		// フォント設定.
		newCellPanel.setFont(style.getFont());
		if (scaleRate != 1d) {
			float newFontSize = newCellPanel.getFont().getSize2D();
			newFontSize *= scaleRate;
			newCellPanel.setFont(newCellPanel.getFont().deriveFont(newFontSize));
		}
		// 配置設定.
		newCellPanel.setHorizontalAlignment(style.getHorizontalAlignment());
		newCellPanel.setVerticalAlignment(style.getVerticalAlignment());
		// 変数キー情報取得.
		String value = cell.getValue();
		String key = null;
		int rowsCount = 0;
		if ((value != null) && value.startsWith(VariableConstants.KEY_STRING)) {
			key = value;
			rowsCount = 1;
			int openIndex = value.indexOf(VariableConstants.LEFT_PARENTHESES);
			int closeIndex = value.indexOf(VariableConstants.RIGHT_PARENTHESES, openIndex + 1);
			if ((openIndex >= 0) && (closeIndex >= 0)) {
				try {
					rowsCount = Integer.parseInt(value.substring(openIndex + 1, closeIndex));
					key = value.substring(0, openIndex);
				}
				catch (NumberFormatException exp) {
					exp.printStackTrace();
				}
			}
		}
		newCellPanel.setKey(key);
		newCellPanel.setIntMaxRow(rowsCount);
		// 値設定.
		String text = convertToHTMLFormat(cell.getValue(), style.getHorizontalAlignment());
		if (!(text == null || text.isEmpty())) {
			newCellPanel.setText(text);
		}
		// 行列設定.
		newCellPanel.setRowIndex(rowIndex);
		newCellPanel.setColumnIndex(columnIndex);
		// 罫線追加.
		if (style.hasBorder()) {
			BorderLabel newBorder = new BorderLabel();
			newBorder.setAncher(newCellPanel);

			for (XMLBorder border : style.getBorders()) {
				newCellPanel.setBorder(border.getPosition(), border.getLineStyle(), border.getLineWeight());
			}
		}
		//
		return newCellPanel;
	}

	/**
	 * セル値をHTML形式に変換します<br>
	 * <br>
	 * 復帰コード文字(\n)を "&lt;br&gt;" に、改行コード文字(\r)を空文字列に変換します<br>
	 * <br>
	 * HTML形式に変換する必要がない場合は、プレーンテキストを返します<br>
	 * 
	 * @oaram str セル値の文字列<br>
	 * @param horizontalAlignment
	 *            横位置を SwingConstants.LEFT、～.CENTER、～.RIGHT から指定します<br>
	 * @return HTML形式の文字列<br>
	 */
	private String convertToHTMLFormat(final String str, final int horizontalAlignment) {
		// HTML形式に変換が必要なのは、改行が含まれる場合のみ.
		if ((str == null) || ((str.indexOf('\n') < 0) && (str.indexOf('\r') < 0))) {
			return str;
		}

		// HTML形式文字列生成.
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		switch (horizontalAlignment) {
			case SwingConstants.RIGHT:
				html.append("<p align='right'>");
				break;
			case SwingConstants.CENTER:
				html.append("<p align='center'>");
				break;
			default:
				html.append("<p align='left'>");
		}
		// エンコード.
		for (int i = 0; i < str.length(); i++) {
			if (encodingMap.containsKey(str.charAt(i))) {
				// エンコード対象文字.
				html.append(encodingMap.get(str.charAt(i)));
			}
			else {
				// その他の文字.
				html.append(str.charAt(i));
			}
		}
		//
		html.append("</p>");
		html.append("</html>");
		return html.toString();
	}

	// --------------------------------------------------

	/**
	 * 列(セル)の幅をピクセル数で取得します<br>
	 * 
	 * @param width
	 *            列幅 (ポイント指定)<br>
	 * @return
	 */
	int getWidthPixels(final double width, final double widthCoefficient) {
		// 帳票レイアウト XML ファイルでの列(セル)の幅は、.
		// ポイント数で記述されている.
		// 拡大縮小率によって変更される.
		return (int) Math.floor(UnitConversion.pointsToPixels(width, widthCoefficient));
	}

	// --------------------------------------------------

	/**
	 * 行(セル)の高さをピクセル数で取得します<br>
	 * 
	 * @param height
	 *            行高さ (ポイント指定)<br>
	 * @return
	 */
	int getHeightPixels(final double height, final double heightCoefficient) {
		// 帳票レイアウト XML ファイルでの行(セル)の高さは、.
		// ポイント数で記述されている.
		// 拡大縮小率によって変更される.
		return (int) Math.floor(UnitConversion.pointsToPixels(height, heightCoefficient));
	}
	// --------------------------------------------------
}
