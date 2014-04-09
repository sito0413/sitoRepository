package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptObject;
import frameWork.core.viewCompiler.script.scriptVar.ScriptUndefined;

public class LexicalNew extends Lexical {
	@Override
	public String getTokenStr() {
		return "new";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		final String className = lexicalAnalyzer.tkStr;
		if (execute) {
			final CScriptVarLink objClassOrFunc = findInScopes(className, scopes);
			if (objClassOrFunc == null) {
				System.out.print(className + " is not a valid class name");
				return new CScriptVarLink(new ScriptUndefined(), TINYJS_TEMP_NAME);
			}
			lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
			final ScriptObject obj = new ScriptObject();
			final CScriptVarLink objLink = new CScriptVarLink(obj, TINYJS_TEMP_NAME);
			if (objClassOrFunc.var.isFunction()) {
				clean(lexicalAnalyzer.tk.functionCall(lexicalAnalyzer, execute, objClassOrFunc, obj, scopes));
			}
			else {
				obj.addChild(TINYJS_PROTOTYPE_CLASS, objClassOrFunc.var);
				lexicalAnalyzer.tk.newFactor(lexicalAnalyzer);
			}
			return objLink;
		}
		lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
		lexicalAnalyzer.tk.newFactor(lexicalAnalyzer);
		return super.factor(lexicalAnalyzer, execute, scopes);
	}
}
