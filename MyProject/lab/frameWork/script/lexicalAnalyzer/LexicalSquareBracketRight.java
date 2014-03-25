package frameWork.script.lexicalAnalyzer;

public class LexicalSquareBracketRight extends Lexical {
	@Override
	public String getTokenStr() {
		return "]";
	}
	
	@Override
	public void isFactorNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		//NOP
	}
	
	@Override
	public boolean isSquareBracketRight() {
		return true;
	}
}
