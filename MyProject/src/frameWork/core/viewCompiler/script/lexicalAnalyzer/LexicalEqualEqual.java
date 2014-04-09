package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptInteger;

public class LexicalEqualEqual extends Lexical {
	@Override
	public String getTokenStr() {
		return "==";
	}
	
	@Override
	public CScriptVar mathsOp() {
		return new ScriptInteger(1);
	}
	
	@Override
	public boolean isConditionNext() {
		return true;
	}
}
