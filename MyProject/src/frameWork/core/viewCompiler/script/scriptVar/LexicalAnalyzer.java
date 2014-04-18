package frameWork.core.viewCompiler.script.scriptVar;

import static frameWork.core.viewCompiler.script.scriptVar.Util.*;

import java.util.Vector;

public class LexicalAnalyzer {
	private String tkStr;
	public String tk;
	private int tokenStart;
	private char currCh, nextCh;
	private int tokenEnd;
	private int tokenLastEnd;
	private final String data;
	private final int dataStart;
	private final int dataEnd;
	
	private int dataPos;
	private final ScriptRoot root;
	
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
		return "(line: " + line + ", col: " + col + LEX_PARENTHESES_RIGHT;
	}
	
	private LexicalAnalyzer getSubLex(final int lastPosition) {
		final int lastCharIdx = tokenLastEnd + 1;
		if (lastCharIdx < dataEnd) {
			return new LexicalAnalyzer(root, this, lastPosition, lastCharIdx);
		}
		return new LexicalAnalyzer(root, this, lastPosition, dataEnd);
	}
	
	private void reset() {
		dataPos = dataStart;
		tokenStart = 0;
		tokenEnd = 0;
		tokenLastEnd = 0;
		tk = LEX_EOF;
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
	
	private String errorMessage(final String tokenStr1, final String tokenStr2) {
		return "Got " + tokenStr1 + " expected " + tokenStr2 + " at " + getPosition(tokenStart);
	}
	
	private void match(final String expected_tk) throws Exception {
		if (tk != expected_tk) {
			throw new Exception(errorMessage(tk, expected_tk));
		}
		getNextToken();
	}
	
	private String getNextToken() {
		tk = LEX_EOF;
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
			tk = LEX_ID;
			if (tkStr.equals(LEX_R_IF)) {
				tk = LEX_R_IF;
			}
			else if (tkStr.equals(LEX_R_ELSE)) {
				tk = LEX_R_ELSE;
			}
			else if (tkStr.equals(LEX_R_DO)) {
				tk = LEX_R_DO;
			}
			else if (tkStr.equals(LEX_R_WHILE)) {
				tk = LEX_R_WHILE;
			}
			else if (tkStr.equals(LEX_R_FOR)) {
				tk = LEX_R_FOR;
			}
			else if (tkStr.equals(LEX_R_BREAK)) {
				tk = LEX_R_BREAK;
			}
			else if (tkStr.equals(LEX_R_CONTINUE)) {
				tk = LEX_R_CONTINUE;
			}
			else if (tkStr.equals(LEX_R_VAR)) {
				tk = LEX_R_VAR;
			}
			else if (tkStr.equals(LEX_R_TRUE)) {
				tk = LEX_R_TRUE;
			}
			else if (tkStr.equals(LEX_R_FALSE)) {
				tk = LEX_R_FALSE;
			}
			else if (tkStr.equals(LEX_R_NULL)) {
				tk = LEX_R_NULL;
			}
			else if (tkStr.equals(LEX_R_NEW)) {
				tk = LEX_R_NEW;
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
			tk = LEX_INT;
			while (isNumeric(currCh) || (isHex && isHexadecimal(currCh))) {
				tkStr += currCh;
				getNextCh();
			}
			if (!isHex && (currCh == '.')) {
				tk = LEX_FLOAT;
				tkStr += '.';
				getNextCh();
				while (isNumeric(currCh)) {
					tkStr += currCh;
					getNextCh();
				}
			}
			if (!isHex && ((currCh == 'e') || (currCh == 'E'))) {
				tk = LEX_FLOAT;
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
						case 'r' :
							tkStr += '\r';
							break;
						case 't' :
							tkStr += '\t';
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
			tk = LEX_STR;
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
			tk = LEX_STR;
		}
		else {
			final char oldCh = currCh;
			if (currCh != 0) {
				if (oldCh == '.') {
					tk = LEX_DOT;
				}
				else if (oldCh == ';') {
					tk = LEX_SEMICOLON;
				}
				else if (oldCh == '+') {
					tk = LEX_PLUS;
				}
				else if (oldCh == '-') {
					tk = LEX_MINUS;
				}
				else if (oldCh == '*') {
					tk = LEX_ASTERISK;
				}
				else if (oldCh == '/') {
					tk = LEX_SLASH;
				}
				else if (oldCh == '%') {
					tk = LEX_PERCENT;
				}
				else if (oldCh == '(') {
					tk = LEX_PARENTHESES_LEFT;
				}
				else if (oldCh == ')') {
					tk = LEX_PARENTHESES_RIGHT;
				}
				else if (oldCh == '{') {
					tk = LEX_BRACE_LEFT;
				}
				else if (oldCh == '}') {
					tk = LEX_BRACE_RIGHT;
				}
				else if (oldCh == '!') {
					tk = LEX_EXCLAMATION_POINT;
				}
				else if (oldCh == '<') {
					tk = LEX_ANGLE_BRACKETS_LEFT;
				}
				else if (oldCh == '>') {
					tk = LEX_ANGLE_BRACKETS_RIGHT;
				}
				else if (oldCh == '=') {
					tk = LEX_EQUAL;
				}
				else if (oldCh == '?') {
					tk = LEX_QUESTION;
				}
				else if (oldCh == '&') {
					tk = LEX_AND;
				}
				else if (oldCh == '|') {
					tk = LEX_OR;
				}
				else if (oldCh == '^') {
					tk = LEX_XOR;
				}
				else if (oldCh == ':') {
					tk = LEX_COLON;
				}
				else if (oldCh == '<') {
					tk = LEX_COMMA;
				}
				else if (oldCh == '[') {
					tk = LEX_SQUARE_BRACKET_LEFT;
				}
				else if (oldCh == ']') {
					tk = LEX_SQUARE_BRACKET_RIGHT;
				}
				getNextCh();
			}
			if ((oldCh == '=') && (currCh == '=')) {
				tk = LEX_EQUALEQUAL;
				getNextCh();
			}
			else if ((oldCh == '!') && (currCh == '=')) {
				tk = LEX_NEQUAL;
				getNextCh();
			}
			else if ((oldCh == '<') && (currCh == '=')) {
				tk = LEX_LEQUAL;
				getNextCh();
			}
			else if ((oldCh == '<') && (currCh == '<')) {
				tk = LEX_LSHIFT;
				getNextCh();
			}
			else if ((oldCh == '>') && (currCh == '=')) {
				tk = LEX_GEQUAL;
				getNextCh();
			}
			else if ((oldCh == '>') && (currCh == '>')) {
				tk = LEX_RSHIFT;
				getNextCh();
				if (currCh == '>') {
					tk = LEX_RSHIFTUNSIGNED;
					getNextCh();
				}
			}
			else if ((oldCh == '+') && (currCh == '=')) {
				tk = LEX_PLUSEQUAL;
				getNextCh();
			}
			else if ((oldCh == '-') && (currCh == '=')) {
				tk = LEX_MINUSEQUAL;
				getNextCh();
			}
			else if ((oldCh == '+') && (currCh == '+')) {
				tk = LEX_PLUSPLUS;
				getNextCh();
			}
			else if ((oldCh == '-') && (currCh == '-')) {
				tk = LEX_MINUSMINUS;
				getNextCh();
			}
			else if ((oldCh == '&') && (currCh == '=')) {
				tk = LEX_ANDEQUAL;
				getNextCh();
			}
			else if ((oldCh == '&') && (currCh == '&')) {
				tk = LEX_ANDAND;
				getNextCh();
			}
			else if ((oldCh == '|') && (currCh == '=')) {
				tk = LEX_OREQUAL;
				getNextCh();
			}
			else if ((oldCh == '|') && (currCh == '|')) {
				tk = LEX_OROR;
				getNextCh();
			}
			else if ((oldCh == '^') && (currCh == '=')) {
				tk = LEX_XOREQUAL;
				getNextCh();
			}
		}
		tokenLastEnd = tokenEnd;
		tokenEnd = dataPos - 3;
		return tk;
	}
	
	public boolean isEndOfFile() {
		return tk.equals(LEX_EOF);
	}
	
	public boolean statement(final boolean exe, final Vector<CScriptVar> scopes) throws Exception {
		boolean execute = exe;
		switch ( tk ) {
			case LEX_R_WHILE :
				getNextToken();
				if (tk.equals(LEX_PARENTHESES_LEFT)) {
					getNextToken();
					final int whileCondStart = tokenStart;
					boolean noexecute = false;
					CScriptVarLink cond = base(execute, scopes);
					boolean loopCond = execute && (cond.var.getBool());
					clean(cond);
					final LexicalAnalyzer whileCond = getSubLex(whileCondStart);
					match(LEX_PARENTHESES_RIGHT);
					final int whileBodyStart = tokenStart;
					if (loopCond) {
						execute = statement(execute, scopes);
					}
					else {
						noexecute = statement(noexecute, scopes);
					}
					final LexicalAnalyzer whileBody = getSubLex(whileBodyStart);
					while (loopCond) {
						whileCond.reset();
						cond = whileCond.base(execute, scopes);
						loopCond = execute && (cond.var.getBool());
						clean(cond);
						if (loopCond) {
							whileBody.reset();
							execute = whileBody.statement(execute, scopes);
						}
					}
					return execute;
				}
				throw new Exception(errorMessage(tk, LEX_PARENTHESES_LEFT));
			case LEX_R_IF :
				getNextToken();
				if (tk.equals(LEX_PARENTHESES_LEFT)) {
					getNextToken();
					final CScriptVarLink var = base(execute, scopes);
					match(LEX_PARENTHESES_RIGHT);
					final boolean cond = execute && var.var.getBool();
					clean(var);
					boolean noexecute = false;
					if (cond) {
						execute = statement(execute, scopes);
					}
					else {
						noexecute = statement(noexecute, scopes);
					}
					if (tk.equals(LEX_R_ELSE)) {
						getNextToken();
						if (cond) {
							noexecute = statement(noexecute, scopes);
						}
						else {
							execute = statement(execute, scopes);
						}
					}
					return execute;
				}
				throw new Exception(errorMessage(tk, LEX_PARENTHESES_LEFT));
				
			case LEX_R_FOR :
				getNextToken();
				if (tk.equals(LEX_PARENTHESES_LEFT)) {
					
					getNextToken();
					execute = statement(execute, scopes);
					final int forCondStart = tokenStart;
					boolean noexecute = false;
					CScriptVarLink cond = base(execute, scopes); // condition
					boolean loopCond = execute && (cond.var.getBool());
					clean(cond);
					final LexicalAnalyzer forCond = getSubLex(forCondStart);
					assertSemicolon();
					final int forIterStart = tokenStart;
					clean(base(noexecute, scopes)); // iterator
					final LexicalAnalyzer forIter = getSubLex(forIterStart);
					match(LEX_PARENTHESES_RIGHT);
					final int forBodyStart = tokenStart;
					if (loopCond) {
						execute = statement(execute, scopes);
					}
					else {
						noexecute = statement(noexecute, scopes);
					}
					final LexicalAnalyzer forBody = getSubLex(forBodyStart);
					if (loopCond) {
						forIter.reset();
						clean(forIter.base(execute, scopes));
					}
					while (execute && loopCond) {
						forCond.reset();
						cond = forCond.base(execute, scopes);
						loopCond = cond.var.getBool();
						clean(cond);
						if (execute && loopCond) {
							forBody.reset();
							execute = forBody.statement(execute, scopes);
						}
						if (execute && loopCond) {
							forIter.reset();
							clean(forIter.base(execute, scopes));
						}
					}
					return execute;
				}
				throw new Exception(errorMessage(tk, LEX_PARENTHESES_LEFT));
				
			case LEX_R_VAR :
				getNextToken();
				while (!tk.equals(LEX_SEMICOLON)) {
					CScriptVarLink a = null;
					if (execute) {
						a = scopes.lastElement().findChildOrCreate(tkStr);
					}
					assertID();
					while (tk.equals(LEX_DOT)) {
						getNextToken();
						if (execute) {
							final CScriptVarLink lastA = a;
							a = lastA.var.findChildOrCreate(tkStr);
						}
						assertID();
					}
					
					if (tk.equals(LEX_EQUAL)) {
						getNextToken();
						final CScriptVarLink var = base(execute, scopes);
						if (execute) {
							a.replaceWith(var);
						}
						clean(var);
					}
					if (tk.equals(LEX_COMMA)) {
						getNextToken();
					}
					else if (!tk.equals(LEX_EQUAL)) {
						throw new Exception(errorMessage(tk, LEX_COMMA));
					}
				}
				assertSemicolon();
				return execute;
			case LEX_STR :
			case LEX_INT :
			case LEX_FLOAT :
			case LEX_ID :
				clean(base(execute, scopes));
				assertSemicolon();
				return execute;
				
			case LEX_SEMICOLON :
			case LEX_EOF :
				getNextToken();
				return execute;
			default :
				throw new Exception(errorMessage(tk, LEX_EOF));
		}
	}
	
	private CScriptVarLink base(final boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink condition = condition(execute, scopes);
		final CScriptVarLink logic = logic(execute, scopes, condition);
		final CScriptVarLink ternary = ternary(execute, scopes, logic);
		switch ( tk ) {
			case LEX_EQUAL :
			case LEX_MINUSEQUAL :
			case LEX_PLUSEQUAL :
				CScriptVarLink lhs = ternary;
				if (execute && !lhs.owned) {
					if (lhs.name.length() > 0) {
						final CScriptVarLink realLhs = root.addChildNoDup(lhs.name, lhs.var);
						clean(lhs);
						lhs = realLhs;
					}
					else {
						System.out.println("Trying to assign to an un-named type");
					}
				}
				
				getNextToken();
				final CScriptVarLink rhs = base(execute, scopes);
				if (execute) {
					switch ( tk ) {
						case LEX_EQUAL :
							lhs.replaceWith(rhs);
							break;
						case LEX_MINUSEQUAL :
							lhs.replaceWith(lhs.var.mathsOp(rhs.var, LEX_MINUS));
							break;
						case LEX_PLUSEQUAL :
							lhs.replaceWith(lhs.var.mathsOp(rhs.var, LEX_PLUS));
							break;
						default :
							break;
					}
				}
				clean(rhs);
				return lhs;
				
			default :
				return ternary;
		}
	}
	
	private CScriptVarLink condition(final boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		final CScriptVarLink e1 = expression(execute, scopes);
		CScriptVarLink a = shift(execute, scopes, e1);
		CScriptVarLink b;
		while (tk.equals(LEX_ANGLE_BRACKETS_LEFT) || tk.equals(LEX_ANGLE_BRACKETS_RIGHT) || tk.equals(LEX_EQUALEQUAL)
		        || tk.equals(LEX_LEQUAL) || tk.equals(LEX_NEQUAL) || tk.equals(LEX_GEQUAL)) {
			final String op = tk;
			getNextToken();
			final CScriptVarLink e2 = expression(execute, scopes);
			b = shift(execute, scopes, e2);
			if (execute) {
				final CScriptVar res = a.var.mathsOp(b.var, op);
				a = a.createLink(res);
			}
			clean(b);
		}
		return a;
	}
	
	private CScriptVarLink expression(final boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		CScriptVarLink a;
		if (tk.equals(LEX_MINUS)) {
			getNextToken();
			a = term(execute, scopes, unary(execute, scopes));
			final CScriptVar zero = new ScriptInteger(0);
			final CScriptVar res = zero.mathsOp(a.var, LEX_MINUS);
			a = a.createLink(res);
		}
		else {
			a = term(execute, scopes, unary(execute, scopes));
		}
		while (tk.equals(LEX_MINUS) || tk.equals(LEX_MINUSMINUS) || tk.equals(LEX_PLUS) || tk.equals(LEX_PLUSPLUS)) {
			final String op = tk;
			getNextToken();
			switch ( op ) {
				case LEX_PLUSPLUS :
					if (execute) {
						final CScriptVar one = new ScriptInteger(1);
						final CScriptVar res = a.var.mathsOp(one, LEX_PLUS);
						final CScriptVarLink oldValue = new CScriptVarLink(a.var, TINYJS_TEMP_NAME);
						// in-place add/subtract
						a.replaceWith(res);
						clean(a);
						a = oldValue;
					}
					break;
				
				case LEX_PLUS : {
					final CScriptVarLink unary = unary(execute, scopes);
					final CScriptVarLink b = term(execute, scopes, unary);
					if (execute) {
						// not in-place, so just replace
						final CScriptVar res = a.var.mathsOp(b.var, op);
						a = a.createLink(res);
					}
					clean(b);
				}
					break;
				
				case LEX_MINUSMINUS :
					if (execute) {
						final CScriptVar one = new ScriptInteger(1);
						final CScriptVar res = a.var.mathsOp(one, LEX_MINUS);
						final CScriptVarLink oldValue = new CScriptVarLink(a.var, TINYJS_TEMP_NAME);
						// in-place add/subtract
						a.replaceWith(res);
						clean(a);
						a = oldValue;
					}
					break;
				
				case LEX_MINUS : {
					final CScriptVarLink unary = unary(execute, scopes);
					final CScriptVarLink b = term(execute, scopes, unary);
					if (execute) {
						// not in-place, so just replace
						final CScriptVar res = a.var.mathsOp(b.var, op);
						a = a.createLink(res);
					}
					clean(b);
				}
					break;
				
				default :
					break;
			}
		}
		return a;
	}
	
	private CScriptVarLink unary(final boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		CScriptVarLink a;
		getNextToken();
		a = factor(execute, scopes);
		if (tk.equals(LEX_EXCLAMATION_POINT)) {
			if (execute) {
				final CScriptVar zero = new ScriptInteger(0);
				final CScriptVar res = a.var.mathsOp(zero, LEX_EQUALEQUAL);
				a = a.createLink(res);
			}
		}
		return a;
	}
	
	private CScriptVarLink factor(final boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		switch ( tk ) {
			case LEX_PARENTHESES_LEFT : {
				getNextToken();
				final CScriptVarLink a = base(execute, scopes);
				match(LEX_PARENTHESES_RIGHT);
				return a;
			}
			case LEX_BRACE_LEFT : {
				final ScriptObject contents = new ScriptObject();
				getNextToken();
				while (!tk.equals(LEX_BRACE_RIGHT)) {
					final String id = tkStr;
					switch ( tk ) {
						case LEX_ID :
						case LEX_STR :
							getNextToken();
							break;
						default :
							throw new Exception(errorMessage(tk, LEX_ID));
					}
					assertColon();
					if (execute) {
						final CScriptVarLink a = base(execute, scopes);
						contents.addChild(id, a.var);
						clean(a);
					}
					switch ( tk ) {
						case LEX_COMMA :
							getNextToken();
							break;
						case LEX_BRACE_RIGHT :
							break;
						default :
							throw new Exception(errorMessage(tk, LEX_COMMA));
					}
				}
				match(LEX_BRACE_RIGHT);
				return new CScriptVarLink(contents, TINYJS_TEMP_NAME);
			}
			case LEX_SQUARE_BRACKET_LEFT : {
				final ScriptArray contents = new ScriptArray();
				getNextToken();
				int idx = 0;
				while (!tk.equals(LEX_SQUARE_BRACKET_RIGHT)) {
					if (execute) {
						final CScriptVarLink a = base(execute, scopes);
						contents.addChild(String.valueOf(idx), a.var);
						clean(a);
					}
					switch ( tk ) {
						case LEX_COMMA :
							getNextToken();
							break;
						case LEX_SQUARE_BRACKET_RIGHT :
							break;
						default :
							throw new Exception(errorMessage(tk, LEX_COMMA));
					}
					idx++;
				}
				getNextToken();
				return new CScriptVarLink(contents, TINYJS_TEMP_NAME);
			}
			case LEX_ID : {
				CScriptVarLink a = execute ? findInScopes(tkStr, scopes) : new CScriptVarLink(new ScriptUndefined(),
				        TINYJS_TEMP_NAME);
				CScriptVar parent = null;
				
				if (execute && (a == null)) {
					a = new CScriptVarLink(new ScriptUndefined(), tkStr);
				}
				assertID();
				while (tk.equals(LEX_DOT) || tk.equals(LEX_PARENTHESES_LEFT) || tk.equals(LEX_SQUARE_BRACKET_LEFT)) {
					if (tk.equals(LEX_PARENTHESES_LEFT)) {
						a = functionCall(execute, a, parent, scopes);
					}
					else if (tk.equals(LEX_DOT)) {
						getNextToken();
						if (execute) {
							final String name = tkStr;
							CScriptVarLink child = a.var.findChild(name);
							if (child == null) {
								child = findInParentClasses(a.var, name);
							}
							if (child == null) {
								if (a.var.isArray() && (name.equals("length"))) {
									child = new CScriptVarLink(new ScriptInteger(a.var.getArrayLength()),
									        TINYJS_TEMP_NAME);
								}
								else if (a.var.isString() && (name.equals("length"))) {
									child = new CScriptVarLink(new ScriptInteger(a.var.getString().length()),
									        TINYJS_TEMP_NAME);
								}
								else {
									child = a.var.addChild(name, null);
								}
							}
							parent = a.var;
							a = child;
						}
						assertID();
					}
					else if (tk.equals(LEX_SQUARE_BRACKET_LEFT)) {
						getNextToken();
						final CScriptVarLink index = base(execute, scopes);
						match(LEX_SQUARE_BRACKET_RIGHT);
						if (execute) {
							final CScriptVarLink child = a.var.findChildOrCreate(index.var.getString());
							parent = a.var;
							a = child;
						}
						clean(index);
					}
				}
				return a;
			}
			
			case "undefined" :
				getNextToken();
				return new CScriptVarLink(new ScriptUndefined(), TINYJS_TEMP_NAME);
				
			case LEX_R_NULL :
				getNextToken();
				return new CScriptVarLink(new ScriptNull(), TINYJS_TEMP_NAME);
				
			case LEX_INT :
			case LEX_FLOAT :
			case LEX_STR :
				final ScriptDouble a = new ScriptDouble(tkStr);
				getNextToken();
				return new CScriptVarLink(a, TINYJS_TEMP_NAME);
				
			case LEX_R_TRUE :
				getNextToken();
				return new CScriptVarLink(new ScriptInteger(1), TINYJS_TEMP_NAME);
				
			case LEX_R_FALSE :
				getNextToken();
				return new CScriptVarLink(new ScriptInteger(0), TINYJS_TEMP_NAME);
				
			case LEX_EOF :
				getNextToken();
				return null;
				
			case LEX_R_NEW :
				getNextToken();
				final String className = tkStr;
				if (execute) {
					final CScriptVarLink objClassOrFunc = findInScopes(className, scopes);
					if (objClassOrFunc == null) {
						System.out.print(className + " is not a valid class name");
						return new CScriptVarLink(new ScriptUndefined(), TINYJS_TEMP_NAME);
					}
					assertID();
					final ScriptObject obj = new ScriptObject();
					final CScriptVarLink objLink = new CScriptVarLink(obj, TINYJS_TEMP_NAME);
					if (objClassOrFunc.var.isFunction()) {
						clean(functionCall(execute, objClassOrFunc, obj, scopes));
					}
					else {
						obj.addChild(TINYJS_PROTOTYPE_CLASS, objClassOrFunc.var);
						if (tk.equals(LEX_PARENTHESES_LEFT)) {
							getNextToken();
							match(LEX_PARENTHESES_RIGHT);
						}
					}
					return objLink;
				}
				assertID();
				if (tk.equals(LEX_PARENTHESES_LEFT)) {
					getNextToken();
					match(LEX_PARENTHESES_RIGHT);
				}
			default :
				throw new Exception(errorMessage(tk, LEX_EOF));
				
		}
	}
	
	private CScriptVarLink functionCall(final boolean execute, final CScriptVarLink function, final CScriptVar parent,
	        final Vector<CScriptVar> scopes) throws Exception {
		if (execute) {
			if (!function.var.isFunction()) {
				throw new Exception("Expecting '" + function.name + "' to be a function");
			}
			return startFunctionCall(execute, function, parent, scopes);
		}
		if (tk.equals(LEX_PARENTHESES_LEFT)) {
			getNextToken();
			while (!tk.equals(LEX_PARENTHESES_RIGHT)) {
				final CScriptVarLink value = base(execute, scopes);
				clean(value);
				hasNextComma();
			}
			getNextToken();
			if (tk.equals(LEX_BRACE_LEFT)) {
				if (tk.equals(LEX_BRACE_LEFT)) {
					block(execute, scopes);
				}
				else {
					throw new Exception(errorMessage(tk, LEX_BRACE_LEFT));
				}
			}
			return function;
		}
		throw new Exception(errorMessage(tk, LEX_PARENTHESES_LEFT));
		
	}
	
	private CScriptVarLink startFunctionCall(boolean execute, final CScriptVarLink function, final CScriptVar parent,
	        final Vector<CScriptVar> scopes) throws Exception {
		if (tk.equals(LEX_PARENTHESES_LEFT)) {
			getNextToken();
			final ScriptFunction functionRoot = new ScriptFunction();
			if (parent != null) {
				functionRoot.addChildNoDup("this", parent);
			}
			CScriptVarLink v = (function.var.firstChild);
			while (v != null) {
				final CScriptVarLink value = base(execute, scopes);
				if (execute) {
					if (value.var.isBasic()) {
						functionRoot.addChild(v.name, value.var.deepCopy());
					}
					else {
						functionRoot.addChild(v.name, value.var);
					}
				}
				clean(value);
				hasNextComma();
				v = v.nextSibling;
			}
			match(LEX_PARENTHESES_RIGHT);
			
			CScriptVarLink returnVar = null;
			final CScriptVarLink returnVarLink = functionRoot.addChild("return", null);
			scopes.add(functionRoot);
			Exception exception = null;
			try {
				//TODO
				final String op = new LexicalAnalyzer(root, function.var.getString()).tk;
				if (op.equals(LEX_BRACE_LEFT)) {
					execute = block(execute, scopes);
					execute = true;
				}
				else {
					throw new Exception(errorMessage(op, LEX_BRACE_LEFT));
				}
				
			}
			catch (final Exception e) {
				exception = e;
			}
			
			if (exception != null) {
				throw exception;
			}
			scopes.remove(scopes.size() - 1);
			returnVar = new CScriptVarLink(returnVarLink.var, TINYJS_TEMP_NAME);
			functionRoot.removeLink(returnVarLink);
			functionRoot.close();
			return returnVar;
		}
		throw new Exception(errorMessage(tk, LEX_PARENTHESES_LEFT));
	}
	
	private void hasNextComma() throws Exception {
		switch ( tk ) {
			case LEX_COMMA :
				getNextToken();
				break;
			case LEX_PARENTHESES_RIGHT :
				break;
			default :
				throw new Exception(errorMessage(tk, LEX_COMMA));
		}
	}
	
	private CScriptVarLink findInParentClasses(final CScriptVar object, final String name) {
		CScriptVarLink parentClass = object.findChild(TINYJS_PROTOTYPE_CLASS);
		while (parentClass != null) {
			final CScriptVarLink implementation = parentClass.var.findChild(name);
			if (implementation != null) {
				return implementation;
			}
			parentClass = parentClass.var.findChild(TINYJS_PROTOTYPE_CLASS);
		}
		if (object.isString()) {
			final CScriptVarLink implementation = root.stringClass.findChild(name);
			if (implementation != null) {
				return implementation;
			}
		}
		if (object.isArray()) {
			final CScriptVarLink implementation = root.arrayClass.findChild(name);
			if (implementation != null) {
				return implementation;
			}
		}
		final CScriptVarLink implementation = root.objectClass.findChild(name);
		if (implementation != null) {
			return implementation;
		}
		return null;
	}
	
	private CScriptVarLink term(final boolean execute, final Vector<CScriptVar> scopes, final CScriptVarLink unary)
	        throws Exception {
		CScriptVarLink a = unary;
		while (tk.equals(LEX_SLASH) || tk.equals(LEX_PERCENT) || tk.equals(LEX_ASTERISK)) {
			final String op = tk;
			getNextToken();
			final CScriptVarLink b = unary(execute, scopes);
			if (execute) {
				final CScriptVar res = a.var.mathsOp(b.var, op);
				a = a.createLink(res);
			}
			clean(b);
		}
		return a;
	}
	
	private CScriptVarLink shift(final boolean execute, final Vector<CScriptVar> scopes, final CScriptVarLink expression)
	        throws Exception {
		switch ( tk ) {
			case LEX_LSHIFT :
			case LEX_RSHIFT :
			case LEX_RSHIFTUNSIGNED :
				final CScriptVarLink a = expression;
				getNextToken();
				final CScriptVarLink b = base(execute, scopes);
				clean(b);
				if (execute) {
					final int shift = b.var.getInt();
					switch ( tk ) {
						case LEX_LSHIFT :
							a.var.setInt(a.var.getInt() << shift);
							break;
						
						case LEX_RSHIFT :
							a.var.setInt(a.var.getInt() >> shift);
							break;
						
						case LEX_RSHIFTUNSIGNED :
							a.var.setInt(a.var.getInt() >>> shift);
							break;
						
						default :
							break;
					}
					a.var.setInt(a.var.getInt() << shift);
				}
				return a;
			default :
				return expression;
		}
	}
	
	private CScriptVarLink logic(final boolean execute, final Vector<CScriptVar> scopes, final CScriptVarLink condition)
	        throws Exception {
		CScriptVarLink a = condition;
		while (tk.equals(LEX_OROR) || tk.equals(LEX_OR) || tk.equals(LEX_ANDAND) || tk.equals(LEX_AND)
		        || tk.equals(LEX_XOR)) {
			a = logicNext(execute, scopes, a);
		}
		return a;
	}
	
	private CScriptVarLink logicNext(final boolean execute, final Vector<CScriptVar> scopes, final CScriptVarLink iniA)
	        throws Exception {
		CScriptVarLink a = iniA;
		switch ( tk ) {
			case LEX_AND : {
				final CScriptVarLink b;
				getNextToken();
				b = condition(execute, scopes);
				if (execute) {
					final CScriptVar res = a.var.mathsOp(b.var, LEX_AND);
					a = a.createLink(res);
				}
				clean(b);
				return a;
			}
			
			case LEX_ANDAND : {
				getNextToken();
				final boolean shortCircuit = !a.var.getBool();
				CScriptVarLink b = condition(shortCircuit ? false : execute, scopes);
				if (execute && !shortCircuit) {
					final CScriptVar newa = new ScriptInteger(a.var.getBool() ? 1 : 0);
					final CScriptVar newb = new ScriptInteger(b.var.getBool() ? 1 : 0);
					a = a.createLink(newa);
					b = b.createLink(newb);
					final CScriptVar res = a.var.mathsOp(b.var, LEX_AND);
					a = a.createLink(res);
				}
				clean(b);
				return a;
			}
			
			case LEX_OR : {
				final CScriptVarLink b;
				getNextToken();
				b = condition(execute, scopes);
				if (execute) {
					final CScriptVar res = a.var.mathsOp(b.var, LEX_OR);
					a = a.createLink(res);
				}
				clean(b);
				return a;
			}
			
			case LEX_OROR : {
				getNextToken();
				final boolean shortCircuit = a.var.getBool();
				CScriptVarLink b = condition(shortCircuit ? false : execute, scopes);
				if (execute && !shortCircuit) {
					final CScriptVar newa = new ScriptInteger(a.var.getBool() ? 1 : 0);
					final CScriptVar newb = new ScriptInteger(b.var.getBool() ? 1 : 0);
					a = a.createLink(newa);
					b = b.createLink(newb);
					final CScriptVar res = a.var.mathsOp(b.var, LEX_OR);
					a = a.createLink(res);
				}
				clean(b);
				return a;
			}
			
			case LEX_XOR : {
				final CScriptVarLink b;
				getNextToken();
				b = condition(execute, scopes);
				if (execute) {
					final CScriptVar res = a.var.mathsOp(b.var, LEX_XOR);
					a = a.createLink(res);
				}
				clean(b);
				return a;
			}
			
			default :
				return a;
		}
	}
	
	private CScriptVarLink ternary(final boolean execute, final Vector<CScriptVar> scopes, final CScriptVarLink logic)
	        throws Exception {
		if (tk.equals(LEX_QUESTION)) {
			CScriptVarLink lhs = logic;
			final boolean noexec = false;
			getNextToken();
			if (!execute) {
				clean(lhs);
				clean(base(noexec, scopes));
				assertColon();
				clean(base(noexec, scopes));
			}
			else {
				final boolean first = lhs.var.getBool();
				clean(lhs);
				if (first) {
					lhs = base(execute, scopes);
					assertColon();
					clean(base(noexec, scopes));
				}
				else {
					clean(base(noexec, scopes));
					assertColon();
					lhs = base(execute, scopes);
				}
			}
			return lhs;
		}
		return logic;
	}
	
	private void assertID() throws Exception {
		if (tk.equals(LEX_ID)) {
			getNextToken();
		}
		else {
			throw new Exception(errorMessage(tk, LEX_ID));
		}
	}
	
	private void assertColon() throws Exception {
		if (tk.equals(LEX_COLON)) {
			getNextToken();
		}
		else {
			throw new Exception(errorMessage(tk, LEX_COLON));
		}
	}
	
	private void assertSemicolon() throws Exception {
		if (tk.equals(LEX_SEMICOLON)) {
			getNextToken();
		}
		else {
			throw new Exception(errorMessage(tk, LEX_SEMICOLON));
		}
	}
	
	private boolean block(boolean execute, final Vector<CScriptVar> scopes) throws Exception {
		getNextToken();
		if (execute) {
			while (!isEndOfFile() && !tk.equals(LEX_BRACE_RIGHT)) {
				execute = statement(execute, scopes);
			}
			match(LEX_BRACE_RIGHT);
		}
		else {
			int brackets = 1;
			while (!isEndOfFile() && (brackets != 0)) {
				if (tk.equals(LEX_OROR)) {
					brackets++;
				}
				else if (tk.equals(LEX_BRACE_RIGHT)) {
					brackets--;
				}
				getNextToken();
			}
		}
		return execute;
		
	}
}
