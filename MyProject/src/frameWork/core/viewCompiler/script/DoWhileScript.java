package frameWork.core.viewCompiler.script;

import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class DoWhileScript extends Script {
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		switch ( statement(scriptsBuffer) ) {
			case ';' :
				return scriptsBuffer.gotoNextChar();
			default :
				throw new Exception("Error ; at " + scriptsBuffer.getPosition());
		}
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		return statement.get(0).execute(classMap, objectMap);
	}
}
