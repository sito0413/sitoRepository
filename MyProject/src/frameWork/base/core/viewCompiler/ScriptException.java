package frameWork.base.core.viewCompiler;

public class ScriptException extends Exception {
	
	public ScriptException(final Throwable e) {
		super(e);
	}
	
	public ScriptException(final String string) {
		super(string);
	}
	
	public static ScriptException illegalStateException() {
		return new ScriptException("IllegalState");
	}
	
	public static ScriptException illegalStateException(final Throwable e) {
		if (e != null) {
			return new ScriptException(e);
		}
		return new ScriptException("IllegalState");
	}
	
	public static ScriptException illegalStateException(final String string) {
		return new ScriptException(string);
	}
	
	public static ScriptException overflowException() {
		return new ScriptException("Overflow");
	}
}
