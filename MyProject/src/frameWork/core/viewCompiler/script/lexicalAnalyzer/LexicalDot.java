package frameWork.core.viewCompiler.script.lexicalAnalyzer;

public class LexicalDot extends Lexical {
	@Override
	public String getTokenStr() {
		return ".";
	}
	
	@Override
	public boolean isIdFactorNext() {
		return true;
	}
	
	@Override
	public boolean isDot() {
		return true;
	}
}
