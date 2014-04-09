package frameWork.core.viewCompiler.script.scriptVar;

import frameWork.core.viewCompiler.script.CScriptVarLink;

public class ScriptFunction extends CScriptVar {
	public ScriptFunction() {
		flags = SCRIPTVAR_FUNCTION;
	}
	
	public CScriptVar getParameter(final String name) {
		return findChildOrCreate(name).var;
	}
	
	public void removeLink(final CScriptVarLink link) throws Exception {
		if (link == null) {
			return;
		}
		if (link.nextSibling != null) {
			link.nextSibling.prevSibling = link.prevSibling;
		}
		if (link.prevSibling != null) {
			link.prevSibling.nextSibling = link.nextSibling;
		}
		if (lastChild == link) {
			lastChild = link.prevSibling;
		}
		if (firstChild == link) {
			firstChild = link.nextSibling;
		}
		link.close();
	}
	
	public void close() throws Exception {
		removeAllChildren();
	}
}
