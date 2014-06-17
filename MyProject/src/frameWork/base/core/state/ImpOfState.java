package frameWork.base.core.state;

import java.io.File;
import java.util.List;
import java.util.Map;

import frameWork.base.core.authority.Role;
import frameWork.base.core.fileSystem.FileSystem;

public class ImpOfState implements State {
	private Role[] auth;
	private final AttributeMap context;
	private final AttributeMap session;
	private final AttributeMap request;
	private final Map<String, List<String>> parameters;
	private final Map<String, File> fileMap;
	private String page;
	private boolean isViewCompiler;
	private boolean isLogin;
	
	public ImpOfState(final AttributeMap context, final AttributeMap session, final AttributeMap request,
	        final Map<String, List<String>> parameters, final Map<String, File> fileMap) {
		this.context = context;
		this.session = session;
		this.request = request;
		this.parameters = parameters;
		this.fileMap = fileMap;
	}
	
	@Override
	public Role[] getAuth() {
		return auth;
	}
	
	@Override
	public void setAuth(final Role[] auth) {
		this.auth = auth;
		this.session.setAttribute(FileSystem.Config.CALL_AUTH, auth);
	}
	
	@Override
	public AttributeMap getContext() {
		return context;
	}
	
	@Override
	public AttributeMap getSession() {
		return session;
	}
	
	@Override
	public AttributeMap getRequest() {
		return request;
	}
	
	@Override
	public final String getParameter(final String name) {
		if (parameters.get(name) == null) {
			return null;
		}
		return parameters.get(name).get(0);
	}
	
	@Override
	public final File getFile(final String name) {
		return fileMap.get(name);
	}
	
	@Override
	public String getPage() {
		return page;
	}
	
	@Override
	public void setPage(final String page) {
		this.page = page;
	}
	
	@Override
	public boolean isViewCompiler() {
		return isViewCompiler;
	}
	
	@Override
	public boolean isLogin() {
		return isLogin;
	}
	
	@Override
	public void setViewCompiler(final boolean isViewCompiler) {
		this.isViewCompiler = isViewCompiler;
	}
	
	public void setLogin(final Role[] attribute) {
		this.auth = attribute;
		if ((auth == null) || (auth.length == 0)) {
			isLogin = false;
			setAuth(new Role[] {
				FileSystem.Config.DEFAULT_ROLE
			});
		}
		else {
			isLogin = true;
		}
	}
}