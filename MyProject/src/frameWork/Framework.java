package frameWork;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import frameWork.base.core.WrapFilter;
import frameWork.base.core.fileSystem.FileSystem;

public class Framework {
	
	public static void main(final String[] args) throws Exception {
		final Server server = new Server(FileSystem.Config.PORT_NO);
		final WebAppContext webapp = new WebAppContext();
		webapp.addFilter(WrapFilter.class, "/*", null);
		webapp.setWar(FileSystem.Root.toURI().toURL().toExternalForm());
		webapp.setContextPath("/");
		server.setHandler(webapp);
		server.start();
		server.join();
	}
	
}
