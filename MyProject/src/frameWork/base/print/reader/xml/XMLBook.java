package frameWork.base.print.reader.xml;

import static frameWork.base.print.reader.xml.XMLConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * ワークブック<br>
 */
class XMLBook {
	private static Map<String, XMLStyle> parseStyles(final Element stylesElement) {
		final Map<String, XMLStyle> stylesMap = new HashMap<>();
		// スタイルノードが指定されていない場合は終了.
		if (stylesElement == null) {
			return stylesMap;
		}
		// スタイルリストを取得.
		final NodeList stylesNodeList = stylesElement.getElementsByTagName(STYLE);
		// スタイルごとにオブジェクトを生成.
		for (int i = 0; i < stylesNodeList.getLength(); i++) {
			final Element styleElement = (Element) stylesNodeList.item(i);
			final String styleId = styleElement.getAttribute(ATTR_ID);
			String parentId = styleElement.getAttribute(ATTR_PARENT_ID);
			if (parentId.isEmpty()) {
				// ---[ Note ]---------------------------------------
				// Style タグに Parent 属性がない場合は Default スタ.
				// イルの設定を継承する.
				// 処理対象が Defaultスタイルの場合は、継承元スタイ.
				// ルに自身を指定しても、存在しないので XML ファイル.
				// で定義されている既定値が使用される.
				// --------------------------------------------------
				parentId = DEFAULTSTYLE_ID;
			}
			stylesMap.put(styleId, new XMLStyle(stylesMap.get(parentId), styleElement));
		}
		return stylesMap;
	}
	
	private static Map<String, XMLSheet> parseWorksheets(final NodeList worksheetsNodeList) {
		final Map<String, XMLSheet> worksheetsMap = new HashMap<>();
		// ワークシートノードリストが指定されていない場合は終了.
		if (worksheetsNodeList == null) {
			return worksheetsMap;
		}
		// ワークシートごとにオブジェクトを生成.
		for (int i = 0; i < worksheetsNodeList.getLength(); i++) {
			final Element worksheetElement = (Element) worksheetsNodeList.item(i);
			final String worksheetName = worksheetElement.getAttribute(ATTR_NAME);
			if (worksheetElement.getElementsByTagName(ELM_TABLE).item(0) == null) {
				continue;
			}
			worksheetsMap.put(worksheetName, new XMLSheet(worksheetElement));
		}
		return worksheetsMap;
	}
	
	//** ワークシートマップ */
	final Map<String, XMLSheet> worksheetsMap;
	//** スタイルマップ */
	final Map<String, XMLStyle> stylesMap;
	
	XMLBook(final Document document) {
		worksheetsMap = parseWorksheets(document.getElementsByTagName(WORKSHEET));
		stylesMap = parseStyles((Element) document.getElementsByTagName(STYLES).item(0));
	}
}
