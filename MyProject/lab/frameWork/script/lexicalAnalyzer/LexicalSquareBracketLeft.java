package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptArray;

public class LexicalSquareBracketLeft extends Lexical {
	@Override
	public String getTokenStr() {
		return "[";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final ScriptArray contents = new ScriptArray();
		lexicalAnalyzer.getNextToken();
		int idx = 0;
		while (!lexicalAnalyzer.tk.isSquareBracketRight()) {
			if (execute) {
				final CScriptVarLink a = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
				contents.addChild(String.valueOf(idx), a.var);
				clean(a);
			}
			lexicalAnalyzer.tk.isFactorNext(lexicalAnalyzer);
			idx++;
		}
		lexicalAnalyzer.getNextToken();
		return new CScriptVarLink(contents, TINYJS_TEMP_NAME);
	}
	
	@Override
	public boolean isIdFactorNext() {
		return true;
	}
	
	@Override
	public boolean isSquareBracketLeft() {
		return true;
	}
}
