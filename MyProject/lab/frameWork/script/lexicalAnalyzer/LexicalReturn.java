package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;

public class LexicalReturn extends Lexical {
	@Override
	public String getTokenStr() {
		return "return";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, boolean execute, final Vector<CScriptVar> scopes)
	        throws Exception {
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink result = lexicalAnalyzer.tk.isStatementReturnNext(lexicalAnalyzer, execute, scopes);
		if (execute) {
			final CScriptVarLink resultVar = scopes.lastElement().findChild(TINYJS_RETURN_VAR);
			if (resultVar != null) {
				resultVar.replaceWith(result);
			}
			else {
				System.out.println("RETURN statement, but not in a function.");
			}
			execute = false;
		}
		clean(result);
		lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
		return execute;
	}
}
