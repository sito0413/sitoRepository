package frameWork;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class FrameWorkKicker {
	public static void main(final String[] arg) throws Exception {
		final URLClassLoader classLoader = new URLClassLoader(toURL(), ClassLoader.getSystemClassLoader());
		final Class<?> cls = Class.forName("frameWork.manager.FrameworkManager", true, classLoader);
		final java.lang.reflect.Method method = cls.getMethod("main", new Class[] {
			String[].class
		});
		
		method.invoke(null, (Object) (arg));
	}
	
	private static URL[] toURL() throws Exception {
		final File file = new File(FrameWorkKicker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		final File[] files = file.listFiles();
		final URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			urls[i] = files[i].toURI().toURL();
		}
		return urls;
	}
}
