package frameWork.script.lexicalAnalyzer;

import static frameWork.script.Util.*;

import java.util.Vector;

import frameWork.script.CScriptVarLink;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptInteger;
import frameWork.script.scriptVar.ScriptUndefined;

public class LexicalId extends Lexical {
	@Override
	public String getTokenStr() {
		return "ID";
	}
	
	@Override
	public boolean statement(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes));
		lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
		return execute;
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		CScriptVarLink a = execute ? findInScopes(lexicalAnalyzer.tkStr, scopes) : new CScriptVarLink(
		        new ScriptUndefined(), TINYJS_TEMP_NAME);
		CScriptVar parent = null;
		
		if (execute && (a == null)) {
			a = new CScriptVarLink(new ScriptUndefined(), lexicalAnalyzer.tkStr);
		}
		lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
		while (lexicalAnalyzer.tk.isIdFactorNext()) {
			if (lexicalAnalyzer.tk.isParenthesesLeft()) {
				a = lexicalAnalyzer.tk.functionCall(lexicalAnalyzer, execute, a, parent, scopes);
			}
			else if (lexicalAnalyzer.tk.isDot()) {
				lexicalAnalyzer.getNextToken();
				if (execute) {
					final String name = lexicalAnalyzer.tkStr;
					CScriptVarLink child = a.var.findChild(name);
					if (child == null) {
						child = findInParentClasses(lexicalAnalyzer, a.var, name);
					}
					if (child == null) {
						if (a.var.isArray() && (name.equals("length"))) {
							child = new CScriptVarLink(new ScriptInteger(a.var.getArrayLength()), TINYJS_TEMP_NAME);
						}
						else if (a.var.isString() && (name.equals("length"))) {
							child = new CScriptVarLink(new ScriptInteger(a.var.getString().length()), TINYJS_TEMP_NAME);
						}
						else {
							child = a.var.addChild(name, null);
						}
					}
					parent = a.var;
					a = child;
				}
				lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
			}
			else if (lexicalAnalyzer.tk.isSquareBracketLeft()) {
				lexicalAnalyzer.getNextToken();
				final CScriptVarLink index = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
				lexicalAnalyzer.match(Lexical.LEX_SQUARE_BRACKET_RIGHT);
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
	
	@Override
	public void factorBraceLeftMatch(final LexicalAnalyzer lexicalAnalyzer) {
		lexicalAnalyzer.getNextToken();
	}
	
	private CScriptVarLink findInParentClasses(final LexicalAnalyzer lexicalAnalyzer, final CScriptVar object,
	        final String name) {
		CScriptVarLink parentClass = object.findChild(TINYJS_PROTOTYPE_CLASS);
		while (parentClass != null) {
			final CScriptVarLink implementation = parentClass.var.findChild(name);
			if (implementation != null) {
				return implementation;
			}
			parentClass = parentClass.var.findChild(TINYJS_PROTOTYPE_CLASS);
		}
		if (object.isString()) {
			final CScriptVarLink implementation = lexicalAnalyzer.root.stringClass.findChild(name);
			if (implementation != null) {
				return implementation;
			}
		}
		if (object.isArray()) {
			final CScriptVarLink implementation = lexicalAnalyzer.root.arrayClass.findChild(name);
			if (implementation != null) {
				return implementation;
			}
		}
		final CScriptVarLink implementation = lexicalAnalyzer.root.objectClass.findChild(name);
		if (implementation != null) {
			return implementation;
		}
		return null;
	}
	
	@Override
	public void assertID(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		lexicalAnalyzer.getNextToken();
	}
	
}
