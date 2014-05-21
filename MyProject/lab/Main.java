

import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.deploy.PropertiesConfigurationManager;
import org.eclipse.jetty.deploy.providers.WebAppProvider;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

//http://repo1.maven.org/maven2/org/eclipse/jetty/aggregate/jetty-all
public class Main {
	public static void main(final String[] args) throws Exception {
		final Server server = new Server(8800);
		
		final HandlerCollection handlers = new HandlerCollection();
		final ContextHandlerCollection contexts = new ContextHandlerCollection();
		handlers.setHandlers(new Handler[] {
		        contexts, new DefaultHandler()
		});
		server.setHandler(handlers);
		
		final DeploymentManager deployer = new DeploymentManager();
		deployer.setContexts(contexts);
		
		final WebAppProvider webappProvider = new WebAppProvider();
		webappProvider.setMonitoredDirName("./main/webapps");
		webappProvider.setScanInterval(1);
		webappProvider.setExtractWars(true);
		webappProvider.setConfigurationManager(new PropertiesConfigurationManager());
		
		deployer.addAppProvider(webappProvider);
		server.addBean(deployer);
		server.start();
		server.join();
	}
}
