package frameWork.core.viewCompiler.script;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import frameWork.core.viewCompiler.Scriptlet;
import frameWork.core.viewCompiler.Textlet;
import frameWork.core.viewCompiler.script.expression.BooleanScript;
import frameWork.core.viewCompiler.script.expression.CallChainScript;
import frameWork.core.viewCompiler.script.expression.CharacterScript;
import frameWork.core.viewCompiler.script.expression.ComplementScript;
import frameWork.core.viewCompiler.script.expression.ConditionOperatorScript;
import frameWork.core.viewCompiler.script.expression.ConstructorScript;
import frameWork.core.viewCompiler.script.expression.DeclarationScript;
import frameWork.core.viewCompiler.script.expression.NotScript;
import frameWork.core.viewCompiler.script.expression.NullScript;
import frameWork.core.viewCompiler.script.expression.NumericScript;
import frameWork.core.viewCompiler.script.expression.OperatorScript;
import frameWork.core.viewCompiler.script.expression.PostfixDecrementScript;
import frameWork.core.viewCompiler.script.expression.PostfixIncrementScript;
import frameWork.core.viewCompiler.script.expression.StringScript;
import frameWork.core.viewCompiler.script.expression.SubstitutionScript;
import frameWork.core.viewCompiler.script.expression.callChain.CallChain;
import frameWork.core.viewCompiler.script.expression.callChain.CallObjectScript;
import frameWork.core.viewCompiler.script.expression.callChain.CallMethodScript;
import frameWork.core.viewCompiler.script.syntax.BlockScript;
import frameWork.core.viewCompiler.script.syntax.BreakScript;
import frameWork.core.viewCompiler.script.syntax.ContineScript;
import frameWork.core.viewCompiler.script.syntax.DoScript;
import frameWork.core.viewCompiler.script.syntax.ExpressionScript;
import frameWork.core.viewCompiler.script.syntax.ForScript;
import frameWork.core.viewCompiler.script.syntax.IfScript;
import frameWork.core.viewCompiler.script.syntax.SwitchScript;
import frameWork.core.viewCompiler.script.syntax.SyntaxScript;
import frameWork.core.viewCompiler.script.syntax.WhileScript;

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
				final Scriptlet scriptlet = iterator.next().toScriptlet();
				if (scriptlet != null) {
					text += scriptlet.toString();
				}
			}
		}
	}
	
	private void next() {
		//		System.out.print(getChar());
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
			if (hasRemaining() && (isWhitespace(getChar()) || !isOperator(getChar()))) {
				//				System.out.print(string);
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
	
	@SuppressWarnings("rawtypes")
	public SyntaxScript getSyntaxToken() throws Exception {
		final SyntaxScript script = block();
		skip();
		return script;
	}
	
	@SuppressWarnings("rawtypes")
	public SyntaxScript block() throws Exception {
		if (startToken("switch")) {
			return new SwitchScript();
		}
		else if (startToken("while")) {
			return new WhileScript();
		}
		else if (startToken("for")) {
			return new ForScript();
		}
		else if (startToken("do")) {
			return new DoScript();
		}
		else if (startToken("if")) {
			return new IfScript();
		}
		else if (startToken("contine")) {
			return new ContineScript();
		}
		else if (startToken("break")) {
			return new BreakScript();
		}
		else if (startWith("{")) {
			return new BlockScript();
		}
		else {
			return getStatementToken();
		}
	}
	
	public ExpressionScript getStatementToken() throws Exception {
		final ExpressionScript script = base();
		skip();
		return script;
	}
	
	public ExpressionScript base() throws Exception {
		ExpressionScript ternary = condition();
		//		logic:
		{
			while ((getChar() == '|') || (getChar() == '&') || (getChar() == '^')) {
				if (startWith("|")) {
					ternary = new OperatorScript("|", ternary, condition());
				}
				else if (startWith("||")) {
					ternary = new OperatorScript("||", ternary, condition());
				}
				else if (startWith("&")) {
					ternary = new OperatorScript("&", ternary, condition());
				}
				else if (startWith("&&")) {
					ternary = new OperatorScript("&&", ternary, condition());
				}
				else if (startWith("^")) {
					ternary = new OperatorScript("^", ternary, condition());
				}
				else {
					break;
				}
			}
		}
		//		ternary:
		{
			if (startWith("?")) {
				final ExpressionScript expressionScript1 = base();
				if (getChar() != ':') {
					throw new Exception("Error " + getChar() + " at " + getPosition());
				}
				gotoNextChar();
				ternary = new ConditionOperatorScript(ternary, expressionScript1, base());
			}
		}
		if (startWith("=")) {
			return new SubstitutionScript(ternary, base());
		}
		else if (startWith("+=")) {
			return new SubstitutionScript(ternary, new OperatorScript("+", ternary, base()));
		}
		else if (startWith("-=")) {
			return new SubstitutionScript(ternary, new OperatorScript("-", ternary, base()));
		}
		else if (startWith("*=")) {
			return new SubstitutionScript(ternary, new OperatorScript("*", ternary, base()));
		}
		else if (startWith("/=")) {
			return new SubstitutionScript(ternary, new OperatorScript("/", ternary, base()));
		}
		else if (startWith("%=")) {
			return new SubstitutionScript(ternary, new OperatorScript("%", ternary, base()));
		}
		else if (startWith("&=")) {
			return new SubstitutionScript(ternary, new OperatorScript("&", ternary, base()));
		}
		else if (startWith("^=")) {
			return new SubstitutionScript(ternary, new OperatorScript("^", ternary, base()));
		}
		else if (startWith("|=")) {
			return new SubstitutionScript(ternary, new OperatorScript("|", ternary, base()));
		}
		else if (startWith("<<=")) {
			return new SubstitutionScript(ternary, new OperatorScript("<<", ternary, base()));
		}
		else if (startWith(">>=")) {
			return new SubstitutionScript(ternary, new OperatorScript(">>", ternary, base()));
		}
		else if (startWith(">>>=")) {
			return new SubstitutionScript(ternary, new OperatorScript(">>>", ternary, base()));
		}
		else {
			return ternary;
		}
	}
	
	private ExpressionScript condition() throws Exception {
		ExpressionScript a = shift();
		while ((getChar() == '<') || (getChar() == '>') || (getChar() == '=') || (getChar() == '!')) {
			if (startWith("<")) {
				a = new OperatorScript("<", a, shift());
			}
			else if (startWith(">")) {
				a = new OperatorScript(">", a, shift());
			}
			else if (startWith("==")) {
				a = new OperatorScript("==", a, shift());
			}
			else if (startWith("!=")) {
				a = new OperatorScript("!=", a, shift());
			}
			else if (startWith("<=")) {
				a = new OperatorScript("<=", a, shift());
			}
			else if (startWith(">=")) {
				a = new OperatorScript(">=", a, shift());
			}
			else {
				break;
			}
		}
		return a;
	}
	
	private ExpressionScript shift() throws Exception {
		ExpressionScript expression;
		//expression:
		{
			if (startWith("++")) {
				final ExpressionScript term = term();
				expression = new SubstitutionScript(term, new OperatorScript("+", term, new NumericScript("1")));
			}
			else if (startWith("--")) {
				final ExpressionScript term = term();
				expression = new SubstitutionScript(term, new OperatorScript("+", term, new NumericScript("1")));
			}
			else if (startWith("+")) {
				expression = term();
			}
			else if (startWith("-")) {
				expression = new OperatorScript("-", new NumericScript("0"), term());
			}
			else {
				expression = term();
			}
			while ((getChar() == '+') || (getChar() == '-')) {
				if (startWith("++")) {
					expression = new PostfixIncrementScript(expression);
				}
				else if (startWith("--")) {
					expression = new PostfixDecrementScript(expression);
				}
				else if (startWith("+")) {
					expression = new OperatorScript("+", expression, term());
				}
				else if (startWith("-")) {
					expression = new OperatorScript("-", expression, term());
				}
				else {
					break;
				}
			}
		}
		//shift:
		{
			if (startWith("<<")) {
				return new OperatorScript("<<", expression, base());
			}
			else if (startWith(">>")) {
				return new OperatorScript(">>", expression, base());
			}
			else if (startWith(">>>")) {
				return new OperatorScript(">>>", expression, base());
			}
			else {
				return expression;
			}
		}
	}
	
	private ExpressionScript term() throws Exception {
		ExpressionScript a = unary();
		while ((getChar() == '/') || (getChar() == '%') || (getChar() == '*')) {
			if (startWith("/")) {
				a = new OperatorScript("/", a, unary());
			}
			else if (startWith("%")) {
				a = new OperatorScript("%", a, unary());
			}
			else if (startWith("*")) {
				a = new OperatorScript("*", a, unary());
			}
			else {
				break;
			}
		}
		return a;
	}
	
	private ExpressionScript unary() throws Exception {
		ExpressionScript a = factor();
		while ((getChar() == '!') || (getChar() == '~')) {
			if (startWith("!")) {
				a = new NotScript(a);
			}
			else if (startWith("~")) {
				a = new ComplementScript(a);
			}
			else {
				break;
			}
		}
		return a;
	}
	
	private ExpressionScript factor() throws Exception {
		switch ( getChar() ) {
			case '\'' :
			case '"' : {
				return string(getChar());
			}
			case '(' : {
				gotoNextChar();
				final ExpressionScript a = base();
				if (getChar() != '(') {
					throw new Exception("Error " + getChar() + " at " + getPosition());
				}
				gotoNextChar();
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
					return new BooleanScript(true);
				}
				else if (startToken("false")) {
					return new BooleanScript(false);
				}
				else if (startToken("null")) {
					return new NullScript();
				}
				else if (isAlpha(getChar())) {
					final CallChainScript token = tokenScript();
					skip();
					if (isAlpha(getChar())) {
						return new DeclarationScript(token, tokenScript());
					}
					return token;
				}
				break;
		}
		return null;
	}
	
	private CallChainScript tokenScript() throws Exception {
		skip();
		final Deque<CallChain> expressionScripts = new ArrayDeque<>();
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
						list.add(base());
					}
					gotoNextChar();
					expressionScripts.addLast(new CallMethodScript(expression, list));
					break;
				}
				case '[' : {
					final CallObjectScript callObjectScript = new CallObjectScript(expression);
					do {
						gotoNextChar();
						callObjectScript.addArray(base());
						if (getChar() != ']') {
							throw illegalCharacterError();
						}
						gotoNextChar();
					}
					while (getChar() == '[');
					expressionScripts.addLast(callObjectScript);
					break;
				}
				default :
					expressionScripts.addLast(new CallObjectScript(expression));
			}
			skip();
			if ((hasRemaining() && (getChar() == '.'))) {
				gotoNextChar();
			}
			else {
				return new CallChainScript(expressionScripts);
				
			}
		}
		throw new Exception("Error Overflow");
	}
	
	private ConstructorScript newScript() throws Exception {
		skip();
		while (hasRemaining()) {
			String expression = "";
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
					list.add(base());
				}
				gotoNextChar();
				return new ConstructorScript(expression, list);
			}
			expression += getChar();
			skip();
		}
		throw new Exception("Error Overflow");
	}
	
	public boolean startToken(final String string) {
		String s = "";
		for (int i = 0; i < string.length(); i++) {
			s += getChar(index + i);
		}
		if (s.equals(string)) {
			index += string.length();
			if (hasRemaining() && (isWhitespace(getChar()) || !(isAlpha(getChar()) || isNumeric(getChar())))) {
				//				System.out.print(string);
				skip();
				return true;
			}
			index -= string.length();
		}
		return false;
	}
	
	private NumericScript numeric() throws Exception {
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
			}
			else {
				throw new Exception("Error " + getChar() + " at " + getPosition());
			}
		}
		if (isWhitespace(getChar())) {
			gotoNextChar();
		}
		return new NumericScript(numeric);
	}
	
	private ExpressionScript string(final char mark) {
		String token = "";
		token += getChar();
		next();
		while (hasRemaining() && (getChar() != mark)) {
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
		token = token.substring(1, token.length() - 1).replace("\\\\", "\\").replace("\\n", "\n").replace("\\r", "\r");
		if (mark == '\'') {
			return new CharacterScript(token.charAt(0));
		}
		return new StringScript(token);
	}
	
	public Exception illegalCharacterError() {
		return new Exception("Error " + getChar() + " at " + getPosition() + " " + stack());
	}
	
	public String stack() {
		return getChar(index - 10) + "" + getChar(index - 9) + getChar(index - 8) + getChar(index - 7)
		        + getChar(index - 6) + getChar(index - 5) + getChar(index - 4) + getChar(index - 3)
		        + getChar(index - 2) + getChar(index - 1) + getChar(index) + getChar(index + 1) + getChar(index + 2)
		        + getChar(index + 3) + getChar(index + 4) + getChar(index + 5) + getChar(index + 6)
		        + getChar(index + 7) + getChar(index + 8) + getChar(index + 9) + getChar(index + 10);
	}
}