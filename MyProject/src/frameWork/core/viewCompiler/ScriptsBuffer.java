package frameWork.core.viewCompiler;

import java.util.Iterator;
import java.util.List;

import frameWork.core.viewCompiler.script.DoScript;
import frameWork.core.viewCompiler.script.ExpressionScript;
import frameWork.core.viewCompiler.script.ForScript;
import frameWork.core.viewCompiler.script.IfScript;
import frameWork.core.viewCompiler.script.MethodScript;
import frameWork.core.viewCompiler.script.NumericScript;
import frameWork.core.viewCompiler.script.OperatorScript;
import frameWork.core.viewCompiler.script.StringScript;
import frameWork.core.viewCompiler.script.SwitchScript;
import frameWork.core.viewCompiler.script.TokenScript;
import frameWork.core.viewCompiler.script.WhileScript;

public class ScriptsBuffer {
	
	private final Iterator<Textlet> iterator;
	private int index;
	private String text;
	
	public ScriptsBuffer(final List<Textlet> textlets) {
		text = "";
		iterator = textlets.iterator();
		index = 0;
	}
	
	public void skip() {
		while (hasRemaining() && isWhitespace(getChar())) {
			next();
		}
		if (hasRemaining()) {
			if ((getChar() == '/') && (getChar(index + 1) == '/')) {
				while ((getChar() != '\n')) {
					next();
				}
				next();
				skip();
			}
			if ((getChar() == '/') && (getChar(index + 1) == '*')) {
				while (((getChar() != '*') || (getChar(index + 1) != '/'))) {
					next();
				}
				next();
				next();
				skip();
			}
		}
	}
	
	public char getChar() {
		return getChar(index);
	}
	
	private char getChar(final int i) {
		read(i);
		if (index < text.length()) {
			return text.charAt(i);
		}
		return 0;
	}
	
	public boolean hasRemaining() {
		read(index);
		return index < text.length();
	}
	
	private void read(final int i) {
		if (iterator.hasNext()) {
			while (i >= text.length()) {
				if (!iterator.hasNext()) {
					return;
				}
				final Scriptlet scriptlet = iterator.next().toScriptlet();
				if (scriptlet != null) {
					text += scriptlet.toString();
				}
			}
		}
	}
	
	private void next() {
		index++;
	}
	
	private static boolean isWhitespace(final char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r');
	}
	
	private static boolean isAlpha(final char ch) {
		return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_');
	}
	
