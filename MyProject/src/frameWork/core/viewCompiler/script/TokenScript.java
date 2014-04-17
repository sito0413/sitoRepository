package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class TokenScript extends Script {
	private final String expression;
	
	public TokenScript(final String expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return expression;
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		return expression;
	}
}
