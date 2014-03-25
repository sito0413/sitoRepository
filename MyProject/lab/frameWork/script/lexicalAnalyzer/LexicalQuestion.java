package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;

public class LexicalQuestion extends Lexical {
	@Override
	public String getTokenStr() {
		return "?";
	}
	
	@Override
	public CScriptVarLink ternary(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink logic) throws Exception {
		CScriptVarLink lhs = logic;
		final boolean noexec = false;
		lexicalAnalyzer.getNextToken();
		if (!execute) {
			clean(lhs);
			clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, noexec, scopes));
			lexicalAnalyzer.tk.assertColon(lexicalAnalyzer);
			clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, noexec, scopes));
		}
		else {
			final boolean first = lhs.var.getBool();
			clean(lhs);
			if (first) {
				lhs = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
				lexicalAnalyzer.tk.assertColon(lexicalAnalyzer);
				clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, noexec, scopes));
			}
			else {
				clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, noexec, scopes));
				lexicalAnalyzer.tk.assertColon(lexicalAnalyzer);
				lhs = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
			}
		}
		return lhs;
	}
	
	@Override
	public CScriptVarLink shift(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink expression) throws Exception {
		return expression;
	}
}
