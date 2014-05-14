package frameWork.base.core.routing;

public class TargetFilter {
	
	public static String[] getClassName(final String target, final char c) {
		if (target == null) {
			return null;
		}
		final String[] value = new String[2];
		final String[] uri = (target.startsWith("/") ? target.substring(1) : target).split("/");
		if (uri.length == 0) {
			return null;
		}
		
		final StringBuilder className = new StringBuilder();
		for (int i = 0; i < (uri.length - 1); i++) {
			if (uri[i].isEmpty() || uri[i].contains(".")) {
				return null;
			}
			className.append(c).append(uri[i]);
		}
		value[0] = className.toString();
		if (uri[(uri.length - 1)].isEmpty()) {
			return null;
		}
		else if (uri[(uri.length - 1)].contains(".")) {
			value[1] = uri[(uri.length - 1)].substring(0, uri[(uri.length - 1)].indexOf('.'));
		}
		else {
			value[1] = uri[(uri.length - 1)];
		}
		return value;
	}
}
