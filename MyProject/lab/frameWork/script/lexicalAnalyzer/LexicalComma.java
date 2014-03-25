package frameWork.script.lexicalAnalyzer;

public class LexicalComma extends Lexical {
	@Override
	public String getTokenStr() {
		return ",";
	}
	
	@Override
	public void factorBraceLeftEnd(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		lexicalAnalyzer.getNextToken();
	}
	
	@Override
	public void hasNextComma(final LexicalAnalyzer lexicalAnalyzer) {
		lexicalAnalyzer.getNextToken();
	}
	
	@Override
	public void isStatementNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		lexicalAnalyzer.getNextToken();
	}
	
	@Override
	public void isFactorNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		lexicalAnalyzer.getNextToken();
	}
}
