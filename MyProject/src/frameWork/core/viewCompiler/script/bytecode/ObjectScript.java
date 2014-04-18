package frameWork.core.viewCompiler.script.bytecode;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;

public class ObjectScript extends Script implements Bytecode {
	public final Class<?> type;
	public Object object;
	
	public ObjectScript(final Class<?> type, final Object object) {
		this.type = type;
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "_";
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
}
