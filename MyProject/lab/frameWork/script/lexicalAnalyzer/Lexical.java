package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.functions.JsCallback;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptFunction;
import frameWork.script.scriptVar.ScriptNativeFunction;
import frameWork.script.scriptVar.ScriptObject;
import frameWork.script.scriptVar.ScriptUndefined;

public abstract class Lexical {
	public static Lexical LEX_EOF = new LexicalEof();
	public static Lexical LEX_ID = new LexicalId();
	
	public static Lexical LEX_INT = new LexicalInt();
	public static Lexical LEX_FLOAT = new LexicalFloat();
	public static Lexical LEX_STR = new LexicalString();
	
	public static Lexical LEX_EQUALEQUAL = new LexicalEqualEqual();
	
	public static Lexical LEX_NEQUAL = new LexicalNotEqual();
	public static Lexical LEX_LEQUAL = new LexicalLeftEqual();
	public static Lexical LEX_LSHIFT = new LexicalLeftShift();
	public static Lexical LEX_GEQUAL = new LexicalRightEqual();
	public static Lexical LEX_RSHIFT = new LexicalRightShift();
	public static Lexical LEX_RSHIFTUNSIGNED = new LexicalRightShiftSigned();
	
	public static Lexical LEX_PLUS = new LexicalPlus();
	
	public static Lexical LEX_PLUSEQUAL = new LexicalPlusEqual();
	
	public static Lexical LEX_MINUS = new LexicalMinus();
	
	public static Lexical LEX_MINUSEQUAL = new LexicalMinusEqual();
	public static Lexical LEX_PLUSPLUS = new LexicalPlusPlus();
	public static Lexical LEX_MINUSMINUS = new LexicalMinusMinus();
	
	public static Lexical LEX_AND = new LexicalAnd();
	
	public static Lexical LEX_ANDEQUAL = new LexicalAndEqual();
	public static Lexical LEX_ANDAND = new LexicalAndAnd();
	
	public static Lexical LEX_OR = new LexicalOr();
	
	public static Lexical LEX_OREQUAL = new LexicalOrEqual();
	public static Lexical LEX_OROR = new LexicalOrOr();
	
	public static Lexical LEX_XOR = new LexicalXor();
	
	public static Lexical LEX_XOREQUAL = new LexicalXorEqual();
	public static Lexical LEX_R_IF = new LexicalIf();
	public static Lexical LEX_R_ELSE = new LexicalElse();
	public static Lexical LEX_R_DO = new LexicalDo();
	public static Lexical LEX_R_WHILE = new LexicalWhile();
	public static Lexical LEX_R_FOR = new LexicalFor();
	public static Lexical LEX_R_BREAK = new LexicalBreak();
	public static Lexical LEX_R_CONTINUE = new LexicalContinue();
	public static Lexical LEX_R_FUNCTION = new LexicalFunction();
	public static Lexical LEX_R_RETURN = new LexicalReturn();
	public static Lexical LEX_R_VAR = new LexicalVar();
	public static Lexical LEX_R_TRUE = new LexicalTrue();
	public static Lexical LEX_R_FALSE = new LexicalFalse();
	public static Lexical LEX_R_NULL = new LexicalNull();
	public static Lexical LEX_R_UNDEFINED = new LexicalUndefined();
	public static Lexical LEX_R_NEW = new LexicalNew();
	public static Lexical LEX_DOT = new LexicalDot();
	
	public static Lexical LEX_SEMICOLON = new LexicalSemicolon();
	
	public static Lexical LEX_ASTERISK = new LexicalAsterisk();
	public static Lexical LEX_SLASH = new LexicalSlash();
	
	public static Lexical LEX_PERCENT = new LexicalPercent();
	
	public static Lexical LEX_PARENTHESES_LEFT = new LexicalParenthesesLeft();
	public static Lexical LEX_PARENTHESES_RIGHT = new LexicalParenthesesRight();
	public static Lexical LEX_BRACE_LEFT = new LexicalBraceLeft();
	public static Lexical LEX_BRACE_RIGHT = new LexicalBraceRight();
	
	public static Lexical LEX_EXCLAMATION_POINT = new LexicalExclamationPoint();
	public static Lexical LEX_ANGLE_BRACKETS_LEFT = new LexicalAngleBracketsLeft();
	public static Lexical LEX_ANGLE_BRACKETS_RIGHT = new LexicalAngleBracketsRight();
	public static Lexical LEX_EQUAL = new LexicalEqual();
	public static Lexical LEX_QUESTION = new LexicalQuestion();
	
