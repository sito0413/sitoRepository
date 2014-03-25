package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptFunction;

public class LexicalFunction extends Lexical {
	@Override
	public String getTokenStr() {
		return "function";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink funcVar = parseFunctionDefinition(lexicalAnalyzer, scopes);
		if (execute) {
			if (funcVar.name.equals(TINYJS_TEMP_NAME)) {
				System.out.println("Functions defined at statement-level are meant to have a name");
			}
			else {
				scopes.lastElement().addChildNoDup(funcVar.name, funcVar.var);
			}
		}
		clean(funcVar);
		return execute;
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink funcVar = parseFunctionDefinition(lexicalAnalyzer, scopes);
		if (funcVar.name != TINYJS_TEMP_NAME) {
			System.out.print("Functions not defined at statement-level are not meant to have a name");
		}
		return funcVar;
	}
	
	private CScriptVarLink parseFunctionDefinition(final LexicalAnalyzer lexicalAnalyzer,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		String funcName = TINYJS_TEMP_NAME;
		if (lexicalAnalyzer.tk == Lexical.LEX_ID) {
			funcName = lexicalAnalyzer.tkStr;
			lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
		}
		final ScriptFunction function = new ScriptFunction();
		final CScriptVarLink funcVar = new CScriptVarLink(function, funcName);
		lexicalAnalyzer.tk.parseFunctionArguments(lexicalAnalyzer, function);
		final int funcBegin = lexicalAnalyzer.tokenStart;
		lexicalAnalyzer.tk.block(lexicalAnalyzer, false, scopes);
		final int lastCharIdx = lexicalAnalyzer.tokenLastEnd + 1;
		if (lastCharIdx < lexicalAnalyzer.dataEnd) {
			return null;
		}
		funcVar.var.data = lexicalAnalyzer.data.substring(funcBegin);
		return funcVar;
	}
}
