package frameWork.script.scriptVar;

public class ScriptInteger extends CScriptVar {
	public ScriptInteger(final String varData) {
		flags = SCRIPTVAR_INTEGER;
		intData = Integer.parseInt(varData);
	}
	
	public ScriptInteger(final int val) {
		flags = SCRIPTVAR_INTEGER;
		intData = val;
	}
}
