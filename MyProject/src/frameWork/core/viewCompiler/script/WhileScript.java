package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class WhileScript extends Script {
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		return block(scriptsBuffer);
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		String value = "";
		while (Boolean.parseBoolean(statement.get(0).execute(classMap, objectMap))) {
			loop:
			for (final Script script : block) {
				value = script.execute(classMap, objectMap);
				switch ( value ) {
					case "break" :
						break loop;
					case "continue" :
						continue loop;
						
				}
			}
		}
		return value;
	}
}
