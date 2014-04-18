package frameWork.core.viewCompiler.script.syntax;

import java.util.ArrayList;
import java.util.List;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public abstract class SyntaxScript extends Script {
	protected final List<ExpressionScript> statement = new ArrayList<>();
	protected final List<SyntaxScript> block = new ArrayList<>();
	
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		scriptsBuffer.statement(statement);
		return block(scriptsBuffer);
	}
	
	protected final void add(final SyntaxScript subScript) {
		block.add(subScript);
	}
	
	protected char block(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				scriptsBuffer.skip();
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				add(subScript);
				switch ( subScript.create(scriptsBuffer) ) {
					case '}' :
						return scriptsBuffer.gotoNextChar();
					case ';' :
						scriptsBuffer.gotoNextChar();
						break;
					default :
						break;
				}
			}
			throw new Exception("Error } at " + scriptsBuffer.getPosition());
		}
		final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
		add(subScript);
		final char c = subScript.create(scriptsBuffer);
		switch ( c ) {
			case ';' :
			case '}' :
				return scriptsBuffer.gotoNextChar();
			default :
				return c;
		}
	}
}
