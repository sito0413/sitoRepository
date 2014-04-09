package frameWork.databaseConnector;

import java.util.Properties;

public class DatabaseConnectorKey {
	private final Properties properties;
	private final String url;
	private final String driverClassName;
	
	public DatabaseConnectorKey(final String username, final String password, final String url,
	        final String driverClassName) {
		this.url = url;
		this.driverClassName = driverClassName;
		this.properties = new Properties();
		if (username != null) {
			this.properties.setProperty("user", username);
		}
		if (password != null) {
			this.properties.setProperty("password", password);
		}
	}
	
	public String getUrl() {
		return url;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
}
