package frameWork.core.viewCompiler.script.scriptVar;

import java.util.Vector;


public abstract class Util {
	//"undefined" "return"
	public static final String LEX_EOF = "EOF";
	public static final String LEX_ID = "ID";
	
	public static final String LEX_INT = "INT";
	public static final String LEX_FLOAT = "FLOAT";
	public static final String LEX_STR = "STRING";
	
	public static final String LEX_EQUALEQUAL = "==";
	
	public static final String LEX_NEQUAL = "!=";
	public static final String LEX_LEQUAL = "<=";
	public static final String LEX_LSHIFT = "<<";
	public static final String LEX_GEQUAL = ">=";
	public static final String LEX_RSHIFT = ">>";
	public static final String LEX_RSHIFTUNSIGNED = ">>>";
	
	public static final String LEX_PLUS = "+";
	
	public static final String LEX_PLUSEQUAL = "+=";
	
	public static final String LEX_MINUS = "-";
	
	public static final String LEX_MINUSEQUAL = "-=";
	public static final String LEX_PLUSPLUS = "++";
	public static final String LEX_MINUSMINUS = "--";
	
	public static final String LEX_AND = "&";
	
	public static final String LEX_ANDEQUAL = "&=";
	public static final String LEX_ANDAND = "&&";
	
	public static final String LEX_OR = "|";
	
	public static final String LEX_OREQUAL = "|=";
	public static final String LEX_OROR = "||";
	
	public static final String LEX_XOR = "^";
	
	public static final String LEX_XOREQUAL = "^=";
	public static final String LEX_R_IF = "if";
	public static final String LEX_R_ELSE = "else";
	public static final String LEX_R_DO = "do";
	public static final String LEX_R_WHILE = "while";
	public static final String LEX_R_FOR = "for";
	public static final String LEX_R_BREAK = "break";
	public static final String LEX_R_CONTINUE = "continue";
	public static final String LEX_R_VAR = "var";
	public static final String LEX_R_TRUE = "true";
	public static final String LEX_R_FALSE = "false";
	public static final String LEX_R_NULL = "null";
	public static final String LEX_R_NEW = "new";
	public static final String LEX_DOT = ".";
	
	public static final String LEX_SEMICOLON = ";";
	
	public static final String LEX_ASTERISK = "*";
	public static final String LEX_SLASH = "/";
	
	public static final String LEX_PERCENT = "%";
	
	public static final String LEX_PARENTHESES_LEFT = "(";
	public static final String LEX_PARENTHESES_RIGHT = ")";
	public static final String LEX_BRACE_LEFT = "{";
	public static final String LEX_BRACE_RIGHT = "}";
	
	public static final String LEX_EXCLAMATION_POINT = "!";
	public static final String LEX_ANGLE_BRACKETS_LEFT = "<";
	public static final String LEX_ANGLE_BRACKETS_RIGHT = ">";
	public static final String LEX_EQUAL = "=";
	public static final String LEX_QUESTION = "?";
	
	public static final String LEX_COLON = ":";
	
	public static final String LEX_COMMA = ",";
	
	public static final String LEX_SQUARE_BRACKET_LEFT = "[";
	public static final String LEX_SQUARE_BRACKET_RIGHT = "]";
	public static final String TINYJS_BLANK_DATA = "";
	public static final String TINYJS_TEMP_NAME = "";
	
	public static final String TINYJS_RETURN_VAR = "return";
	public static final String TINYJS_PROTOTYPE_CLASS = "prototype";
	
	public static void clean(final CScriptVarLink x) throws Exception {
		if ((x != null) && (!x.owned)) {
			x.close();
		}
	}
	
	public static boolean isWhitespace(final char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r');
	}
	
	public static boolean isAlpha(final char ch) {
		return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_');
	}
	
	public static boolean isNumeric(final char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	
	public static boolean isHexadecimal(final char ch) {
		return ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'f')) || ((ch >= 'A') && (ch <= 'F'));
	}
	
	public static CScriptVarLink findInScopes(final String childName, final Vector<CScriptVar> scopes) {
		for (int s = scopes.size() - 1; s >= 0; s--) {
			final CScriptVarLink v = scopes.get(s).findChild(childName);
			if (v != null) {
				return v;
			}
		}
		return null;
	}
}
