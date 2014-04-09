package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptInteger;

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
