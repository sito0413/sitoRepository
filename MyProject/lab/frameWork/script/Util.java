package frameWork.script;

import java.util.Vector;

import frameWork.script.scriptVar.CScriptVar;

public abstract class Util {
	public static final String TINYJS_BLANK_DATA = "";
	public static final String TINYJS_TEMP_NAME = "";
	
	public static final String TINYJS_RETURN_VAR = "return";
	public static final String TINYJS_PROTOTYPE_CLASS = "prototype";
	
	public static void clean(final CScriptVarLink x) throws Exception {
		if ((x != null) && (!x.owned)) {
			x.close();
		}
	}
	
	public static boolean isWhitespace(final char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r');
	}
	
	public static boolean isAlpha(final char ch) {
		return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_');
	}
	
	public static boolean isNumeric(final char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	
	public static boolean isHexadecimal(final char ch) {
		return ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'f')) || ((ch >= 'A') && (ch <= 'F'));
	}
	
	public static CScriptVarLink findInScopes(final String childName, final Vector<CScriptVar> scopes) {
		for (int s = scopes.size() - 1; s >= 0; s--) {
			final CScriptVarLink v = scopes.get(s).findChild(childName);
			if (v != null) {
				return v;
			}
		}
		return null;
	}
}
