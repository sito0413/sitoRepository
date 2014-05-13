package framework2.base.report.book.xml;

import java.util.Vector;

import javax.print.attribute.standard.MediaSizeName;

/**
 * 用紙サイズ<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/19
 */
public enum XMLPaperSize {

	/** A4 */
	A4(9),

	/** A3 */
	A3(8);

	/**
	 * コンストラクタ<br>
	 * 
	 * @param xmlValue
	 */
	private XMLPaperSize(final int xmlValue) {
		this.setXMLValue(xmlValue);
	}

	/** XMLファイルの値 */
	private int xmlValue;

	/**
	 * XMLファイルの値を取得します<br>
	 * 
	 * @return
	 */
	public int getXMLValue() {
		return this.xmlValue;
	}

	/**
	 * XMLファイルの値を設定します<br>
	 * 
	 * @param xmlValue
	 */
	private void setXMLValue(final int xmlValue) {
		this.xmlValue = xmlValue;
	}

	/**
	 * 対応する MediaSizeName型 に変換します<br>
	 * 
	 * @return
	 */
	public MediaSizeName toMediaSizename() {
		MediaSizeName mediaSizeName;
		switch (this) {
		case A3:
			mediaSizeName = MediaSizeName.ISO_A3;
			break;
		case A4:
		default:
			mediaSizeName = MediaSizeName.ISO_A4;
			break;
		}
		return mediaSizeName;
	}

	public static XMLPaperSize parse(final Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof XMLPaperSize) {
			return (XMLPaperSize) obj;
		}
		XMLPaperSize result = null;
		try {
			int i = Integer.parseInt(String.valueOf(obj));
			for (XMLPaperSize paperSizel : XMLPaperSize.getList()) {
				if (paperSizel.getXMLValue() == i) {
					result = paperSizel;
					break;
				}
			}
		}
		catch (NumberFormatException exp) {
			exp.printStackTrace();
		}
		return result;
	}

	private static Vector<XMLPaperSize> getList() {
		Vector<XMLPaperSize> list = new Vector<>();
		list.add(XMLPaperSize.A4);
		list.add(XMLPaperSize.A3);
		return list;
	}

}
