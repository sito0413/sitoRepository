package frameWork.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalExclamationPoint extends Lexical {
	@Override
	public String getTokenStr() {
		return "!";
	}
	
	@Override
	public CScriptVarLink unary(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		CScriptVarLink a;
		lexicalAnalyzer.getNextToken();
		a = lexicalAnalyzer.tk.factor(lexicalAnalyzer, execute, scopes);
		if (execute) {
			final CScriptVar zero = new ScriptInteger(0);
			final CScriptVar res = a.var.mathsOp(zero, Lexical.LEX_EQUALEQUAL);
			a = a.createLink(res);
		}
		return a;
	}
}
