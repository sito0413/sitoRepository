package frameWork.core.viewCompiler.script.lexicalAnalyzer;

public class LexicalParenthesesRight extends Lexical {
	@Override
	public String getTokenStr() {
		return ")";
	}
	
	@Override
	public void hasNextComma(final LexicalAnalyzer lexicalAnalyzer) {
		//NOOP
	}
	
	@Override
	public boolean isNotParenthesesRight() {
		return false;
	}
	
}
