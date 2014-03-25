package frameWork.script.lexicalAnalyzer;

public class LexicalColon extends Lexical {
	@Override
	public String getTokenStr() {
		return ":";
	}
	
	@Override
	public void assertColon(final LexicalAnalyzer lexicalAnalyzer) {
		lexicalAnalyzer.getNextToken();
	}
	
}
