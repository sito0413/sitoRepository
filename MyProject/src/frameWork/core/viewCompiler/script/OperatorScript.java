package frameWork.core.viewCompiler.script;

import java.util.Deque;

import frameWork.core.viewCompiler.Scope;
import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.script.bytecode.Bytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;

public class OperatorScript extends Script {
	private final String operator;
	
	public OperatorScript(final String operator) {
		this.operator = operator;
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws Exception {
		return null;
	}
	
	@Override
	public String toString() {
		return operator;
	}
	
	@Override
	public boolean isOperator() {
		return true;
	}
	
	public void execute1(final Deque<Script> buffer, final Deque<Script> org, final Scope scope) throws Exception {
		switch ( toString() ) {
			case "++" :
				if (!buffer.isEmpty()) {
					final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
					buffer.addLast(new NumericScript(objectScript.object.toString()));
					if (objectScript.type.isAssignableFrom(Double.class)) {
						objectScript.object = ((Double) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Float.class)) {
						objectScript.object = ((Float) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Long.class)) {
						objectScript.object = ((Long) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Integer.class)) {
						objectScript.object = ((Integer) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Short.class)) {
						objectScript.object = ((Short) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Byte.class)) {
						objectScript.object = ((Byte) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Character.class)) {
						objectScript.object = ((Character) objectScript.object) + 1;
					}
					else {
						throw new Exception("execute1");
					}
				}
				else if (!org.isEmpty()) {
					final ObjectScript objectScript = scope.get(org.pollFirst().toString());
					buffer.addLast(new NumericScript(objectScript.object.toString()));
					if (objectScript.type.isAssignableFrom(Double.class)) {
						objectScript.object = ((Double) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Float.class)) {
						objectScript.object = ((Float) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Long.class)) {
						objectScript.object = ((Long) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Integer.class)) {
						objectScript.object = ((Integer) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Short.class)) {
						objectScript.object = ((Short) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Byte.class)) {
						objectScript.object = ((Byte) objectScript.object) + 1;
					}
					else if (objectScript.type.isAssignableFrom(Character.class)) {
						objectScript.object = ((Character) objectScript.object) + 1;
					}
					else {
						throw new Exception("execute1");
					}
				}
				else {
					throw new Exception("execute1");
				}
				
				break;
			
			case "--" :
				if (!buffer.isEmpty()) {
					final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
					buffer.addLast(new NumericScript(objectScript.object.toString()));
					if (objectScript.type.isAssignableFrom(Double.class)) {
						objectScript.object = ((Double) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Float.class)) {
						objectScript.object = ((Float) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Long.class)) {
						objectScript.object = ((Long) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Integer.class)) {
						objectScript.object = ((Integer) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Short.class)) {
						objectScript.object = ((Short) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Byte.class)) {
						objectScript.object = ((Byte) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Character.class)) {
						objectScript.object = ((Character) objectScript.object) - 1;
					}
					else {
						throw new Exception("execute1");
					}
				}
				else if (!org.isEmpty()) {
					final ObjectScript objectScript = scope.get(org.pollFirst().toString());
					buffer.addLast(new NumericScript(objectScript.object.toString()));
					if (objectScript.type.isAssignableFrom(Double.class)) {
						objectScript.object = ((Double) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Float.class)) {
						objectScript.object = ((Float) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Long.class)) {
						objectScript.object = ((Long) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Integer.class)) {
						objectScript.object = ((Integer) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Short.class)) {
						objectScript.object = ((Short) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Byte.class)) {
						objectScript.object = ((Byte) objectScript.object) - 1;
					}
					else if (objectScript.type.isAssignableFrom(Character.class)) {
						objectScript.object = ((Character) objectScript.object) - 1;
					}
					else {
						throw new Exception("execute1");
					}
				}
				else {
					throw new Exception("execute1");
				}
				
				break;
			
			case "+" :
				if (!((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty())) {
					buffer.addLast(this);
				}
				break;
			
			case "-" :
				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
					final Script script = buffer.pollLast();
					if (script.isNumeric()) {
						buffer.addLast(new NumericScript("-" + script.toString()));
					}
					else {
						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
						buffer.addLast(new NumericScript(objectScript.object.toString()));
						if (objectScript.type.isAssignableFrom(Double.class)) {
							objectScript.object = -((Double) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Float.class)) {
							objectScript.object = -((Float) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Long.class)) {
							objectScript.object = -((Long) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Integer.class)) {
							objectScript.object = -((Integer) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Short.class)) {
							objectScript.object = -((Short) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Byte.class)) {
							objectScript.object = -((Byte) objectScript.object);
						}
						else if (objectScript.type.isAssignableFrom(Character.class)) {
							objectScript.object = -((Character) objectScript.object);
						}
						else {
							throw new Exception("execute1");
						}
					}
				}
				else {
					buffer.addLast(this);
				}
				break;
			case "~" :
				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
					final Script script = buffer.pollLast();
					if (script.isNumeric()) {
						buffer.addLast(new NumericScript(Integer.toString(~Integer.parseInt(script.toString()))));
					}
					else {
						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
						if (objectScript.type.isAssignableFrom(Long.class)) {
							objectScript.object = ~((Long) objectScript.object);
							buffer.addLast(new NumericScript(objectScript.object.toString()));
						}
						else if (objectScript.type.isAssignableFrom(Integer.class)) {
							objectScript.object = ~((Integer) objectScript.object);
							buffer.addLast(new NumericScript(objectScript.object.toString()));
						}
						else if (objectScript.type.isAssignableFrom(Short.class)) {
							objectScript.object = ~((Short) objectScript.object);
							buffer.addLast(new NumericScript(objectScript.object.toString()));
						}
						else if (objectScript.type.isAssignableFrom(Byte.class)) {
							objectScript.object = ~((Byte) objectScript.object);
							buffer.addLast(new NumericScript(objectScript.object.toString()));
						}
						else if (objectScript.type.isAssignableFrom(Character.class)) {
							objectScript.object = ~((Character) objectScript.object);
							buffer.addLast(new NumericScript(objectScript.object.toString()));
						}
						else {
							throw new Exception("execute1");
						}
					}
				}
				else {
					throw new Exception("execute1");
				}
				break;
			case "!" :
				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
					final Script script = buffer.pollLast();
					if (script.isBoolean()) {
						buffer.addLast(new TokenScript(Boolean.toString(!Boolean.parseBoolean(script.toString()))));
					}
					else {
						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
						if (objectScript.type.isAssignableFrom(Boolean.class)) {
							objectScript.object = !((Boolean) objectScript.object);
							buffer.addLast(new TokenScript(objectScript.object.toString()));
						}
						else {
							throw new Exception("execute1");
						}
					}
				}
				else {
					throw new Exception("execute1");
				}
				break;
			default :
				buffer.addLast(this);
				break;
		}
		
	}
	
	public void execute3(final Deque<Script> buffer, final Deque<Script> org, final Scope scope) throws Exception {
		//		switch ( toString() ) {
		//			case "*" :
		//				if (!buffer.isEmpty()&&!org.isEmpty()) {
		//					if (buffer.peekLast().isNumeric()) {
		//						buffer.addLast(new NumericScript(Integer.toString(~Integer.parseInt(script.toString()))));
		//					}
		//					else {
		//					}
		//
		//
		//					final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
		//					buffer.addLast(new NumericScript(objectScript.object.toString()));
		//					if (objectScript.type.isAssignableFrom(Double.class)) {
		//						objectScript.object = ((Double) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Float.class)) {
		//						objectScript.object = ((Float) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Long.class)) {
		//						objectScript.object = ((Long) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//						objectScript.object = ((Integer) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Short.class)) {
		//						objectScript.object = ((Short) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//						objectScript.object = ((Byte) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Character.class)) {
		//						objectScript.object = ((Character) objectScript.object) + 1;
		//					}
		//					else {
		//						throw new Exception("execute1");
		//					}
		//				}
		//				else if () {
		//					final ObjectScript objectScript = scope.get(org.pollFirst().toString());
		//					buffer.addLast(new NumericScript(objectScript.object.toString()));
		//					if (objectScript.type.isAssignableFrom(Double.class)) {
		//						objectScript.object = ((Double) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Float.class)) {
		//						objectScript.object = ((Float) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Long.class)) {
		//						objectScript.object = ((Long) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//						objectScript.object = ((Integer) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Short.class)) {
		//						objectScript.object = ((Short) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//						objectScript.object = ((Byte) objectScript.object) + 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Character.class)) {
		//						objectScript.object = ((Character) objectScript.object) + 1;
		//					}
		//					else {
		//						throw new Exception("execute1");
		//					}
		//				}
		//				else {
		//					throw new Exception("execute1");
		//				}
		//				break;
		//
		//			case "--" :
		//				if (!buffer.isEmpty()) {
		//					final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
		//					buffer.addLast(new NumericScript(objectScript.object.toString()));
		//					if (objectScript.type.isAssignableFrom(Double.class)) {
		//						objectScript.object = ((Double) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Float.class)) {
		//						objectScript.object = ((Float) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Long.class)) {
		//						objectScript.object = ((Long) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//						objectScript.object = ((Integer) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Short.class)) {
		//						objectScript.object = ((Short) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//						objectScript.object = ((Byte) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Character.class)) {
		//						objectScript.object = ((Character) objectScript.object) - 1;
		//					}
		//					else {
		//						throw new Exception("execute1");
		//					}
		//				}
		//				else if (!org.isEmpty()) {
		//					final ObjectScript objectScript = scope.get(org.pollFirst().toString());
		//					buffer.addLast(new NumericScript(objectScript.object.toString()));
		//					if (objectScript.type.isAssignableFrom(Double.class)) {
		//						objectScript.object = ((Double) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Float.class)) {
		//						objectScript.object = ((Float) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Long.class)) {
		//						objectScript.object = ((Long) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//						objectScript.object = ((Integer) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Short.class)) {
		//						objectScript.object = ((Short) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//						objectScript.object = ((Byte) objectScript.object) - 1;
		//					}
		//					else if (objectScript.type.isAssignableFrom(Character.class)) {
		//						objectScript.object = ((Character) objectScript.object) - 1;
		//					}
		//					else {
		//						throw new Exception("execute1");
		//					}
		//				}
		//				else {
		//					throw new Exception("execute1");
		//				}
		//
		//				break;
		//
		//			case "+" :
		//				if (!((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty())) {
		//					buffer.addLast(this);
		//				}
		//				break;
		//
		//			case "-" :
		//				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
		//					final Script script = buffer.pollLast();
		//					if (script.isNumeric()) {
		//						buffer.addLast(new NumericScript("-" + script.toString()));
		//					}
		//					else {
		//						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
		//						buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						if (objectScript.type.isAssignableFrom(Double.class)) {
		//							objectScript.object = -((Double) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Float.class)) {
		//							objectScript.object = -((Float) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Long.class)) {
		//							objectScript.object = -((Long) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//							objectScript.object = -((Integer) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Short.class)) {
		//							objectScript.object = -((Short) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//							objectScript.object = -((Byte) objectScript.object);
		//						}
		//						else if (objectScript.type.isAssignableFrom(Character.class)) {
		//							objectScript.object = -((Character) objectScript.object);
		//						}
		//						else {
		//							throw new Exception("execute1");
		//						}
		//					}
		//				}
		//				else {
		//					buffer.addLast(this);
		//				}
		//				break;
		//			case "~" :
		//				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
		//					final Script script = buffer.pollLast();
		//					if (script.isNumeric()) {
		//						buffer.addLast(new NumericScript(Integer.toString(~Integer.parseInt(script.toString()))));
		//					}
		//					else {
		//						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
		//						if (objectScript.type.isAssignableFrom(Long.class)) {
		//							objectScript.object = ~((Long) objectScript.object);
		//							buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						}
		//						else if (objectScript.type.isAssignableFrom(Integer.class)) {
		//							objectScript.object = ~((Integer) objectScript.object);
		//							buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						}
		//						else if (objectScript.type.isAssignableFrom(Short.class)) {
		//							objectScript.object = ~((Short) objectScript.object);
		//							buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						}
		//						else if (objectScript.type.isAssignableFrom(Byte.class)) {
		//							objectScript.object = ~((Byte) objectScript.object);
		//							buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						}
		//						else if (objectScript.type.isAssignableFrom(Character.class)) {
		//							objectScript.object = ~((Character) objectScript.object);
		//							buffer.addLast(new NumericScript(objectScript.object.toString()));
		//						}
		//						else {
		//							throw new Exception("execute1");
		//						}
		//					}
		//				}
		//				else {
		//					throw new Exception("execute1");
		//				}
		//				break;
		//			case "!" :
		//				if ((buffer.isEmpty() || buffer.peekLast().isOperator()) && !org.isEmpty()) {
		//					final Script script = buffer.pollLast();
		//					if (script.isBoolean()) {
		//						buffer.addLast(new TokenScript(Boolean.toString(!Boolean.parseBoolean( script.toString()))));
		//					}
		//					else {
		//						final ObjectScript objectScript = scope.get(buffer.pollLast().toString());
		//						if (objectScript.type.isAssignableFrom(Boolean.class)) {
		//							objectScript.object = !((Boolean) objectScript.object);
		//							buffer.addLast(new TokenScript(objectScript.object.toString()));
		//						}
		//						else {
		//							throw new Exception("execute1");
		//						}
		//					}
		//				}
		//				else {
		//					throw new Exception("execute1");
		//				}
		//				break;
		//			default :
		//				buffer.addLast(this);
		//				break;
		//		}
		
	}
}
