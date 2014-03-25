package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalOrOr extends Lexical {
	@Override
	public String getTokenStr() {
		return "||";
	}
	
	@Override
	public boolean isLogicNext() {
		return true;
	}
	
	@Override
	public CScriptVarLink logicNext(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, CScriptVarLink a) throws Exception {
		lexicalAnalyzer.getNextToken();
		final boolean shortCircuit = a.var.getBool();
		CScriptVarLink b = lexicalAnalyzer.tk.condition(lexicalAnalyzer, shortCircuit ? false : execute, scopes);
		if (execute && !shortCircuit) {
			final CScriptVar newa = new ScriptInteger(a.var.getBool() ? 1 : 0);
			final CScriptVar newb = new ScriptInteger(b.var.getBool() ? 1 : 0);
			a = a.createLink(newa);
			b = b.createLink(newb);
			final CScriptVar res = a.var.mathsOp(b.var, Lexical.LEX_OR);
			a = a.createLink(res);
		}
		clean(b);
		return a;
	}
}