package frameWork.script.lexicalAnalyzer;

public class LexicalBraceRight extends Lexical {
	@Override
	public String getTokenStr() {
		return "}";
	}
	
	@Override
	public void factorBraceLeftEnd(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		//NOP
	}
	
	@Override
	public int brackets(final int brackets) {
		return brackets - 1;
	}
	
	@Override
	public boolean isBraceRight() {
		return true;
	}
}
