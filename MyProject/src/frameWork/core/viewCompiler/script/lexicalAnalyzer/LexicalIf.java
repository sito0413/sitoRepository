package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalIf extends Lexical {
	@Override
	public String getTokenStr() {
		return "if";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return lexicalAnalyzer.tk.startIfStatement(lexicalAnalyzer, execute, scopes);
	}
}