	public static Lexical LEX_COLON = new LexicalColon();
	
	public static Lexical LEX_COMMA = new LexicalComma();
	
	public static Lexical LEX_SQUARE_BRACKET_LEFT = new LexicalSquareBracketLeft();
	public static Lexical LEX_SQUARE_BRACKET_RIGHT = new LexicalSquareBracketRight();
	
	public abstract String getTokenStr();
	
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "EOF"));
	}
	
	public CScriptVar mathsOp() {
		return new ScriptUndefined();
	}
	
	public CScriptVar mathsOp(final int da, final int db) throws Exception {
		throw new Exception("Operation " + this + " not supported on the Int datatype");
	}
	
	public CScriptVar mathsOp(final double da, final double db) throws Exception {
		throw new Exception("Operation " + this + " not supported on the Double datatype");
	}
	
	public CScriptVar mathsOp(final String da, final String db) throws Exception {
		throw new Exception("Operation " + this + " not supported on the string datatype");
	}
	
	public CScriptVar mathsOp(final CScriptVar a, final CScriptVar b) throws Exception {
		throw new Exception("Operation " + this + " not supported on the Array datatype");
	}
	
	public CScriptVarLink base(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink ternary) throws Exception {
		return ternary;
	}
	
	public CScriptVarLink ternary(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink logic) throws Exception {
		return logic;
	}
	
	public CScriptVarLink shift(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink expression) throws Exception {
		return expression;
	}
	
	public boolean isEndOfFile() {
		return false;
	}
	
	public void factorBraceLeftEnd(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ","));
	}
	
	public void factorBraceLeftMatch(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "ID"));
	}
	
	public boolean isConditionNext() {
		return false;
	}
	
	public boolean isExpressionNext() {
		return false;
	}
	
	public CScriptVarLink expression(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink a) throws Exception {
		return a;
	}
	
	public CScriptVarLink negateOp(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink unary = lexicalAnalyzer.tk.unary(lexicalAnalyzer, execute, scopes);
		return lexicalAnalyzer.tk.term(lexicalAnalyzer, execute, scopes, unary);
	}
	
	public boolean isLogicNext() {
		return false;
	}
	
	public CScriptVarLink logicNext(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink a) throws Exception {
		return a;
	}
	
	public boolean isIdFactorNext() {
		return false;
	}
	
	public boolean isDot() {
		return false;
	}
	
	public boolean isNotSemicolon() {
		return true;
	}
	
	public boolean isTermNext() {
		return false;
	}
	
	public boolean isParenthesesLeft() {
		return false;
	}
	
	public boolean isNotParenthesesRight() {
		return true;
	}
	
	public void newFactor(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		// NOP
	}
	
	public void hasNextComma(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ","));
	}
	
	public int brackets(final int brackets) {
		return brackets;
	}
	
	public boolean isBraceRight() {
		return false;
	}
	
	public CScriptVarLink unary(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		return factor(lexicalAnalyzer, execute, scopes);
	}
	
	public void replaceWith(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink a) throws Exception {
		// NOP
	}
	
	public void isStatementNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ","));
	}
	
	public void isFactorNext(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ","));
	}
	
	public CScriptVarLink isStatementReturnNext(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		return lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
	}
	
	public boolean isSquareBracketLeft() {
		return false;
	}
	
	public boolean isSquareBracketRight() {
		return false;
	}
	
	public void parseFunctionArguments(final LexicalAnalyzer lexicalAnalyzer, final ScriptFunction funcVar)
	        throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public void functionCallAfter(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public void functionCallBlock(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		//NOP
	}
	
	public CScriptVarLink logic(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink condition) throws Exception {
		CScriptVarLink a = condition;
		while (lexicalAnalyzer.tk.isLogicNext()) {
			a = lexicalAnalyzer.tk.logicNext(lexicalAnalyzer, execute, scopes, a);
		}
		return a;
	}
	
	public CScriptVarLink condition(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink e1 = lexicalAnalyzer.tk.expression(lexicalAnalyzer, execute, scopes);
		CScriptVarLink a = lexicalAnalyzer.tk.shift(lexicalAnalyzer, execute, scopes, e1);
		CScriptVarLink b;
		while (lexicalAnalyzer.tk.isConditionNext()) {
			final Lexical op = lexicalAnalyzer.tk;
			lexicalAnalyzer.getNextToken();
			final CScriptVarLink e2 = lexicalAnalyzer.tk.expression(lexicalAnalyzer, execute, scopes);
			b = lexicalAnalyzer.tk.shift(lexicalAnalyzer, execute, scopes, e2);
			if (execute) {
				final CScriptVar res = a.var.mathsOp(b.var, op);
				a = a.createLink(res);
			}
			clean(b);
		}
		return a;
	}
	
	public CScriptVarLink expression(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		CScriptVarLink a = lexicalAnalyzer.tk.negateOp(lexicalAnalyzer, execute, scopes);
		while (lexicalAnalyzer.tk.isExpressionNext()) {
			final Lexical op = lexicalAnalyzer.tk;
			lexicalAnalyzer.getNextToken();
			a = op.expression(lexicalAnalyzer, execute, scopes, a);
		}
		return a;
	}
	
	public CScriptVarLink term(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes, final CScriptVarLink unary) throws Exception {
		CScriptVarLink a = unary;
		while (lexicalAnalyzer.tk.isTermNext()) {
			final Lexical op = lexicalAnalyzer.tk;
			lexicalAnalyzer.getNextToken();
			final CScriptVarLink b = lexicalAnalyzer.tk.unary(lexicalAnalyzer, execute, scopes);
			if (execute) {
				final CScriptVar res = a.var.mathsOp(b.var, op);
				a = a.createLink(res);
			}
			clean(b);
		}
		return a;
	}
	
	public CScriptVarLink functionCall(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final CScriptVarLink function, final CScriptVar parent, final Vector<CScriptVar> scopes) throws Exception {
		if (execute) {
			if (!function.var.isFunction()) {
				throw new Exception("Expecting '" + function.name + "' to be a function");
			}
			return lexicalAnalyzer.tk.startFunctionCall(lexicalAnalyzer, execute, function, parent, scopes);
		}
		lexicalAnalyzer.tk.functionCallAfter(lexicalAnalyzer, execute, scopes);
		return function;
	}
	
	public CScriptVarLink startFunctionCall(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final CScriptVarLink function, final CScriptVar parent, final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public CScriptVarLink base(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink condition = lexicalAnalyzer.tk.condition(lexicalAnalyzer, execute, scopes);
		final CScriptVarLink logic = lexicalAnalyzer.tk.logic(lexicalAnalyzer, execute, scopes, condition);
		final CScriptVarLink ternary = lexicalAnalyzer.tk.ternary(lexicalAnalyzer, execute, scopes, logic);
		return lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes, ternary);
	}
	
	public void addNative(final LexicalAnalyzer lexicalAnalyzer, final JsCallback jsCallback) throws Exception {
		lexicalAnalyzer.match(Lexical.LEX_R_FUNCTION);
		CScriptVar base = lexicalAnalyzer.root;
		String funcName = lexicalAnalyzer.tkStr;
		lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
		while (lexicalAnalyzer.tk.isDot()) {
			lexicalAnalyzer.getNextToken();
			CScriptVarLink link = base.findChild(funcName);
			if (link == null) {
				link = base.addChild(funcName, new ScriptObject());
			}
			base = link.var;
			funcName = lexicalAnalyzer.tkStr;
			lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
		}
		
		final ScriptNativeFunction funcVar = new ScriptNativeFunction(jsCallback);
		lexicalAnalyzer.tk.parseFunctionArguments(lexicalAnalyzer, funcVar);
		base.addChild(funcName, funcVar);
	}
	
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "EOF"));
	}
	
	public boolean block(final LexicalAnalyzer lexicalAnalyzer, final boolean execute, final Vector<CScriptVar> scopes)
	        throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "{"));
	}
	
	public boolean startWhileStatement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public boolean startForStatement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public boolean startIfStatement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "("));
	}
	
	public boolean startIfElseStatement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final boolean noexecute, final Vector<CScriptVar> scopes, final boolean cond) throws Exception {
		return execute;
	}
	
	public void assertColon(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ":"));
		
	}
	
	public void assertID(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), "ID"));
	}
	
	public void assertSemicolon(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		throw new Exception(lexicalAnalyzer.errorMessage(getTokenStr(), ";"));
	}
}
