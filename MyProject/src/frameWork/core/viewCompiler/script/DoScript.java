package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class DoScript extends Script {
	private DoWhileScript doWhileScript;
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			block(scriptsBuffer);
			if (scriptsBuffer.startWith("while")) {
				doWhileScript = new DoWhileScript();
				return doWhileScript.create(scriptsBuffer);
			}
			throw new Exception("Error while at " + scriptsBuffer.getPosition());
		}
		throw new Exception("Error { at " + scriptsBuffer.getPosition());
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		String value = "";
		do {
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
		while (Boolean.parseBoolean(doWhileScript.execute(classMap, objectMap)));
		return value;
	}
}
