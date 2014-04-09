package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalVar extends Lexical {
	@Override
	public String getTokenStr() {
		return "var";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		while (lexicalAnalyzer.tk.isNotSemicolon()) {
			CScriptVarLink a = null;
			if (execute) {
				a = scopes.lastElement().findChildOrCreate(lexicalAnalyzer.tkStr);
			}
			lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
			while (lexicalAnalyzer.tk.isDot()) {
				lexicalAnalyzer.getNextToken();
				if (execute) {
					final CScriptVarLink lastA = a;
					a = lastA.var.findChildOrCreate(lexicalAnalyzer.tkStr);
				}
				lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
			}
			lexicalAnalyzer.tk.replaceWith(lexicalAnalyzer, execute, scopes, a);
			lexicalAnalyzer.tk.isStatementNext(lexicalAnalyzer);
		}
		lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
		return execute;
	}
}
