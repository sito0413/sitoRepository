package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class OperatorScript extends Script {
	private final String operator;
	
	public OperatorScript(final String operator) {
		this.operator = operator;
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		return operator;
	}
	
	@Override
	public String toString() {
		return operator;
	}
}
