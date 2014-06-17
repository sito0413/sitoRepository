package frameWork.base.core.viewCompiler.script.bytecode;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.Script;

public class BreakScript extends Script<Bytecode> implements Bytecode {
	
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
