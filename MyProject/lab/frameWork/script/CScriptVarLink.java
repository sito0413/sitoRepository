package frameWork.script;

import static frameWork.script.Util.*;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptUndefined;

public class CScriptVarLink {
	public CScriptVar var;
	public final String name;
	public boolean owned;
	
	public CScriptVarLink nextSibling;
	public CScriptVarLink prevSibling;
	
	public CScriptVarLink(final CScriptVar var, final String name) {
		this.name = name;
		this.nextSibling = null;
		this.prevSibling = null;
		this.var = var.ref();
		this.owned = false;
	}
	
	public void replaceWith(final CScriptVar newVar) throws Exception {
		final CScriptVar oldVar = var;
		var = newVar.ref();
		oldVar.unref();
	}
	
	public void replaceWith(final CScriptVarLink newVar) throws Exception {
		if (newVar != null) {
			replaceWith(newVar.var);
		}
		else {
			replaceWith(new ScriptUndefined());
		}
	}
	
	public CScriptVarLink createLink(final CScriptVar newVar) throws Exception {
		if (owned) {
			return new CScriptVarLink(newVar, TINYJS_TEMP_NAME);
		}
		replaceWith(newVar);
		return this;
	}
	
	public void close() throws Exception {
		var.unref();
	}
}
