package frameWork.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UtilityUrl {
	private final String url;
	
	public UtilityUrl(final String url) {
		this.url = url;
	}
	
	public UtilityUrl addParameter(final String parameterName, final String value) {
		char c = '?';
		if (url.contains("?")) {
			c = '&';
		}
		try {
			return new UtilityUrl(url + c + parameterName + '=' + URLEncoder.encode(value, "UTF-8"));
		}
		catch (final UnsupportedEncodingException e) {
			return new UtilityUrl(url + c + parameterName + '=' + value);
		}
	}
	
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return url;
	}
	
	public boolean equals(final UtilityUrl obj) {
		return url.equals(obj.url);
	}
	
	public boolean equals(final String obj) {
		return url.equals(obj);
	}
}
