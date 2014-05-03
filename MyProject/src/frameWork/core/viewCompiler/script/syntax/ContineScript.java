package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.SyntaxScript;

public class ContineScript extends SyntaxScript<Bytecode> implements Bytecode {
	
	public ContineScript(final String contineLabel) {
		super(contineLabel);
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		return this;
	}
	
	@Override
	public String toString() {
		return "contine";
	}
	
	@Override
	public boolean isBreak() {
		return false;
	}
	
	@Override
	public boolean isContinue() {
		return true;
	}
	
	@Override
	public Object get() {
		return label;
	}
}
