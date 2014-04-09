package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;

public class LexicalSemicolon extends Lexical {
	@Override
	public String getTokenStr() {
		return ";";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return execute;
	}
	
	@Override
	public boolean isNotSemicolon() {
		return false;
	}
	
	@Override
	public void isStatementNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		//NOP
	}
	
	@Override
	public CScriptVarLink isStatementReturnNext(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		return null;
	}
	
	@Override
	public void assertSemicolon(final LexicalAnalyzer lexicalAnalyzer) {
		lexicalAnalyzer.getNextToken();
	}
}