	private static boolean isNumeric(final char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	
	private static boolean isOperator(final char ch) {
		return (ch == '!') || (ch == '=') || (ch == '<') || (ch == '>') || (ch == '+') || (ch == '-') || (ch == '*')
		        || (ch == '/') || (ch == '&') || (ch == '|') || (ch == '^') || (ch == '%');
		
	}
	
	public String getPosition() {
		int line = 1, col = 1;
		for (int i = 0; i < index; i++) {
			final char ch = getChar(i);
			col++;
			if (ch == '\n') {
				line++;
				col = 0;
			}
		}
		return "(line: " + line + ", col: " + col + ")";
	}
	
	public boolean startWith(final String string) {
		String s = "";
		for (int i = 0; i < string.length(); i++) {
			s += getChar(index + i);
		}
		if (s.equals(string)) {
			index += string.length();
			if (hasRemaining() && !(isAlpha(getChar()) || isNumeric(getChar()))) {
				skip();
				return true;
				
			}
			index -= string.length();
		}
		return false;
	}
	
	public char gotoNextChar() {
		next();
		skip();
		return getChar();
	}
	
	public Script getSyntaxToken() throws Exception {
		if (startWith("switch")) {
			return new SwitchScript();
		}
		else if (startWith("while")) {
			return new WhileScript();
		}
		else if (startWith("for")) {
			return new ForScript();
		}
		else if (startWith("do")) {
			return new DoScript();
		}
		else if (startWith("if")) {
			return new IfScript();
		}
		else {
			return getStatementToken();
		}
	}
	
	public Script getStatementToken() throws Exception {
		final ExpressionScript expressionScript = new ExpressionScript();
		String expression = "";
		boolean isNumeric = true;
		while (hasRemaining()) {
			switch ( getChar() ) {
				case ':' :
				case ',' :
				case ';' :
				case ')' :
				case '}' : {
					if (!expression.isEmpty()) {
						if (isNumeric) {
							expressionScript.addToken(new NumericScript(expression));
							isNumeric = true;
							expression = "";
						}
						else {
							expressionScript.addToken(new TokenScript(expression));
							isNumeric = true;
							expression = "";
						}
					}
					return expressionScript;
				}
				case '"' : {
					if (!expression.isEmpty()) {
						throw new Exception("Error " + expression + "\" at " + getPosition());
					}
					String token = "";
					token += getChar();
					next();
					while (hasRemaining() && (getChar() != '"')) {
						final char c = getChar();
						token += c;
						next();
						if (c == '\\') {
							token += getChar();
							next();
						}
					}
					token += getChar();
					next();
					skip();
					expressionScript.addToken(new StringScript(token));
					break;
				}
				case '\'' : {
					if (!expression.isEmpty()) {
						throw new Exception("Error " + expression + "' at " + getPosition());
					}
					String token = "";
					token += getChar();
					next();
					while (hasRemaining() && (getChar() != '\'')) {
						final char c = getChar();
						token += c;
						next();
						if (c == '\\') {
							token += getChar();
							next();
						}
					}
					token += getChar();
					next();
					skip();
					expressionScript.addToken(new StringScript(token));
					break;
				}
				case '(' : {
					if (expression.isEmpty()) {
						final ExpressionScript script = new ExpressionScript();
						script.statement(this);
						expressionScript.addToken(script);
					}
					else {
						final MethodScript script = new MethodScript(expression);
						isNumeric = true;
						expression = "";
						script.statement(this);
						expressionScript.addToken(script);
					}
					
					break;
				}
				case '?' : {
					expressionScript.addToken(new OperatorScript("?"));
					next();
					skip();
					expressionScript.addToken(getStatementToken());
					if (getChar() != ':') {
						throw new Exception("Error " + getChar() + " at " + getPosition());
					}
					next();
					skip();
					break;
				}
				default :
					if (isWhitespace(getChar()) || isOperator(getChar()) || (getChar() == '.')) {
						if (getChar() == '.') {
							if (!expression.isEmpty() && !isNumeric) {
								expressionScript.addToken(new TokenScript(expression));
								isNumeric = true;
								expression = "";
							}
							isNumeric &= isNumeric(getChar());
							expression += getChar();
							next();
						}
						else if (isOperator(getChar())) {
							if (!expression.isEmpty()) {
								if (isNumeric) {
									expressionScript.addToken(new NumericScript(expression));
									isNumeric = true;
									expression = "";
								}
								else {
									expressionScript.addToken(new TokenScript(expression));
									isNumeric = true;
									expression = "";
								}
							}
							
							String operator = "";
							while (hasRemaining() && isOperator(getChar())) {
								operator += getChar();
								next();
							}
							switch ( operator ) {
								case "+" :
								case "-" :
								case "*" :
								case "/" :
								case "%" :
								case "!" :
								case "<" :
								case ">" :
								case "=" :
								case "&" :
								case "|" :
								case "^" :
								case "==" :
								case "!=" :
								case "<=" :
								case "<<" :
								case ">=" :
								case ">>" :
								case ">>>" :
								case "+=" :
								case "-=" :
								case "++" :
								case "--" :
								case "*=" :
								case "/=" :
								case "&=" :
								case "&&" :
								case "|=" :
								case "||" :
								case "^=" :
									expressionScript.addToken(new OperatorScript(operator));
									break;
								
								case "+-" :
								case "*-" :
								case "/-" :
								case "%-" :
								case "!-" :
								case "<-" :
								case ">-" :
								case "=-" :
								case "?-" :
								case "&-" :
								case "|-" :
								case "^-" :
								case "==-" :
								case "!=-" :
								case "<=-" :
								case "<<-" :
								case ">=-" :
								case ">>-" :
								case ">>>-" :
								case "+=-" :
								case "-=-" :
								case "++-" :
								case "---" :
								case "*=-" :
								case "/=-" :
								case "&=-" :
								case "&&-" :
								case "|=-" :
								case "||-" :
								case "^=-" : {
									expressionScript.addToken(new OperatorScript(operator.substring(0,
									        operator.length() - 1)));
									expressionScript.addToken(new OperatorScript("-"));
									break;
								}
								
								default :
									throw new Exception("Error " + operator + " at " + getPosition());
							}
							
						}
						else {
							if (!expression.isEmpty()) {
								if (isNumeric) {
									expressionScript.addToken(new NumericScript(expression));
									isNumeric = true;
									expression = "";
								}
								else {
									expressionScript.addToken(new TokenScript(expression));
									isNumeric = true;
									expression = "";
								}
							}
							skip();
						}
					}
					else {
						isNumeric &= isNumeric(getChar());
						expression += getChar();
						next();
					}
					break;
			}
		}
		throw new Exception("Error over index");
	}
}