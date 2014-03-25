package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptDouble;
import frameWork.script.scriptVar.ScriptInteger;
import frameWork.script.scriptVar.ScriptString;

public class LexicalPlus extends Lexical {
	@Override
	public String getTokenStr() {
		return "+";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da + db);
	}
	
	@Override
	public CScriptVar mathsOp(final double da, final double db) {
		return new ScriptDouble(da + db);
	}
	
	@Override
	public CScriptVar mathsOp(final String da, final String db) {
		return new ScriptString(da + db);
	}
	
	@Override
	public boolean isExpressionNext() {
		return true;
	}
	
	@Override
	public CScriptVarLink expression(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, CScriptVarLink a) throws Exception {
		final CScriptVarLink unary = lexicalAnalyzer.tk.unary(lexicalAnalyzer, execute, scopes);
		final CScriptVarLink b = lexicalAnalyzer.tk.term(lexicalAnalyzer, execute, scopes, unary);
		if (execute) {
			// not in-place, so just replace
			final CScriptVar res = a.var.mathsOp(b.var, this);
			a = a.createLink(res);
		}
		clean(b);
		return a;
	}
}
