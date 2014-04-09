package frameWork.core.viewCompiler.script.scriptVar;

public class ScriptDouble extends CScriptVar {
	public ScriptDouble(final String varData) {
		flags = SCRIPTVAR_DOUBLE;
		doubleData = Double.parseDouble(varData);
	}
	
	public ScriptDouble(final double val) {
		flags = SCRIPTVAR_DOUBLE;
		doubleData = val;
	}
}
