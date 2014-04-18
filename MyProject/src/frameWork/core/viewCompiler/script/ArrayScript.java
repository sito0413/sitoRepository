package frameWork.core.viewCompiler.script;

import java.util.Deque;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public class ArrayScript extends Script {
	final Script syntaxToken;
	
	public ArrayScript(final Script syntaxToken) {
		this.syntaxToken = syntaxToken;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
	@Override
	public boolean isArray() {
		return true;
	}
	
	public void execute1(final Deque<Script> buffer, final Deque<Script> org, final Scope scope) {
		buffer.addLast(this);
	}
}
