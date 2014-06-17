package frameWork.architect;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.manager.tab.TabPanel;

public class ArchitectTab extends TabPanel {
	JTextPane textPane;
	
	public ArchitectTab() {
		setLayout(new BorderLayout(0, 0));
		{
			{
				textPane = new JTextPane();
				textPane.setEditable(false);
				try {
					final TestHandler handler = new TestHandler();
					final URL url = FileSystem.class.getResource("/frameWork/architect/TEST-frameWork.AllTests.xml");
					if (url != null) {
						final URLConnection connection = url.openConnection();
						if (connection != null) {
							SAXParserFactory.newInstance().newSAXParser().parse(connection.getInputStream(), handler);
							textPane.setText(textPane.getText() + handler);
						}
					}
				}
				catch (final IOException | ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}
				try {
					try (PrintStream fi = new PrintStream(new File("report.dtd"))) {
						fi.print("<?final xml version=\"1.0\" encoding=\"UTF-8\"?>");
					}
					
					final CoverageHandler handler = new CoverageHandler();
					final URL url = FileSystem.class.getResource("/frameWork/architect/result.xml");
					if (url != null) {
						final URLConnection connection = url.openConnection();
						if (connection != null) {
							SAXParserFactory.newInstance().newSAXParser().parse(connection.getInputStream(), handler);
							textPane.setText(textPane.getText() + handler);
						}
					}
					new File("report.dtd").delete();
				}
				catch (final IOException | ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}
			}
			add(new JScrollPane(textPane), BorderLayout.CENTER);
		}
	}
	
	@Override
	public String getTabName() {
		return "基本情報";
	}
}
