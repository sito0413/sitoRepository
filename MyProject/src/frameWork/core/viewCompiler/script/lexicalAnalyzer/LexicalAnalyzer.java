package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;
import frameWork.core.viewCompiler.script.functions.JsCallback;
import frameWork.core.viewCompiler.script.scriptVar.ScriptRoot;

public class LexicalAnalyzer {
	String tkStr;
	public Lexical tk;
	int tokenStart;
	private char currCh, nextCh;
	int tokenEnd;
	int tokenLastEnd;
	final String data;
	private final int dataStart;
	final int dataEnd;
	
	private int dataPos;
	final ScriptRoot root;
	
	public LexicalAnalyzer(final ScriptRoot root, final String input) {
		this.root = root;
		data = input;
		dataStart = 0;
		dataEnd = input.length();
		reset();
	}
	
	private LexicalAnalyzer(final ScriptRoot root, final LexicalAnalyzer owner, final int startChar, final int endChar) {
		this.root = root;
		data = owner.data;
		dataStart = startChar;
		dataEnd = endChar;
		reset();
	}
	
	public String getPosition() {
		return getPosition(tokenLastEnd);
	}
	
	public void addNative(final JsCallback jsCallback) throws Exception {
		tk.addNative(this, jsCallback);
	}
	
	private String getPosition(int pos) {
		if (pos < 0) {
			pos = tokenLastEnd;
		}
		int line = 1, col = 1;
		for (int i = 0; i < pos; i++) {
			char ch;
			if (i < dataEnd) {
				ch = data.charAt(i);
			}
			else {
				ch = 0;
			}
			col++;
			if (ch == '\n') {
				line++;
				col = 0;
			}
		}
		return "(line: " + line + ", col: " + col + ")";
	}
	
	LexicalAnalyzer getSubLex(final int lastPosition) {
		final int lastCharIdx = tokenLastEnd + 1;
		if (lastCharIdx < dataEnd) {
			return new LexicalAnalyzer(root, this, lastPosition, lastCharIdx);
		}
		return new LexicalAnalyzer(root, this, lastPosition, dataEnd);
	}
	
	void reset() {
		dataPos = dataStart;
		tokenStart = 0;
		tokenEnd = 0;
		tokenLastEnd = 0;
		tk = Lexical.LEX_EOF;
		tkStr = "";
		getNextCh();
		getNextCh();
		getNextToken();
	}
	
	private void getNextCh() {
		currCh = nextCh;
		if (dataPos < dataEnd) {
			nextCh = data.charAt(dataPos);
		}
		else {
			nextCh = 0;
		}
		dataPos++;
	}
	
	public String errorMessage(final String tokenStr1, final String tokenStr2) {
		return "Got " + tokenStr1 + " expected " + tokenStr2 + " at " + getPosition(tokenStart);
	}
	
	public void match(final Lexical expected_tk) throws Exception {
		if (tk != expected_tk) {
			throw new Exception(errorMessage(tk.getTokenStr(), expected_tk.getTokenStr()));
		}
		getNextToken();
	}
	
