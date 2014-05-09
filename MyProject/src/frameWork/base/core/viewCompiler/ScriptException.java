package frameWork.core.viewCompiler;

public class ScriptException extends Exception {
	
	public ScriptException(final Throwable e) {
		super(e);
	}
	
	public ScriptException(final String string) {
		super(string);
	}
	
	public static ScriptException IllegalStateException() {
		return new ScriptException("IllegalState");
	}
	
	public static ScriptException IllegalStateException(final Throwable e) {
		return new ScriptException(e);
	}
	
	public static ScriptException IllegalStateException(final String string) {
		return new ScriptException(string);
	}
	
	public static ScriptException overflowException() {
		return new ScriptException("overflow");
	}
	
}
