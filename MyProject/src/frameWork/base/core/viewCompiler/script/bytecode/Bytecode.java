package frameWork.base.core.viewCompiler.script.bytecode;

public interface Bytecode {
	boolean isBreak();
	
	boolean isContinue();
	
	Object get();
}
