package frameWork.script.lexicalAnalyzer;

import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptDouble;
import frameWork.script.scriptVar.ScriptInteger;

public class LexicalSlash extends Lexical {
	
	@Override
	public String getTokenStr() {
		return "/";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da / db);
	}
	
	@Override
	public CScriptVar mathsOp(final double da, final double db) {
		return new ScriptDouble(da / db);
	}
	
	@Override
	public boolean isTermNext() {
		return true;
	}
}
