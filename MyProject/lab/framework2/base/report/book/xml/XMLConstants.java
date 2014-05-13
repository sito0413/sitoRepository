package framework2.base.report.book.xml;

/**
 * XML定数<br>
 * 
 * @version 1.0.0
 * @作成者 津田.
 * @作成日 2009/06/16
 * @最終更新者 若井.
 * @最終更新日 2009/10/19
 */
@SuppressWarnings("nls")
public interface XMLConstants {

	/* 【タグ】 */

	/** スタイルス */
	public static final String STYLES = "Styles";
	/** スタイル */
	public static final String STYLE = "Style";
	/** アラインメント */
	public static final String STYLE_ALIGNMENT = "Alignment";
	// /** ボーダーズ */
	// public static final String STYLE_BORDERS = "Borders";
	/** ボーダ */
	public static final String STYLE_BORDER = "Border";
	/** フォント */
	public static final String STYLE_FONT = "Font";
	/** 内部書式 */
	public static final String STYLE_INTERIOR = "Interior";
	/** 書式 */
	public static final String STYLE_NUMBERFORMAT = "NumberFormat";
	/** 保護 */
	public static final String STYLE_PROTECTION = "Protection";

	/** ワークシート */
	public static final String WORKSHEET = "Worksheet";
	/** テーブル */
	public static final String ELM_TABLE = "Table";
	/** 列 */
	public static final String ELM_COLUMN = "Column";
	/** 行 */
	public static final String ELM_ROW = "Row";
	/** セル */
	public static final String ELM_CELL = "Cell";
	/** データ */
	public static final String ELM_DATA = "Data";
	/** ワークシートオプション */
	public static final String ELM_WSOPTIONS = "WorksheetOptions";
	/** ページセットアップ */
	public static final String ELM_PAGESETUP = "PageSetup";
	/** ヘッダ */
	public static final String ELM_HEADER = "Header";
	/** フッタ */
	public static final String ELM_FOOTER = "Footer";
	/** 余白 */
	public static final String ELM_PAGEMARGINS = "PageMargins";
	/** 印刷情報 */
	public static final String ELM_PRINT = "Print";
	/** 用紙サイズ */
	public static final String ELM_PAPERSIZE = "PaperSizeIndex";
	/** 用紙方向 */
	public static final String ELM_LAYOUT = "Layout";
	/** 倍率 */
	public static final String ELM_SCALE = "Scale";

	/* 【属性キー】 */

	/** スタイルID */
	public static final String ATTR_ID = "ss:ID";
	/** スタイル名 */
	public static final String ATTR_NAME = "ss:Name";
	/** 継承元スタイルID */
	public static final String ATTR_PARENT_ID = "ss:Parent";
	/** 横アラインメント */
	public static final String ATTR_HORIZONAL = "ss:Horizontal";
	/** 縦アラインメント */
	public static final String ATTR_VERTICAL = "ss:Vertical";
	/** ボーダポジション */
	public static final String ATTR_POSITION = "ss:Position";
	/** ボーダスタイル */
	public static final String ATTR_LINESTYLE = "ss:LineStyle";
	/** ボーダ太さ */
	public static final String ATTR_WEIGHT = "ss:Weight";
	/** フォント名 */
	public static final String ATTR_FONTNAME = "ss:FontName";
	/** フォントサイズ */
	public static final String ATTR_SIZE = "ss:Size";
	/** フォント太字 */
	public static final String ATTR_BOLD = "ss:Bold";
	/** フォントイタリック */
	public static final String ATTR_ITALIC = "ss:Italic";
	/** フォント下線 */
	public static final String ATTR_UNDERLINE = "ss:Underline";
	/** 縮小表示 */
	public static final String ATTR_SHRINKTOFIT = "ss:ShrinkToFit";

	/** 有効列数 */
	public static final String ATTR_EXCOLUMNCOUNT = "ss:ExpandedColumnCount";
	/** 有効行数 */
	public static final String ATTR_EXROWCOUNT = "ss:ExpandedRowCount";
	/** デフォルト列幅 */
	public static final String ATTR_DEFCOLUMNWIDTH = "ss:DefaultColumnWidth";
	/** デフォルト行高さ */
	public static final String ATTR_DEFROWHEIGHT = "ss:DefaultRowHeight";
	/** 列幅 */
	public static final String ATTR_WIDTH = "ss:Width";
	/** 行高さ */
	public static final String ATTR_HEIGHT = "ss:Height";
	/** 行、列、セルのインデックス */
	public static final String ATTR_INDEX = "ss:Index";
	/** セルのスタイル指定ID */
	public static final String ATTR_STYLEID = "ss:StyleID";
	/** 繰り返し回数 */
	public static final String ATTR_SPAN = "ss:Span";
	/** 縦結合 */
	public static final String ATTR_MERGEDOWN = "ss:MergeDown";
	/** 横結合 */
	public static final String ATTR_MERGEACROSS = "ss:MergeAcross";
	/** ヘッダ、フッタの高さ */
	public static final String ATTR_HFMARGIN = "x:Margin";
	/** 上マージン */
	public static final String ATTR_MARGINTOP = "x:Top";
	/** 左マージン */
	public static final String ATTR_MARGINLEFT = "x:Left";
	/** 右マージン */
	public static final String ATTR_MARGINRIGHT = "x:Right";
	/** 下マージン */
	public static final String ATTR_MARGINBOTTOM = "x:Bottom";
	/** 用紙方向 */
	public static final String ATTR_ORIENTATION = "x:Orientation";

	/* 【属性値】 */

	/** 左 */
	public static final String POSIT_LEFT = "Left";
	/** センタ */
	public static final String POSIT_CENTER = "Center";
	/** 右 */
	public static final String POSIT_RIGHT = "Right";
	/** 上 */
	public static final String POSIT_TOP = "Top";
	/** 下 */
	public static final String POSIT_BOTTOM = "Bottom";
	/** 左上からの対角線 */
	public static final String POSIT_DLEFT = "DiagonalLeft";
	/** 右上からの対角線 */
	public static final String POSIT_DRIGHT = "DiagonalRight";

	/** 連続線 */
	public static final String LSTYLE_CONTINUOUS = "Continuous";
	/** 二重線 */
	public static final String LSTYLE_DOUBLE = "Double";

	/** 1ページ目 */
	public static final String SHEET1 = "Sheet1";
	/** 2ページ目以降 */
	public static final String SHEET2 = "Sheet2";

	/* 【その他】 */

	/** 特殊文字列(不要?) */
	public static final String NOTCHILD_STRING = "#text";

	/** エクセル改行 */
	public static final String EXCEL_CRLF = "&#10;";

	/** デフォルトスタイルID */
	public static final String DEFAULTSTYLE_ID = "Default";

}
