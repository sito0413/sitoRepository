package frameWork.base.core.routing;

import java.io.IOException;
import java.lang.reflect.Method;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ViewCompiler;

class Rout {
	public final Class<?> className;
	public final Method methodName;
	public final String page;
	
	Rout(final Class<?> className, final Method methodName, final String page) {
		this.className = className;
		this.methodName = methodName;
		this.page = page;
	}
	
	void invoke(final State state, final Response response) throws Exception {
		state.setViewCompiler(true);
		state.setPage(page);
		methodName.setAccessible(true);
		methodName.invoke(className.newInstance(), state);
		if (state.isViewCompiler()) {
			viewCompile(state, response);
		}
		else {
			invokeResource(state, response);
		}
	}
	
	void invokeResource(final State state, final Response response) throws NoSuchMethodException, SecurityException,
	        Exception {
		new ResourceRout(methodName.getName(), FileSystem.Data.getResource(state.getPage()).getAbsolutePath()).invoke(
		        state, response);
	}
	
	void viewCompile(final State state, final Response response) throws IOException, ScriptException {
		ViewCompiler.compile(response, state);
	}
}
