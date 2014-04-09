package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptString;

public class LexicalString extends Lexical {
	@Override
	public String getTokenStr() {
		return "STRING";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes));
		lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
		return execute;
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final ScriptString a = new ScriptString(lexicalAnalyzer.tkStr);
		lexicalAnalyzer.getNextToken();
		return new CScriptVarLink(a, TINYJS_TEMP_NAME);
		
	}
	
	@Override
	public void factorBraceLeftMatch(final LexicalAnalyzer lexicalAnalyzer) {
		lexicalAnalyzer.getNextToken();
	}
}