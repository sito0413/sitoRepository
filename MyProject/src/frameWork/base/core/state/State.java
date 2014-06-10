package frameWork.base.core.state;

import java.io.File;

import frameWork.base.core.authority.Role;

public interface State {
	public Role[] getAuth();
	
	public void setAuth(final Role[] auth);
	
	public AttributeMap getContext();
	
	public AttributeMap getSession();
	
	public AttributeMap getRequest();
	
	public String getParameter(final String name);
	
	public File getFile(final String name);
	
	public String getPage();
	
	public void setPage(final String page);
	
	public boolean isViewCompiler();
	
	public boolean isLogin();
	
	public void setViewCompiler(final boolean isViewCompiler);
}