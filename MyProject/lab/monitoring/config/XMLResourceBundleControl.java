package monitoring.config;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class XMLResourceBundleControl extends ResourceBundle.Control {
	public static final String XML = "xml";
	public static final List<String> FORMAT_XML = Collections.unmodifiableList(Arrays.asList(XML));
	
	@Override
	public List<String> getFormats(final String baseName) {
		if (baseName == null) {
			throw new NullPointerException();
		}
		// XML 形式のリソースバンドルだけを扱う
		return FORMAT_XML;
	}
	
	@Override
	public long getTimeToLive(final String baseName, final Locale locale) {
		return 10 * 1000L;
	}
	
	@Override
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
	        final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException,
	        IOException {
		
		if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
			throw new NullPointerException();
		}
		
		ResourceBundle bundle = null;
		// XML 形式のリソースバンドルだけを扱う
		if (format.equals(XML)) {
			final String bundleName = toBundleName(baseName, locale);
			final String resourceName = toResourceName(bundleName, format);
			InputStream stream = null;
			if (reload) {
				final URL url = loader.getResource(resourceName);
				if (url != null) {
					final URLConnection connection = url.openConnection();
					if (connection != null) {
						// 再読み込みのときにキャッシュを使わないようにする
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			}
			else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				final BufferedInputStream bis = new BufferedInputStream(stream);
				bundle = new XMLResourceBundle(bis);
				bis.close();
			}
		}
		return bundle;
	}
}
