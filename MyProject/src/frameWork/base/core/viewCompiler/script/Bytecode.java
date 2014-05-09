package frameWork.base.core.viewCompiler.script;

public interface Bytecode {
	boolean isBreak();
	
	boolean isContinue();
	
	Object get();
}
