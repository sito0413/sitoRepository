package frameWork.base.core.targetFilter;

import java.lang.reflect.Method;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.State;

public class TargetFilter {
	public static Target parse(final String target, final String method) {
		final String className = getClassName(target);
		if ((className == null) || (method == null)) {
			return null;
		}
		try {
			final Class<?> c = Class.forName(FileSystem.Config.getString("packageName", "controller") + className);
			if (c.isAssignableFrom(TargetHandler.class)) {
				final Method m = c.getMethod(method, State.class);
				return new Target(c, m, className.replace(".", "/") + ".jsp");
			}
			
		}
		catch (final ClassNotFoundException | NoSuchMethodException e) {
			//NOOP
		}
		return null;
	}
	
	static String getClassName(final String target) {
		if (target == null) {
			return null;
		}
		
		final String[] uri = (target.startsWith("/") ? target.substring(1) : target).split("/");
		if (uri.length == 0) {
			return null;
		}
		
		final StringBuilder className = new StringBuilder();
		for (int i = 0; i < (uri.length - 1); i++) {
			if (uri[i].isEmpty() || uri[i].contains(".")) {
				return null;
			}
			className.append(".").append(uri[i]);
		}
		if (uri[(uri.length - 1)].isEmpty()) {
			return null;
		}
		else if (uri[(uri.length - 1)].contains(".")) {
			className.append(".").append(uri[(uri.length - 1)].subSequence(0, uri[(uri.length - 1)].indexOf('.')));
			return className.toString();
		}
		else {
			className.append(".").append(uri[(uri.length - 1)]);
			return className.toString();
		}
	}
}
