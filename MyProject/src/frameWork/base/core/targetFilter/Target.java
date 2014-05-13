package frameWork.base.core.targetFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.State;

public class Target {
	public final Class<?> className;
	public final Method methodName;
	public final String page;
	
	Target(final Class<?> className, final Method methodName, final String page) {
		this.className = className;
		this.methodName = methodName;
		this.page = page;
	}
	
	public boolean invoke(final State state) {
		try {
			state.setViewCompiler(true);
			state.setPage(page);
			methodName.setAccessible(true);
			methodName.invoke(className.newInstance(), state);
			return state.isViewCompiler();
		}
		catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException
		        | InstantiationException e) {
			FileSystem.Log.logging(e);
			return false;
		}
	}
}
