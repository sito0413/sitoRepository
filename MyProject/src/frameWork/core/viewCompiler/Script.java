package frameWork.core.viewCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Script {
	
	protected final List<Script> block;
	protected final List<Script> statement;
	
	public Script() {
		this.block = new ArrayList<>();
		this.statement = new ArrayList<>();
	}
	
	public char syntax(final ScriptsBuffer scriptsBuffer) throws Exception {
		final Script subScript = scriptsBuffer.getSyntaxToken();
		block.add(subScript);
		final char c = subScript.create(scriptsBuffer);
		return c;
	}
	
	protected char statement(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '(') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == ')') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				statement.add(scriptsBuffer.getStatementToken());
				switch ( scriptsBuffer.getChar() ) {
					case ')' :
						return scriptsBuffer.gotoNextChar();
					default :
						scriptsBuffer.gotoNextChar();
						break;
				}
			}
			throw new Exception("Error ) at " + scriptsBuffer.getPosition());
		}
		throw new Exception("Error ( at " + scriptsBuffer.getPosition());
	}
	
	protected char block(final ScriptsBuffer scriptsBuffer) throws Exception {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			while (scriptsBuffer.hasRemaining()) {
				scriptsBuffer.skip();
				switch ( syntax(scriptsBuffer) ) {
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
		final char c = syntax(scriptsBuffer);
		switch ( c ) {
			case ';' :
			case '}' :
				return scriptsBuffer.gotoNextChar();
			default :
				return c;
		}
	}
	
	public abstract char create(final ScriptsBuffer scriptsBuffer) throws Exception;
	
	public abstract String execute(Map<String, Class<?>> classMap, Map<String, Object> objectMap) throws Exception;
	
	public boolean isMethod() {
		return false;
	}
}
