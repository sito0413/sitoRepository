package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class ElseScript extends Script {
	private IfScript ifScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.startWith("if")) {
			ifScript = new IfScript();
			return ifScript.create(scriptsBuffer);
		}
		return block(scriptsBuffer);
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		if (ifScript == null) {
			String value = "";
			for (final Script script : block) {
				value = script.execute(classMap, objectMap);
				switch ( value ) {
					case "break;" :
					case "continue" :
						return value;
				}
			}
			return value;
		}
		return ifScript.execute(classMap, objectMap);
	}
}
