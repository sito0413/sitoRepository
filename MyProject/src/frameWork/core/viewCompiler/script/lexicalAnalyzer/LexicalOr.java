package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptInteger;

public class LexicalOr extends Lexical {
	@Override
	public String getTokenStr() {
		return "|";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da | db);
	}
	
	@Override
	public boolean isLogicNext() {
		return true;
	}
	
	@Override
	public CScriptVarLink logicNext(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, CScriptVarLink a) throws Exception {
		final CScriptVarLink b;
		lexicalAnalyzer.getNextToken();
		b = lexicalAnalyzer.tk.condition(lexicalAnalyzer, execute, scopes);
		if (execute) {
			final CScriptVar res = a.var.mathsOp(b.var, Lexical.LEX_OR);
			a = a.createLink(res);
		}
		clean(b);
		return a;
	}
}