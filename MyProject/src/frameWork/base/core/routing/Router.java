package frameWork.base.core.routing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import frameWork.architect.Literal;
import frameWork.base.HttpStatus;
import frameWork.base.core.authority.AuthorityChecker;
import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;

public class Router {
	private static final String DEFAULT_METHOD = "get".toLowerCase();
	
	public static int routing(final String requestURI, final String method, final Response response, final State state)
	        throws Exception {
		if (method != null) {
			Rout rout = getRout(requestURI, method);
			if (rout != null) {
				if (AuthorityChecker.check(rout.className, rout.methodName, state.getAuth())) {
					rout.invoke(state, response);
					return HttpStatus.OK_200;
				}
				
				if (!state.isLogin()) {
					rout = getRout(FileSystem.Config.LOGIN_PATH, DEFAULT_METHOD);
					if (rout != null) {
						if (AuthorityChecker.check(rout.className, rout.methodName, state.getAuth())) {
							rout.invoke(state, response);
							return HttpStatus.OK_200;
						}
					}
				}
				return HttpStatus.UNAUTHORIZED_401;
			}
			return HttpStatus.NOT_FOUND_404;
		}
		return HttpStatus.BAD_REQUEST_400;
	}
	
	private static Rout getRout(final String requestURI, final String method) throws NoSuchMethodException,
	        SecurityException {
		Rout rout = null;
		try {
			final Properties properties = new Properties();
			try (FileInputStream fileInputStream = new FileInputStream(FileSystem.Config.Routing)) {
				properties.loadFromXML(fileInputStream);
			}
			final String className = properties.getProperty(requestURI);
			if (className != null) {
				final Class<?> c = Class.forName(Literal.packageName + className);
				if (RoutingHandler.class.isAssignableFrom(c)) {
					rout = new Rout(c, c.getMethod(method, State.class), className.replace(".", "/") + ".jsp");
				}
			}
		}
		catch (final Exception e) {
			FileSystem.Log.logging(e);
		}
		if (rout == null) {
			final String[] value = getClassName(requestURI, '.');
			if (value == null) {
				return null;
			}
			final String className = value[0] + "." + value[1];
			try {
				final Class<?> c = Class.forName(Literal.packageName + className);
				if (RoutingHandler.class.isAssignableFrom(c)) {
					rout = new Rout(c, c.getMethod(method, State.class), className.replace(".", "/") + ".jsp");
					try {
						final Properties properties = new Properties();
						if (!FileSystem.Config.Routing.exists()) {
							try (FileOutputStream fileOutputStream = new FileOutputStream(FileSystem.Config.Routing)) {
								new Properties().storeToXML(fileOutputStream, "");
							}
						}
						try (FileInputStream fileInputStream = new FileInputStream(FileSystem.Config.Routing)) {
							properties.loadFromXML(fileInputStream);
						}
						properties.setProperty(requestURI, className);
						try (FileOutputStream fileOutputStream = new FileOutputStream(FileSystem.Config.Routing)) {
							properties.storeToXML(fileOutputStream, "");
						}
					}
					catch (final Exception e) {
						FileSystem.Log.logging(e);
					}
				}
			}
			catch (final ClassNotFoundException | NoSuchMethodException e) {
				//NOOP
			}
		}
		if (rout == null) {
			final File resource = FileSystem.Resource.getResource(requestURI);
			if (resource.exists()) {
				rout = new ResourceRout(method, resource.getAbsolutePath());
			}
		}
		return rout;
	}
	
	public static String[] getClassName(final String target, final char c) {
		if (target == null) {
			return null;
		}
		final String[] value = new String[2];
		final String[] uri = (target.startsWith("/") ? target.substring(1) : target).split("/");
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
