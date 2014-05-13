package frameWork.base.core.routing;

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
			return new UtilityUrl(c + parameterName + '=' + URLEncoder.encode(value, "UTF-8"));
		}
		catch (final UnsupportedEncodingException e) {
			return new UtilityUrl(c + parameterName + '=' + value);
		}
	}
	
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return url;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj)) {
			return url.equalsIgnoreCase(obj.toString());
		}
		return true;
	}
	
	public boolean equals(final UtilityUrl obj) {
		return url.equalsIgnoreCase(obj.url);
	}
	
	public boolean equals(final String obj) {
		return url.equalsIgnoreCase(obj);
	}
}
