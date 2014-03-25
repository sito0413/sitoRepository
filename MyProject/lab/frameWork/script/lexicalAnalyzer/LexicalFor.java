package frameWork.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.script.scriptVar.CScriptVar;

public class LexicalFor extends Lexical {
	@Override
	public String getTokenStr() {
		return "for";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return lexicalAnalyzer.tk.startForStatement(lexicalAnalyzer, execute, scopes);
	}
}
