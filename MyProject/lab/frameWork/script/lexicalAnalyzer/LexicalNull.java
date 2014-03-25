package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptNull;

public class LexicalNull extends Lexical {
	@Override
	public String getTokenStr() {
		return "null";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		return new CScriptVarLink(new ScriptNull(), TINYJS_TEMP_NAME);
	}
}
