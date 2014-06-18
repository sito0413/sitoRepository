import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import frameWork.base.core.WrapFilter;

//http://repo1.maven.org/maven2/org/eclipse/jetty/aggregate/jetty-all
public class Main extends HttpServlet {
	public static void main(final String[] args) throws Exception {
		final Server server = new Server(8800);
		final URL warLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
		final WebAppContext webapp = new WebAppContext();
		System.out.println(warLocation.toExternalForm());
		webapp.addFilter(WrapFilter.class, "/*", null);
		webapp.addServlet(Main.class, "/*");
		webapp.setWar(warLocation.toExternalForm());
		webapp.setContextPath("/");
		server.setHandler(webapp);
		server.start();
		server.join();
	}
	
	@Override
	public void doGet(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
		
		res.getWriter().println("Servlet on Jetty." + req.getSession().getId());
	}
}
