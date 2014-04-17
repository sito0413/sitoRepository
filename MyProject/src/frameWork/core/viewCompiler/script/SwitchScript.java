package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class SwitchScript extends Script {
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		switch ( statement(scriptsBuffer) ) {
			case '{' :
				return block(scriptsBuffer);
			default :
				throw new Exception("Error { at " + scriptsBuffer.getPosition());
		}
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		final String label = statement.get(0).execute(classMap, objectMap);
		for (final Script script : block) {
			if (label.equals(script.toString())) {
				if (script.execute(classMap, objectMap).equals("break")) {
					return "";
				}
			}
			else if ("default".equals(script.toString())) {
				script.execute(classMap, objectMap);
				return "";
			}
		}
		return "";
	}
}
