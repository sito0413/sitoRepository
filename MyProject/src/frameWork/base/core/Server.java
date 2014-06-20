package frameWork.base.core;

import org.eclipse.jetty.webapp.WebAppContext;

import frameWork.base.core.fileSystem.FileSystem;

public class Server {
	public Server(final int port) throws Exception {
		final org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
		final WebAppContext webapp = new WebAppContext();
		webapp.addFilter(WrapFilter.class, "/*", null);
		webapp.setWar(FileSystem.Root.toURI().toURL().toExternalForm());
		webapp.setContextPath("/");
		server.setHandler(webapp);
		server.start();
	}
}
