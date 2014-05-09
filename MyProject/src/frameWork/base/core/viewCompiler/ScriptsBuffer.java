package frameWork.base.core.viewCompiler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import frameWork.base.core.viewCompiler.parser.Textlet;
import frameWork.base.core.viewCompiler.script.SyntaxScript;
import frameWork.base.core.viewCompiler.script.syntax.BlockScript;
import frameWork.base.core.viewCompiler.script.syntax.BreakScript;
import frameWork.base.core.viewCompiler.script.syntax.ContineScript;
import frameWork.base.core.viewCompiler.script.syntax.DoScript;
import frameWork.base.core.viewCompiler.script.syntax.ExpressionScript;
import frameWork.base.core.viewCompiler.script.syntax.ForScript;
import frameWork.base.core.viewCompiler.script.syntax.IfScript;
import frameWork.base.core.viewCompiler.script.syntax.SwitchScript;
import frameWork.base.core.viewCompiler.script.syntax.WhileScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.ArrayConstructorScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.ArrayScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.CallArrayScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.CallChainScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.CallMethodScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.CallObjectScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.CastScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.ConditionOperatorScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.ConstructorScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.DeclarationScript;
import frameWork.base.core.viewCompiler.script.syntax.expression.InstanceBytecode;
import frameWork.base.core.viewCompiler.script.syntax.expression.OperatorScript;

