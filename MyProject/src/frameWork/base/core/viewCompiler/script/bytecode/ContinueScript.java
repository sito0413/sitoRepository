package frameWork.base.core.viewCompiler.script.bytecode;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.Script;

public class ContinueScript extends Script<Bytecode> implements Bytecode {
	
	public ContinueScript(final String contineLabel) {
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
