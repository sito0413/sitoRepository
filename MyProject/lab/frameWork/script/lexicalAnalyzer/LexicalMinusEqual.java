package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;

public class LexicalMinusEqual extends Lexical {
	@Override
	public String getTokenStr() {
		return "-=";
	}
	
	@Override
	public CScriptVarLink base(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink ternary) throws Exception {
		CScriptVarLink lhs = ternary;
		if (execute && !lhs.owned) {
			if (lhs.name.length() > 0) {
				final CScriptVarLink realLhs = lexicalAnalyzer.root.addChildNoDup(lhs.name, lhs.var);
				clean(lhs);
				lhs = realLhs;
			}
			else {
				System.out.println("Trying to assign to an un-named type");
			}
		}
		
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink rhs = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		if (execute) {
			lhs.replaceWith(lhs.var.mathsOp(rhs.var, Lexical.LEX_MINUS));
		}
		clean(rhs);
		return lhs;
	}
}
