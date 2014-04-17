package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class IfScript extends Script {
	private ElseScript elseScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		final char c = block(scriptsBuffer);
		if (scriptsBuffer.startWith("else")) {
			elseScript = new ElseScript();
			return elseScript.create(scriptsBuffer);
		}
		return c;
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		if (Boolean.parseBoolean(statement.get(0).execute(classMap, objectMap))) {
			String value = "";
			for (final Script script : block) {
				value = script.execute(classMap, objectMap);
				switch ( value ) {
					case "break" :
					case "continue" :
						return value;
				}
			}
			return value;
		}
		else if (elseScript != null) {
			return elseScript.execute(classMap, objectMap);
		}
		return "";
	}
}
