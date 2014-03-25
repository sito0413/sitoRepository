package frameWork.script.lexicalAnalyzer;

import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalAngleBracketsRight extends Lexical {
	@Override
	public String getTokenStr() {
		return ">";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da > db ? 1 : 0);
	}
	
	@Override
	public CScriptVar mathsOp(final double da, final double db) {
		return new ScriptInteger(da > db ? 1 : 0);
	}
	
	@Override
	public boolean isConditionNext() {
		return true;
	}
}
