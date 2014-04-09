package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalWhile extends Lexical {
	@Override
	public String getTokenStr() {
		return "while";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return lexicalAnalyzer.tk.startWhileStatement(lexicalAnalyzer, execute, scopes);
	}
}
