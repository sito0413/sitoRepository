package frameWork.script.lexicalAnalyzer;

import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalPercent extends Lexical {
	@Override
	public String getTokenStr() {
		return "%";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da % db);
	}
	
	@Override
	public boolean isTermNext() {
		return true;
	}
}
