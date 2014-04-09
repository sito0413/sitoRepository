package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptObject;

public class LexicalBraceLeft extends Lexical {
	
	@Override
	public String getTokenStr() {
		return "{";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final ScriptObject contents = new ScriptObject();
		lexicalAnalyzer.getNextToken();
		while (!lexicalAnalyzer.tk.isBraceRight()) {
			final String id = lexicalAnalyzer.tkStr;
			lexicalAnalyzer.tk.factorBraceLeftMatch(lexicalAnalyzer);
			lexicalAnalyzer.tk.assertColon(lexicalAnalyzer);
			if (execute) {
				final CScriptVarLink a = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
				contents.addChild(id, a.var);
				clean(a);
			}
			lexicalAnalyzer.tk.factorBraceLeftEnd(lexicalAnalyzer);
		}
		lexicalAnalyzer.match(Lexical.LEX_BRACE_RIGHT);
		return new CScriptVarLink(contents, TINYJS_TEMP_NAME);
	}
	
	@Override
	public int brackets(final int brackets) {
		return brackets + 1;
	}
	
	@Override
	public boolean block(final LexicalAnalyzer lexicalAnalyzer, boolean execute, final Vector<CScriptVar> scopes)
	        throws Exception {
		lexicalAnalyzer.getNextToken();
		if (execute) {
			while (!lexicalAnalyzer.tk.isEndOfFile() && !lexicalAnalyzer.tk.isBraceRight()) {
				execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
			}
			lexicalAnalyzer.match(Lexical.LEX_BRACE_RIGHT);
		}
		else {
			int brackets = 1;
			while (!lexicalAnalyzer.tk.isEndOfFile() && (brackets != 0)) {
				brackets = lexicalAnalyzer.tk.brackets(brackets);
				lexicalAnalyzer.getNextToken();
			}
		}
		return execute;
	}
	
	@Override
	public void functionCallBlock(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.tk.block(lexicalAnalyzer, execute, scopes);
	}
}
