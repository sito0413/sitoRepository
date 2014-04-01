package monitoring.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class XMLResourceBundle extends ResourceBundle {
	private final Properties properties;
	
	public XMLResourceBundle(final InputStream stream) throws IOException {
		// Propertiesクラスを使用してXMLファイルを読み込む
		properties = new Properties();
		properties.loadFromXML(stream);
	}
	
	@Override
	public Object handleGetObject(final String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return properties.get(key);
	}
	
	@Override
	public Enumeration<String> getKeys() {
		return (Enumeration<String>) properties.propertyNames();
	}
}
