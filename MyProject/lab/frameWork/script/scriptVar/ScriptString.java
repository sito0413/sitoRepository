package frameWork.script.scriptVar;

public class ScriptString extends CScriptVar {
	public ScriptString(final String varData) {
		flags = SCRIPTVAR_STRING;
		data = varData;
	}
}
