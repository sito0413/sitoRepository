package frameWork.core.viewCompiler.script;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class ExpressionScript extends Script {
	protected final Deque<Script> token = new ArrayDeque<>();
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		callMethod(classMap, objectMap);
		return "";
	}
	
	private void callMethod(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) {
		final Deque<Script> buffer = new ArrayDeque<>();
		for (final Script script : token.toArray(new Script[token.size()])) {
			if (script.isMethod()) {
				if (script.toString().startsWith(".")) {
					String string = "";
					do {
						string += buffer.pollLast().toString();
					}
					while (string.startsWith("."));
					System.out.println(string + script);
				}
				else if (!buffer.isEmpty() && buffer.peekLast().equals("new")) {
					System.out.println(script);
				}
			}
			else {
				buffer.addLast(script);
			}
		}
	}
	
	public void addToken(final Script script) {
		token.addLast(script);
	}
}
