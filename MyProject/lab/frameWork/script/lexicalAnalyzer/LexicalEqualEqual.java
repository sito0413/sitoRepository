package frameWork.script.lexicalAnalyzer;

import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

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
