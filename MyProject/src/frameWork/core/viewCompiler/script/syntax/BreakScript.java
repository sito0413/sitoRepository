package frameWork.core.viewCompiler.script.syntax;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.ScriptException;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.SyntaxScript;

public class BreakScript extends SyntaxScript<Bytecode> implements Bytecode {
	
	public BreakScript(final String breakLabel) {
		super(breakLabel);
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
		return "break";
	}
	
	@Override
	public boolean isBreak() {
		return true;
	}
	
	@Override
	public boolean isContinue() {
		return false;
	}
	
	@Override
	public Object get() {
		return label;
	}
}