	public Lexical getNextToken() {
		tk = Lexical.LEX_EOF;
		tkStr = "";
		while ((currCh != 0) && isWhitespace(currCh)) {
			getNextCh();
		}
		if ((currCh == '/') && (nextCh == '/')) {
			while ((currCh != 0) && (currCh != '\n')) {
				getNextCh();
			}
			getNextCh();
			getNextToken();
			return tk;
		}
		if ((currCh == '/') && (nextCh == '*')) {
			while ((currCh != 0) && ((currCh != '*') || (nextCh != '/'))) {
				getNextCh();
			}
			getNextCh();
			getNextCh();
			getNextToken();
			return tk;
		}
		
		tokenStart = dataPos - 2;
		if (isAlpha(currCh)) {
			while (isAlpha(currCh) || isNumeric(currCh)) {
				tkStr += currCh;
				getNextCh();
			}
			tk = Lexical.LEX_ID;
			if (tkStr.equals("if")) {
				tk = Lexical.LEX_R_IF;
			}
			else if (tkStr.equals("else")) {
				tk = Lexical.LEX_R_ELSE;
			}
			else if (tkStr.equals("do")) {
				tk = Lexical.LEX_R_DO;
			}
			else if (tkStr.equals("while")) {
				tk = Lexical.LEX_R_WHILE;
			}
			else if (tkStr.equals("for")) {
				tk = Lexical.LEX_R_FOR;
			}
			else if (tkStr.equals("break")) {
				tk = Lexical.LEX_R_BREAK;
			}
			else if (tkStr.equals("continue")) {
				tk = Lexical.LEX_R_CONTINUE;
			}
			else if (tkStr.equals("function")) {
				tk = Lexical.LEX_R_FUNCTION;
			}
			else if (tkStr.equals("return")) {
				tk = Lexical.LEX_R_RETURN;
			}
			else if (tkStr.equals("var")) {
				tk = Lexical.LEX_R_VAR;
			}
			else if (tkStr.equals("true")) {
				tk = Lexical.LEX_R_TRUE;
			}
			else if (tkStr.equals("false")) {
				tk = Lexical.LEX_R_FALSE;
			}
			else if (tkStr.equals("null")) {
				tk = Lexical.LEX_R_NULL;
			}
			else if (tkStr.equals("undefined")) {
				tk = Lexical.LEX_R_UNDEFINED;
			}
			else if (tkStr.equals("new")) {
				tk = Lexical.LEX_R_NEW;
			}
		}
		else if (isNumeric(currCh)) {
			boolean isHex = false;
			if (currCh == '0') {
				tkStr += currCh;
				getNextCh();
			}
			if (currCh == 'x') {
				isHex = true;
				tkStr += currCh;
				getNextCh();
			}
			tk = Lexical.LEX_INT;
			while (isNumeric(currCh) || (isHex && isHexadecimal(currCh))) {
				tkStr += currCh;
				getNextCh();
			}
			if (!isHex && (currCh == '.')) {
				tk = Lexical.LEX_FLOAT;
				tkStr += '.';
				getNextCh();
				while (isNumeric(currCh)) {
					tkStr += currCh;
					getNextCh();
				}
			}
			if (!isHex && ((currCh == 'e') || (currCh == 'E'))) {
				tk = Lexical.LEX_FLOAT;
				tkStr += currCh;
				getNextCh();
				if (currCh == '-') {
					tkStr += currCh;
					getNextCh();
				}
				while (isNumeric(currCh)) {
					tkStr += currCh;
					getNextCh();
				}
			}
		}
		else if (currCh == '"') {
			getNextCh();
			while ((currCh != 0) && (currCh != '"')) {
				if (currCh == '\\') {
					getNextCh();
					switch ( currCh ) {
						case 'n' :
							tkStr += '\n';
							break;
						case '"' :
							tkStr += '"';
							break;
						case '\\' :
							tkStr += '\\';
							break;
						default :
							tkStr += currCh;
					}
				}
				else {
					tkStr += currCh;
				}
				getNextCh();
			}
			getNextCh();
			tk = Lexical.LEX_STR;
		}
		else if (currCh == '\'') {
			getNextCh();
			while ((currCh != 0) && (currCh != '\'')) {
				if (currCh == '\\') {
					getNextCh();
					switch ( currCh ) {
						case 'n' :
							tkStr += '\n';
							break;
						case 'r' :
							tkStr += '\r';
							break;
						case 't' :
							tkStr += '\t';
							break;
						case '\'' :
							tkStr += '\'';
							break;
						case '\\' :
							tkStr += '\\';
							break;
						case 'x' : {
							String buf = "";
							getNextCh();
							buf = buf + currCh;
							getNextCh();
							buf = buf + currCh;
							tkStr += buf;
						}
							break;
						default :
							if ((currCh >= '0') && (currCh <= '7')) {
								String buf = "";
								buf = buf + currCh;
								getNextCh();
								buf = buf + currCh;
								getNextCh();
								buf = buf + currCh;
								tkStr += buf;
							}
							else {
								tkStr += currCh;
							}
					}
				}
				else {
					tkStr += currCh;
				}
				getNextCh();
			}
			getNextCh();
			tk = Lexical.LEX_STR;
		}
		else {
			final char oldCh = currCh;
			if (currCh != 0) {
				if (oldCh == '.') {
					tk = Lexical.LEX_DOT;
				}
				else if (oldCh == ';') {
					tk = Lexical.LEX_SEMICOLON;
				}
				else if (oldCh == '+') {
					tk = Lexical.LEX_PLUS;
				}
				else if (oldCh == '-') {
					tk = Lexical.LEX_MINUS;
				}
				else if (oldCh == '*') {
					tk = Lexical.LEX_ASTERISK;
				}
				else if (oldCh == '/') {
					tk = Lexical.LEX_SLASH;
				}
				else if (oldCh == '%') {
					tk = Lexical.LEX_PERCENT;
				}
				else if (oldCh == '(') {
					tk = Lexical.LEX_PARENTHESES_LEFT;
				}
				else if (oldCh == ')') {
					tk = Lexical.LEX_PARENTHESES_RIGHT;
				}
				else if (oldCh == '{') {
					tk = Lexical.LEX_BRACE_LEFT;
				}
				else if (oldCh == '}') {
					tk = Lexical.LEX_BRACE_RIGHT;
				}
				else if (oldCh == '!') {
					tk = Lexical.LEX_EXCLAMATION_POINT;
				}
				else if (oldCh == '<') {
					tk = Lexical.LEX_ANGLE_BRACKETS_LEFT;
				}
				else if (oldCh == '>') {
					tk = Lexical.LEX_ANGLE_BRACKETS_RIGHT;
				}
				else if (oldCh == '=') {
					tk = Lexical.LEX_EQUAL;
				}
				else if (oldCh == '?') {
					tk = Lexical.LEX_QUESTION;
				}
				else if (oldCh == '&') {
					tk = Lexical.LEX_AND;
				}
				else if (oldCh == '|') {
					tk = Lexical.LEX_OR;
				}
				else if (oldCh == '^') {
					tk = Lexical.LEX_XOR;
				}
				else if (oldCh == ':') {
					tk = Lexical.LEX_COLON;
				}
				else if (oldCh == '<') {
					tk = Lexical.LEX_COMMA;
				}
				else if (oldCh == '[') {
					tk = Lexical.LEX_SQUARE_BRACKET_LEFT;
				}
				else if (oldCh == ']') {
					tk = Lexical.LEX_SQUARE_BRACKET_RIGHT;
				}
				getNextCh();
			}
			if ((oldCh == '=') && (currCh == '=')) {
				tk = Lexical.LEX_EQUALEQUAL;
				getNextCh();
			}
			else if ((oldCh == '!') && (currCh == '=')) {
				tk = Lexical.LEX_NEQUAL;
				getNextCh();
			}
			else if ((oldCh == '<') && (currCh == '=')) {
				tk = Lexical.LEX_LEQUAL;
				getNextCh();
			}
			else if ((oldCh == '<') && (currCh == '<')) {
				tk = Lexical.LEX_LSHIFT;
				getNextCh();
			}
			else if ((oldCh == '>') && (currCh == '=')) {
				tk = Lexical.LEX_GEQUAL;
				getNextCh();
			}
			else if ((oldCh == '>') && (currCh == '>')) {
				tk = Lexical.LEX_RSHIFT;
				getNextCh();
				if (currCh == '>') {
					tk = Lexical.LEX_RSHIFTUNSIGNED;
					getNextCh();
				}
			}
			else if ((oldCh == '+') && (currCh == '=')) {
				tk = Lexical.LEX_PLUSEQUAL;
				getNextCh();
			}
			else if ((oldCh == '-') && (currCh == '=')) {
				tk = Lexical.LEX_MINUSEQUAL;
				getNextCh();
			}
			else if ((oldCh == '+') && (currCh == '+')) {
				tk = Lexical.LEX_PLUSPLUS;
				getNextCh();
			}
			else if ((oldCh == '-') && (currCh == '-')) {
				tk = Lexical.LEX_MINUSMINUS;
				getNextCh();
			}
			else if ((oldCh == '&') && (currCh == '=')) {
				tk = Lexical.LEX_ANDEQUAL;
				getNextCh();
			}
			else if ((oldCh == '&') && (currCh == '&')) {
				tk = Lexical.LEX_ANDAND;
				getNextCh();
			}
			else if ((oldCh == '|') && (currCh == '=')) {
				tk = Lexical.LEX_OREQUAL;
				getNextCh();
			}
			else if ((oldCh == '|') && (currCh == '|')) {
				tk = Lexical.LEX_OROR;
				getNextCh();
			}
			else if ((oldCh == '^') && (currCh == '=')) {
				tk = Lexical.LEX_XOREQUAL;
				getNextCh();
			}
		}
		tokenLastEnd = tokenEnd;
		tokenEnd = dataPos - 3;
		return tk;
	}
}
