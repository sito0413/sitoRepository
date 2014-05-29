package frameWork.architect;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CoverageHandler extends DefaultHandler {
	StringBuilder builder = new StringBuilder("カバレッジ \r\n");
	boolean flag = false;
	
	@Override
	public void startElement(final String namespaceURI, final String localName, final String qName,
	        final Attributes atts) {
		if (qName.equals("counter")) {
		}
		if (!flag && qName.equals("counter")) {
			if (atts.getValue("type").equals("INSTRUCTION") || atts.getValue("type").equals("BRANCH")) {
				final int missed = Integer.parseInt(atts.getValue("missed"));
				final int covered = Integer.parseInt(atts.getValue("covered"));
				builder.append("　").append(atts.getValue("type"))
				        .append(atts.getValue("type").length() > 8 ? "\t" : "\t\t")
				        .append(covered / (missed + covered)).append("％ (").append(covered).append(" of ")
				        .append((missed + covered)).append(")\r\n");
			}
		}
		else if (qName.equals("package")) {
			flag = true;
		}
	}
	
	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		if (qName.equals("package")) {
			flag = false;
		}
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
}
