package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalEqual extends Lexical {
	@Override
	public String getTokenStr() {
		return "=";
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
			lhs.replaceWith(rhs);
		}
		clean(rhs);
		return lhs;
	}
	
	@Override
	public void replaceWith(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink a) throws Exception {
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink var = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		if (execute) {
			a.replaceWith(var);
		}
		clean(var);
		
	}
}
