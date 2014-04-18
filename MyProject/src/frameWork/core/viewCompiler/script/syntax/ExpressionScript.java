package frameWork.core.viewCompiler.script.syntax;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;
import frameWork.core.viewCompiler.script.ArrayScript;
import frameWork.core.viewCompiler.script.MethodScript;
import frameWork.core.viewCompiler.script.OperatorScript;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.CaseScript;

public class ExpressionScript extends SyntaxScript {
	protected final Deque<Script> token;
	
	public ExpressionScript(final Deque<Script> token) {
		this.token = token;
	}
	
	public ExpressionScript(final List<ExpressionScript> statement) {
		this(new ArrayDeque<Script>());
		token.addAll(statement);
	}
	
	@Override
	public String toString() {
		return token.toString();
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return scriptsBuffer.getChar();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		if (!token.isEmpty()) {
			switch ( token.peekFirst().toString() ) {
				case "case" :
					final Script caseScript = token.pollFirst();
					final Bytecode label = token.peekFirst().execute(scope);
					token.addFirst(caseScript);
					return new CaseScript(label);
					
				default :
					final Deque<Script> buffer = new ArrayDeque<>(token);
					callSyntax(buffer, scope);
					callMethod(buffer, scope);
					return execute1(buffer, scope);
			}
		}
		return null;
	}
	
	private void callSyntax(final Deque<Script> org, final Scope scope) {
		System.out.println(org);
		//		final Deque<Script> buffer = new ArrayDeque<>();
		//		while (!org.isEmpty()) {
		//			final Script script = org.pollFirst();
		//			if (script.isSyntax()) {
		//				//				buffer.
		//			}
		//			else {
		//				buffer.addLast(script);
		//			}
		//		}
	}
	
	private void callMethod(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		for (final Script script : org) {
			if (script.isMethod()) {
				if (script.toString().startsWith(".")) {
					final List<String> list = new ArrayList<>();
					while (true) {
						final String s = buffer.pollLast().toString();
						if (s.startsWith(".")) {
							list.add(0, s.substring(1));
							continue;
						}
						list.add(0, s);
						break;
					}
					if (!buffer.isEmpty() && buffer.peekLast().equals("new")) {
						String path = "";
						for (int i = 0; i < list.size(); i++) {
							path += list.get(i) + ".";
						}
						buffer.addLast(((MethodScript) script).callConstructor(
						        scope.getImport(path + script.toString()), scope));
					}
					else {
						Object object = scope.get(list.get(0)).object;
						for (int i = 1; i < list.size(); i++) {
							object = object.getClass().getField(list.get(i)).get(object);
						}
						buffer.addLast(((MethodScript) script).callMethod(object, scope));
						//TODO new fullpath
					}
				}
				else if (!buffer.isEmpty() && buffer.peekLast().equals("new")) {
					buffer.addLast(((MethodScript) script).callConstructor(scope.getImport(script.toString()), scope));
				}
				else {
					throw new Exception("callMethod");
				}
			}
			else {
				buffer.addLast(script);
			}
		}
	}
	
	private Bytecode execute1(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute1(buffer, org, scope);
			}
			else if (script.isArray()) {
				((ArrayScript) script).execute1(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}//．　(パラメータのリスト)
		return execute2(buffer, scope);
	}
	
	private Bytecode execute2(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			//TODO new ?
			final Script script = org.pollFirst();
			buffer.addLast(script);
		}
		return execute3(buffer, scope);
	}
	
	private Bytecode execute3(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute4(buffer, scope);
	}
	
	private Bytecode execute4(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute5(buffer, scope);
	}
	
	private Bytecode execute5(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute6(buffer, scope);
	}
	
	private Bytecode execute6(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute7(buffer, scope);
	}
	
	private Bytecode execute7(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute8(buffer, scope);
	}
	
	private Bytecode execute8(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute9(buffer, scope);
	}
	
	private Bytecode execute9(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return execute10(buffer, scope);
	}
	
	private Bytecode execute10(final Deque<Script> org, final Scope scope) throws Exception {
		final Deque<Script> buffer = new ArrayDeque<>();
		while (!org.isEmpty()) {
			final Script script = org.pollFirst();
			if (script.isOperator()) {
				((OperatorScript) script).execute3(buffer, org, scope);
			}
			else {
				buffer.addLast(script);
			}
		}
		return buffer.getFirst().execute(scope);
	}
	
}
