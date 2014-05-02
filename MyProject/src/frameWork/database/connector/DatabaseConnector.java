package frameWork.database.connector;

import java.util.Properties;
import frameWork.database.connector.pool.ConnectorPool;

/*
 * このソースは database/DBコネクター.xls から自動生成されました。
 * 原則このソースは修正しないでください
 * 作成日時 Fri May 02 15:10:25 JST 2014
 */
public enum DatabaseConnector {

	MySQL("root", "sito", "jdbc:mysql://192.168.0.84:3306/", "manabezaikokannri"),
	MySQL2("root", "sito", "jdbc:mysql://192.168.0.84:3306/", "manabezaikokannri"),
	MySQL3("root", "sito", "jdbc:mysql://192.168.0.84:3306/", "manabezaikokannri"),
	;
	private final ConnectorPool connectorPool;
	private final Properties properties;
	private final String url;
	private final String driverClassName;
	private DatabaseConnector(final String username, final String password, final String url, final String driverClassName) {
		this.url = url;
		this.driverClassName = driverClassName;
		this.properties = new Properties();
		if (username != null) {
			this.properties.setProperty("user", username);
		}
		if (password != null) {
			this.properties.setProperty("password", password);
		}

		connectorPool = new ConnectorPool(this);
	}

	public synchronized DatabaseController getController() {
		return connectorPool.getConnector();
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
