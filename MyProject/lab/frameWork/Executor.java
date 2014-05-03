package frameWork;


public class Executor {
	public static void main(final String[] args) {
		int port = 8800;
		String url = "";
		if (args.length >= 2) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch (final Exception e) {
				
			}
			url = args[1];
		}
		try {
			//			final ServerConnector server = Server.connect(port, new File(".").getCanonicalFile(),
			//			        new DatabaseConnectorKey("kousyu", "newwave98",
			//			                "jdbc:sqlserver://nwksv001\\SQLEXPRESS:49363;database=kousyu"
			//			                //   + this.resourceManager.getDataBaseDir().getAbsolutePath() + ";create=true"
			//			                ,// "org.apache.derby.jdbc.EmbeddedDriver"
			//			                "com.microsoft.sqlserver.jdbc.SQLServerDriver"));
			//			if (Desktop.isDesktopSupported()) {
			//				try {
			//					Desktop.getDesktop().browse(
			//					        new URI("http://localhost" + (port == 80 ? "" : (":" + port)) + "/" + url));
			//				}
			//				catch (final Exception e) {
			//					e.printStackTrace();
			//				}
			//				server.join();
			//			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
