package frameWork.architect;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class TestHandler extends DefaultHandler {
	StringBuilder builder = new StringBuilder("テスト結果 \r\n");
	
	@Override
	public void startElement(final String namespaceURI, final String localName, final String qName,
	        final Attributes atts) {
		if (qName.equals("testsuite")) {
			builder.append("　name\t: ").append(atts.getValue("name")).append("\r\n");
			builder.append("　tests\t: ").append(atts.getValue("tests")).append("件\r\n");
			builder.append("　errors\t: ").append(atts.getValue("errors")).append("件\r\n");
			builder.append("　failures\t: ").append(atts.getValue("failures")).append("件\r\n");
		}
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
}
