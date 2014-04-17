package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class StringScript extends Script {
	final String token;
	
	public StringScript(final String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		return token;
	}
}
