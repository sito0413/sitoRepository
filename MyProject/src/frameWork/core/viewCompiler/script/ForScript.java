package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class ForScript extends Script {
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		statement(scriptsBuffer);
		return block(scriptsBuffer);
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		String value = "";
		if (statement.size() == 3) {
			statement.get(0).execute(classMap, objectMap);
			while (Boolean.parseBoolean(statement.get(1).execute(classMap, objectMap))) {
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
				statement.get(2).execute(classMap, objectMap);
			}
		}
		else {
			statement.get(0).execute(classMap, objectMap);
			statement.get(1).execute(classMap, objectMap);
			
			@SuppressWarnings("rawtypes")
			final Iterable iterable = (Iterable) objectMap.get(statement.get(1).toString());
			for (final Object object : iterable) {
				objectMap.put(statement.get(0).toString(), object);
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
		}
		return value;
	}
}
