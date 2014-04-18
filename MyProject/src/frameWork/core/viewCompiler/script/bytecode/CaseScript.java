package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;

public class CaseScript extends Script implements Bytecode {
	final Bytecode label;
	
	public CaseScript() {
		this(null);
	}
	
	public CaseScript(final Bytecode label) {
		this.label = label;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return this;
	}
	
	@Override
	public String toString() {
		return "case";
	}
	
	public boolean equals(final Bytecode obj) {
		return label.equals(obj);
	}
	
}
