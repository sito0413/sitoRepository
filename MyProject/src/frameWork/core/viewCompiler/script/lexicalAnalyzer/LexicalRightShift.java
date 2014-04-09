package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalRightShift extends Lexical {
	
	@Override
	public String getTokenStr() {
		return ">>";
	}
	
	@Override
	public CScriptVarLink shift(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink expression) throws Exception {
		final CScriptVarLink a = expression;
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink b = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		clean(b);
		if (execute) {
			final int shift = b.var.getInt();
			a.var.setInt(a.var.getInt() >>> shift);
		}
		return a;
	}
}
