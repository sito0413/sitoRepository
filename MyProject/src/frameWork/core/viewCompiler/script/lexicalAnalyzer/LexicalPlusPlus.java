package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptInteger;

public class LexicalPlusPlus extends Lexical {
	@Override
	public String getTokenStr() {
		return "++";
	}
	
	@Override
	public boolean isExpressionNext() {
		return true;
	}
	
	@Override
	public CScriptVarLink expression(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, CScriptVarLink a) throws Exception {
		if (execute) {
			final CScriptVar one = new ScriptInteger(1);
			final CScriptVar res = a.var.mathsOp(one, Lexical.LEX_PLUS);
			final CScriptVarLink oldValue = new CScriptVarLink(a.var, TINYJS_TEMP_NAME);
			// in-place add/subtract
			a.replaceWith(res);
			clean(a);
			a = oldValue;
		}
		return a;
	}
	
}
