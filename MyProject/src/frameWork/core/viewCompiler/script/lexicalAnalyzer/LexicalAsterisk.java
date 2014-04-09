package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptDouble;
import frameWork.core.viewCompiler.script.scriptVar.ScriptInteger;

public class LexicalAsterisk extends Lexical {
	
	@Override
	public String getTokenStr() {
		return "*";
	}
	
	@Override
	public CScriptVar mathsOp(final int da, final int db) {
		return new ScriptInteger(da * db);
	}
	
	@Override
	public CScriptVar mathsOp(final double da, final double db) {
		return new ScriptDouble(da * db);
	}
	
	@Override
	public boolean isTermNext() {
		return true;
	}
}