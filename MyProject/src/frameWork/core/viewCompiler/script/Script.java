package frameWork.core.viewCompiler.script;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;

public abstract class Script<T extends Bytecode> {
	public abstract T execute(Scope scope) throws Exception;
	
	public abstract void print(int index);
	
	public void print(final int index, final String string) {
		for (int i = 0; i < index; i++) {
			System.out.print('\t');
		}
		System.out.println(string);
	}
	
	public void print() {
		print(0);
	}
}
