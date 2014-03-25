package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalTrue extends Lexical {
	@Override
	public String getTokenStr() {
		return "true";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return new CScriptVarLink(new ScriptInteger(1), TINYJS_TEMP_NAME);
	}
}
