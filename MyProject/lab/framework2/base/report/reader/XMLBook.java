package framework2.base.report.reader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import framework2.base.report.util.XMLConstants;

/**
 * ワークブック<br>
 */
public class XMLBook {

	/** ワークシートマップ */
	private final Map<String, XMLSheet> worksheetsMap;
	/** スタイルマップ */
	private final Map<String, XMLStyle> stylesMap;

	/**
	 * コンストラクタ<br>
	 */
	public XMLBook(final Document document) {
		worksheetsMap = new ConcurrentHashMap<String, XMLSheet>();
		stylesMap = new ConcurrentHashMap<String, XMLStyle>();
		// スタイル.
		parseStyles((Element) document.getElementsByTagName(XMLConstants.STYLES).item(0));
		// ワークシート.
		parseWorksheets(document.getElementsByTagName(XMLConstants.WORKSHEET));

	}

	// --------------------------------------------------
	/**
	 * スタイルマップを取得します<br>
	 * 
	 * @return worksheetsMap
	 */
	public Map<String, XMLStyle> getStylesMap() {
		return this.stylesMap;
	}

	// --------------------------------------------------
	/**
	 * ワークシートを取得します<br>
	 * 
	 * @return worksheetsMap
	 */
	public XMLSheet getWorksheet(final String sheetName) {
		return this.worksheetsMap.get(sheetName);
	}

	// --------------------------------------------------

	/**
	 * スタイルをXMLからオブジェクト化します.
	 * 
	 * @param stylesNode
	 * @return
	 */
	public Map<String, XMLStyle> parseStyles(final Element stylesElement) {
		// スタイルノードが指定されていない場合は終了.
		if (stylesElement == null) {
			return stylesMap;
		}
		// スタイルリストを取得.
		NodeList stylesNodeList = stylesElement.getElementsByTagName(XMLConstants.STYLE);
		// スタイルごとにオブジェクトを生成.
		for (int i = 0; i < stylesNodeList.getLength(); i++) {
			Element styleElement = (Element) stylesNodeList.item(i);
			String styleId = styleElement.getAttribute(XMLConstants.ATTR_ID);
			String parentId = styleElement.getAttribute(XMLConstants.ATTR_PARENT_ID);
			if (parentId.isEmpty()) {
				// ---[ Note ]---------------------------------------
				// Style タグに Parent 属性がない場合は Default スタ.
				// イルの設定を継承する.
				// 処理対象が Defaultスタイルの場合は、継承元スタイ.
				// ルに自身を指定しても、存在しないので XML ファイル.
				// で定義されている既定値が使用される.
				// --------------------------------------------------
				parentId = XMLConstants.DEFAULTSTYLE_ID;
			}
			// スタイル生成＆属性設定.
			XMLStyle newStyle = new XMLStyle(stylesMap.get(parentId), styleElement);
			// スタイル追加
			stylesMap.put(styleId, newStyle);
		}
		return stylesMap;
	}

	// --------------------------------------------------

	/**
	 * ワークシートをXMLからオブジェクト化します<br>
	 * 
	 * @param worksheetsNodeList
	 * @return
	 */
	public Map<String, XMLSheet> parseWorksheets(final NodeList worksheetsNodeList) {
		// ワークシートノードリストが指定されていない場合は終了.
		if (worksheetsNodeList == null) {
			return worksheetsMap;
		}
		// ワークシートごとにオブジェクトを生成.
		for (int i = 0; i < worksheetsNodeList.getLength(); i++) {
			Element worksheetElement = (Element) worksheetsNodeList.item(i);
			String worksheetName = worksheetElement.getAttribute(XMLConstants.ATTR_NAME);
			if (worksheetElement.getElementsByTagName(XMLConstants.ELM_TABLE).item(0) == null) {
				continue;
			}
			// ワークシート生成＆属性設定.
			XMLSheet newWorksheet = new XMLSheet(worksheetElement);
			// ワークシート追加.
			worksheetsMap.put(worksheetName, newWorksheet);
		}
		//
		return worksheetsMap;
	}
}