@SuppressWarnings("rawtypes")
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
				gotoNextChar();
			}
			if ((getChar() == '/') && (getChar(index + 1) == '*')) {
				while (((getChar() != '*') || (getChar(index + 1) != '/'))) {
					next();
				}
				next();
				gotoNextChar();
			}
		}
	}
	
	public char getChar() {
		return getChar(index);
	}
	
	private char getChar(final int i) {
		if (0 <= i) {
			read(i);
			if (i < text.length()) {
				return text.charAt(i);
			}
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
				final String scriptlet = iterator.next().toScript();
				if (scriptlet != null) {
					text += scriptlet;
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
		return (ch == '!') || (ch == '^') || (ch == '~') || (ch == '=') || (ch == '+') || (ch == '-') || (ch == '*')
		        || (ch == '/') || (ch == '%') || (ch == '&') || (ch == '|') || (ch == '<') || (ch == '>');
	}
	
	private boolean startWith(final String string) {
		String s = "";
		for (int i = 0; i < string.length(); i++) {
			s += getChar(index + i);
		}
		if (s.equals(string)) {
			index += string.length();
			if (hasRemaining() && (isWhitespace(getChar()) || !isOperator(getChar()))) {
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
	
	public final SyntaxScript getSyntaxToken() throws ScriptException {
		skip();
		if (startToken("switch")) {
			return new SwitchScript("");
		}
		else if (startToken("while")) {
			return new WhileScript("");
		}
		else if (startToken("for")) {
			return new ForScript("");
		}
		else if (startToken("do")) {
			return new DoScript("");
		}
		else if (startToken("if")) {
			return new IfScript("");
		}
		else if (startToken("contine")) {
			String token = "";
			if (getChar() != ';') {
				token = getLabel();
				skip();
				if (getChar() != ';') {
					throw illegalCharacterError();
				}
			}
			return new ContineScript(token);
		}
		else if (startToken("break")) {
			String token = "";
			if (getChar() != ';') {
				token = getLabel();
				skip();
				if (getChar() != ';') {
					throw illegalCharacterError();
				}
			}
			return new BreakScript(token);
		}
		else if (startToken("case")) {
			throw ScriptException.IllegalStateException();
		}
		else if (startWith("{")) {
			return new BlockScript("");
		}
		else {
			int buffer = index;
			final SyntaxScript script = getStatementToken();
			if (getChar() == ':') {
				final int temp = index;
				index = buffer;
				buffer = temp;
				final String label = getLabel();
				if (getChar() == ':') {
					gotoNextChar();
					if (startToken("switch")) {
						return new SwitchScript(label);
					}
					else if (startToken("while")) {
						return new WhileScript(label);
					}
					else if (startToken("for")) {
						return new ForScript(label);
					}
					else if (startToken("do")) {
						return new DoScript(label);
					}
					else if (startToken("if")) {
						return new IfScript(label);
					}
					else if (startWith("{")) {
						return new BlockScript(label);
					}
					index = buffer;
				}
				throw ScriptException.IllegalStateException();
			}
			return script;
		}
	}
	
	public ExpressionScript getStatementToken() throws ScriptException {
		ExpressionScript logic = condition();
		{
			while ((getChar() == '|') || (getChar() == '&') || (getChar() == '^')) {
				if (startWith("|")) {
					logic = new OperatorScript("|", logic, condition());
				}
				else if (startWith("||")) {
					logic = new OperatorScript("||", logic, condition());
				}
				else if (startWith("&")) {
					logic = new OperatorScript("&", logic, condition());
				}
				else if (startWith("&&")) {
					logic = new OperatorScript("&&", logic, condition());
				}
				else if (startWith("^")) {
					logic = new OperatorScript("^", logic, condition());
				}
				else {
					break;
				}
			}
		}
		ExpressionScript ternary = logic;
		//ternary
		{
			if (startWith("?")) {
				final ExpressionScript expressionScript1 = getStatementToken();
				if (getChar() != ':') {
					throw illegalCharacterError();
				}
				gotoNextChar();
				ternary = new ConditionOperatorScript(ternary, expressionScript1, getStatementToken());
			}
		}
		final ExpressionScript base = ternary;
		//base
		{
			if (startWith("=")) {
				return new OperatorScript("=", base, getStatementToken());
			}
			else if (startWith("+=")) {
				return new OperatorScript("=", base, new OperatorScript("+", base, getStatementToken()));
			}
			else if (startWith("-=")) {
				return new OperatorScript("=", base, new OperatorScript("-", base, getStatementToken()));
			}
			else if (startWith("*=")) {
				return new OperatorScript("=", base, new OperatorScript("*", base, getStatementToken()));
			}
			else if (startWith("/=")) {
				return new OperatorScript("=", base, new OperatorScript("/", base, getStatementToken()));
			}
			else if (startWith("%=")) {
				return new OperatorScript("=", base, new OperatorScript("%", base, getStatementToken()));
			}
			else if (startWith("&=")) {
				return new OperatorScript("=", base, new OperatorScript("&", base, getStatementToken()));
			}
			else if (startWith("^=")) {
				return new OperatorScript("=", base, new OperatorScript("^", base, getStatementToken()));
			}
			else if (startWith("|=")) {
				return new OperatorScript("=", base, new OperatorScript("|", base, getStatementToken()));
			}
			else if (startWith("<<=")) {
				return new OperatorScript("=", base, new OperatorScript("<<", base, getStatementToken()));
			}
			else if (startWith(">>=")) {
				return new OperatorScript("=", base, new OperatorScript(">>", base, getStatementToken()));
			}
			else if (startWith(">>>=")) {
				return new OperatorScript("=", base, new OperatorScript(">>>", base, getStatementToken()));
			}
			else {
				skip();
			}
		}
		return base;
	}
	
	private ExpressionScript condition() throws ScriptException {
		ExpressionScript shift = shift();
		while ((getChar() == '<') || (getChar() == '>') || (getChar() == '=') || (getChar() == '!')) {
			if (startWith("<")) {
				shift = new OperatorScript("<", shift, shift());
			}
			else if (startWith(">")) {
				shift = new OperatorScript(">", shift, shift());
			}
			else if (startWith("==")) {
				shift = new OperatorScript("==", shift, shift());
			}
			else if (startWith("!=")) {
				shift = new OperatorScript("!=", shift, shift());
			}
			else if (startWith("<=")) {
				shift = new OperatorScript("<=", shift, shift());
			}
			else if (startWith(">=")) {
				shift = new OperatorScript(">=", shift, shift());
			}
			else {
				break;
			}
		}
		return shift;
	}
	
	private ExpressionScript shift() throws ScriptException {
		ExpressionScript expression;
		//expression
		{
			if (startWith("++")) {
				final ExpressionScript term = term();
				expression = new OperatorScript("=", term, new OperatorScript("+", term, InstanceBytecode.ONE));
			}
			else if (startWith("--")) {
				final ExpressionScript term = term();
				expression = new OperatorScript("=", term, new OperatorScript("+", term, InstanceBytecode.ONE));
			}
			else if (startWith("+")) {
				expression = term();
			}
			else if (startWith("-")) {
				final ExpressionScript term = term();
				expression = new OperatorScript("-", InstanceBytecode.ZERO, term);
			}
			else {
				expression = term();
			}
			while ((getChar() == '+') || (getChar() == '-')) {
				if (startWith("++")) {
					expression = new OperatorScript("++", expression);
				}
				else if (startWith("--")) {
					expression = new OperatorScript("--", expression);
				}
				else if (startWith("+")) {
					expression = new OperatorScript("+", expression, term());
				}
				else if (startWith("-")) {
					expression = new OperatorScript("-", expression, term());
				}
			}
		}
		if (startWith("<<")) {
			return new OperatorScript("<<", expression, getStatementToken());
		}
		else if (startWith(">>")) {
			return new OperatorScript(">>", expression, getStatementToken());
		}
		else if (startWith(">>>")) {
			return new OperatorScript(">>>", expression, getStatementToken());
		}
		else {
			return expression;
		}
	}
	
	private ExpressionScript term() throws ScriptException {
		ExpressionScript term = unary();
		while ((getChar() == '/') || (getChar() == '%') || (getChar() == '*')) {
			if (startWith("/")) {
				term = new OperatorScript("/", term, unary());
			}
			else if (startWith("%")) {
				term = new OperatorScript("%", term, unary());
			}
			else if (startWith("*")) {
				term = new OperatorScript("*", term, unary());
			}
			else {
				break;
			}
		}
		return term;
	}
	
	private ExpressionScript unary() throws ScriptException {
		if (startWith("!")) {
			return new OperatorScript("!", factor());
		}
		else if (startWith("~")) {
			return new OperatorScript("~", factor());
		}
		else {
			return factor();
		}
	}
	
	private ExpressionScript factor() throws ScriptException {
		switch ( getChar() ) {
			case '\'' :
			case '"' : {
				return string(getChar());
			}
			case '{' : {
				final Deque<ExpressionScript> expressionScripts = new ArrayDeque<>();
				gotoNextChar();
				final List<ExpressionScript> list = new ArrayList<>();
				while (hasRemaining() && (getChar() != '}')) {
					if (getChar() == ',') {
						gotoNextChar();
					}
					list.add(getStatementToken());
				}
				gotoNextChar();
				if ((getChar() == '.')) {
					final ArrayDeque<ExpressionScript> arrayDeque = new ArrayDeque<>();
					arrayDeque.addFirst(new ArrayScript(expressionScripts));
					gotoNextChar();
					return tokenScript(arrayDeque);
				}
				return new ArrayScript(expressionScripts);
			}
			case '(' : {
				gotoNextChar();
				final ExpressionScript a = getStatementToken();
				if (getChar() != ')') {
					throw illegalCharacterError();
				}
				gotoNextChar();
				if (isAlpha(getChar()) || isNumeric(getChar()) || (getChar() == '(')) {
					return new CastScript(a, getStatementToken());
				}
				else if ((getChar() == '.')) {
					final ArrayDeque<ExpressionScript> arrayDeque = new ArrayDeque<>();
					arrayDeque.addFirst(a);
					gotoNextChar();
					return tokenScript(arrayDeque);
				}
				return a;
			}
			default :
				if (isNumeric(getChar())) {
					return numeric();
				}
				else if (startToken("new")) {
					return newScript();
				}
				else if (startToken("true")) {
					return new InstanceBytecode(boolean.class, true);
				}
				else if (startToken("false")) {
					return new InstanceBytecode(boolean.class, false);
				}
				else if (startToken("null")) {
					return new InstanceBytecode(Object.class, null);
				}
				else if (isAlpha(getChar())) {
					return callChain();
				}
				break;
		}
		throw illegalCharacterError();
	}
	
	private ExpressionScript callChain() throws ScriptException {
		final CallChainScript token = tokenScript(new ArrayDeque<ExpressionScript>());
		skip();
		if (isAlpha(getChar())) {
			return new DeclarationScript(token, tokenScript(new ArrayDeque<ExpressionScript>()));
		}
		return token;
		
	}
	
	private CallChainScript tokenScript(final Deque<ExpressionScript> expressionScripts) throws ScriptException {
		skip();
		while (hasRemaining()) {
			String expression = "";
			while (hasRemaining() && (isAlpha(getChar()) || isNumeric(getChar()))) {
				expression += getChar();
				next();
			}
			skip();
			switch ( getChar() ) {
				case '(' : {
					gotoNextChar();
					final List<ExpressionScript> list = new ArrayList<>();
					while (hasRemaining() && (getChar() != ')')) {
						if (getChar() == ',') {
							gotoNextChar();
						}
						list.add(getStatementToken());
					}
					gotoNextChar();
					expressionScripts.addLast(new CallMethodScript(expression, list));
					break;
				}
				case '[' : {
					final CallArrayScript callObjectScript = new CallArrayScript(expression);
					do {
						gotoNextChar();
						if (getChar() != ']') {
							callObjectScript.addArray(getStatementToken());
							if (getChar() != ']') {
								throw illegalCharacterError();
							}
						}
						gotoNextChar();
					}
					while (getChar() == '[');
					expressionScripts.addLast(callObjectScript);
					break;
				}
				case '<' : {
					expressionScripts.addLast(new CallObjectScript(expression));
					final int mark = index;
					gotoNextChar();
					int depth = 1;
					while (hasRemaining() && (depth > 0)) {
						if (isAlpha(getChar())) {
							while (hasRemaining() && (isAlpha(getChar()) || isNumeric(getChar()))) {
								next();
							}
						}
						else if (getChar() == ',') {
							gotoNextChar();
						}
						else if (getChar() == '>') {
							depth--;
							gotoNextChar();
						}
						else if (getChar() == '<') {
							depth++;
							gotoNextChar();
						}
						else {
							break;
						}
					}
					if (!hasRemaining() || (depth > 0)) {
						index = mark;
					}
					break;
				}
				default :
					expressionScripts.addLast(new CallObjectScript(expression));
					break;
			}
			if ((hasRemaining() && (getChar() == '.'))) {
				gotoNextChar();
			}
			else {
				return new CallChainScript(expressionScripts);
			}
		}
		throw ScriptException.overflowException();
	}
	
	private ConstructorScript newScript() throws ScriptException {
		skip();
		String expression = "";
		while (hasRemaining()) {
			while (hasRemaining() && (isAlpha(getChar()) || isNumeric(getChar()))) {
				expression += getChar();
				next();
			}
			skip();
			if (getChar() == '(') {
				gotoNextChar();
				final List<ExpressionScript> list = new ArrayList<>();
				while (hasRemaining() && (getChar() != ')')) {
					if (getChar() == ',') {
						gotoNextChar();
					}
					list.add(getStatementToken());
				}
				gotoNextChar();
				return new ConstructorScript(expression, list);
			}
			if (getChar() == '[') {
				gotoNextChar();
				final List<ExpressionScript> list = new ArrayList<>();
				final ExpressionScript i = getStatementToken();
				if (getChar() != ']') {
					throw ScriptException.overflowException();
				}
				gotoNextChar();
				if (getChar() == '{') {
					gotoNextChar();
					while (hasRemaining() && (getChar() != '}')) {
						if (getChar() == ',') {
							gotoNextChar();
						}
						list.add(getStatementToken());
					}
					gotoNextChar();
				}
				return new ArrayConstructorScript(expression, i, list);
			}
			if (getChar() == '<') {
				while (hasRemaining() && (getChar() != '>')) {
					gotoNextChar();
				}
				gotoNextChar();
				continue;
			}
			if (getChar() == '.') {
				expression += getChar();
				skip();
				continue;
			}
			break;
		}
		throw ScriptException.overflowException();
	}
	
	private InstanceBytecode numeric() throws ScriptException {
		String numeric = "";
		while (hasRemaining() && isNumeric(getChar())) {
			numeric += getChar();
			next();
		}
		if (getChar() == '.') {
			numeric += getChar();
			next();
			if (hasRemaining() && isNumeric(getChar())) {
				while (hasRemaining() && isNumeric(getChar())) {
					numeric += getChar();
					next();
				}
				skip();
				return new InstanceBytecode(double.class, Double.parseDouble(numeric));
			}
			throw illegalCharacterError();
		}
		skip();
		return new InstanceBytecode(int.class, Integer.parseInt(numeric));
	}
	
	private ExpressionScript string(final char mark) {
		String token = "";
		token += getChar();
		next();
		while (hasRemaining() && (getChar() != mark)) {
			final char c = getChar();
			if (c != '\\') {
				token += c;
			}
			else {
				next();
				switch ( getChar() ) {
					case '"' :
						token += '"';
						break;
					case '\\' :
						token += '\\';
						break;
					case 'r' :
						token += '\r';
						break;
					case 'n' :
						token += '\n';
						break;
					case 't' :
						token += '\t';
						break;
					default :
						token += c;
						break;
				}
			}
			next();
		}
		token += getChar();
		gotoNextChar();
		token = token.substring(1, token.length() - 1);
		if (mark == '\'') {
			return new InstanceBytecode(char.class, token.charAt(0));
		}
		return new InstanceBytecode(String.class, token);
	}
	
	public ScriptException illegalCharacterError() {
		int line = 1, col = 1;
		for (int i = 0; i < index; i++) {
			final char ch = getChar(i);
			col++;
			if (ch == '\n') {
				line++;
				col = 0;
			}
		}
		return ScriptException.IllegalStateException("Error " + getChar() + " at(line: " + line + ", col: " + col + ")"
		        + getChar(index + 1) + getChar(index + 2) + getChar(index + 3) + getChar(index + 4)
		        + getChar(index + 5) + getChar(index + 6) + getChar(index + 7) + getChar(index + 8)
		        + getChar(index + 9) + getChar(index + 10) + getChar(index + 11) + getChar(index + 12));
	}
	
	private String getLabel() throws ScriptException {
		final StringBuilder builder = new StringBuilder();
		skip();
		if (isAlpha(getChar()) || isNumeric(getChar())) {
			do {
				builder.append(getChar());
				next();
			}
			while (hasRemaining() && (isAlpha(getChar()) || isNumeric(getChar())));
		}
		else if ((getChar() == '"') || (getChar() == '\'')) {
			builder.append(string(getChar()).execute(null).get().toString());
		}
		skip();
		return builder.toString();
	}
	
	public boolean startToken(final String string) {
		String s = "";
		for (int i = 0; i < string.length(); i++) {
			s += getChar(index + i);
		}
		if (s.equals(string)) {
			index += string.length();
			if (hasRemaining() && (isWhitespace(getChar()) || !(isAlpha(getChar()) || isNumeric(getChar())))) {
				skip();
				return true;
			}
			index -= string.length();
		}
		return false;
	}
	
	public void execute(final Scope scope) throws ScriptException {
		while (hasRemaining()) {
			final SyntaxScript script = getSyntaxToken();
			switch ( script.create(this) ) {
				case ';' :
				case '}' :
					gotoNextChar();
					break;
				default :
					break;
			}
			script.execute(scope);
		}
	}
}