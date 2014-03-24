package frameWork.utility.state;

import java.io.File;

import frameWork.databaseConnector.DatabaseConnector;
import frameWork.utility.state.attributeMap.AttributeMap;

@SuppressWarnings("nls")
public interface State {
	
	public String[] auth();
	
	public AttributeMap getContext();
	
	public AttributeMap getSession();
	
	public AttributeMap getRequest();
	
	public String getParameter(final String name);
	
	public File getFile(final String name);
	
	public DatabaseConnector getConnector();
	
	public String getPage();
	
	public void setPage(final String page);
	
	public void setConnector(DatabaseConnector connector);
}