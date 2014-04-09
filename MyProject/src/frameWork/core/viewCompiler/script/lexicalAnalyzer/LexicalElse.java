package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalElse extends Lexical {
	@Override
	public String getTokenStr() {
		return "else";
	}
	
	@Override
	public boolean startIfElseStatement(final LexicalAnalyzer lexicalAnalyzer, boolean execute, boolean noexecute,
	        final Vector<CScriptVar> scopes, final boolean cond) throws Exception {
		lexicalAnalyzer.getNextToken();
		if (cond) {
			noexecute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, noexecute, scopes);
		}
		else {
			execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
		}
		return execute;
	}
}
