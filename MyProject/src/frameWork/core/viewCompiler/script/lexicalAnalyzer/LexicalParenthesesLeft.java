package frameWork.core.viewCompiler.script.lexicalAnalyzer;

import static frameWork.core.viewCompiler.script.Util.*;

import java.util.Vector;

import frameWork.core.viewCompiler.script.CScriptVarLink;
import frameWork.core.viewCompiler.script.scriptVar.CScriptVar;
import frameWork.core.viewCompiler.script.scriptVar.ScriptFunction;

public class LexicalParenthesesLeft extends Lexical {
	@Override
	public String getTokenStr() {
		return "(";
	}
	
	@Override
	public CScriptVarLink factor(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink a = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
		return a;
	}
	
	@Override
	public boolean isIdFactorNext() {
		return true;
	}
	
	@Override
	public boolean isParenthesesLeft() {
		return true;
	}
	
	@Override
	public void newFactor(final LexicalAnalyzer lexicalAnalyzer) throws Exception {
		lexicalAnalyzer.getNextToken();
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
	}
	
	@Override
	public void parseFunctionArguments(final LexicalAnalyzer lexicalAnalyzer, final ScriptFunction funcVar)
	        throws Exception {
		lexicalAnalyzer.getNextToken();
		while (lexicalAnalyzer.tk.isNotParenthesesRight()) {
			funcVar.addChildNoDup(lexicalAnalyzer.tkStr, null);
			lexicalAnalyzer.tk.assertID(lexicalAnalyzer);
			lexicalAnalyzer.tk.hasNextComma(lexicalAnalyzer);
		}
		lexicalAnalyzer.getNextToken();
	}
	
	@Override
	public void functionCallAfter(final LexicalAnalyzer lexicalAnalyzer, final boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		while (lexicalAnalyzer.tk.isNotParenthesesRight()) {
			final CScriptVarLink value = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
			clean(value);
			lexicalAnalyzer.tk.hasNextComma(lexicalAnalyzer);
		}
		lexicalAnalyzer.getNextToken();
		lexicalAnalyzer.tk.functionCallBlock(lexicalAnalyzer, execute, scopes);
	}
	
	@Override
	public boolean startWhileStatement(final LexicalAnalyzer lexicalAnalyzer, boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		final int whileCondStart = lexicalAnalyzer.tokenStart;
		boolean noexecute = false;
		CScriptVarLink cond = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		boolean loopCond = execute && (cond.var.getBool());
		clean(cond);
		final LexicalAnalyzer whileCond = lexicalAnalyzer.getSubLex(whileCondStart);
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
		final int whileBodyStart = lexicalAnalyzer.tokenStart;
		if (loopCond) {
			execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
		}
		else {
			noexecute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, noexecute, scopes);
		}
		final LexicalAnalyzer whileBody = lexicalAnalyzer.getSubLex(whileBodyStart);
		while (loopCond) {
			whileCond.reset();
			cond = whileCond.tk.base(whileCond, execute, scopes);
			loopCond = execute && (cond.var.getBool());
			clean(cond);
			if (loopCond) {
				whileBody.reset();
				execute = whileBody.tk.statement(whileBody, execute, scopes);
			}
		}
		return execute;
		
	}
	
	@Override
	public boolean startForStatement(final LexicalAnalyzer lexicalAnalyzer, boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
		final int forCondStart = lexicalAnalyzer.tokenStart;
		boolean noexecute = false;
		CScriptVarLink cond = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes); // condition
		boolean loopCond = execute && (cond.var.getBool());
		clean(cond);
		final LexicalAnalyzer forCond = lexicalAnalyzer.getSubLex(forCondStart);
		lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
		final int forIterStart = lexicalAnalyzer.tokenStart;
		clean(lexicalAnalyzer.tk.base(lexicalAnalyzer, noexecute, scopes)); // iterator
		final LexicalAnalyzer forIter = lexicalAnalyzer.getSubLex(forIterStart);
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
		final int forBodyStart = lexicalAnalyzer.tokenStart;
		if (loopCond) {
			execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
		}
		else {
			noexecute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, noexecute, scopes);
		}
		final LexicalAnalyzer forBody = lexicalAnalyzer.getSubLex(forBodyStart);
		if (loopCond) {
			forIter.reset();
			clean(forIter.tk.base(forIter, execute, scopes));
		}
		while (execute && loopCond) {
			forCond.reset();
			cond = forCond.tk.base(forCond, execute, scopes);
			loopCond = cond.var.getBool();
			clean(cond);
			if (execute && loopCond) {
				forBody.reset();
				execute = forBody.tk.statement(forBody, execute, scopes);
			}
			if (execute && loopCond) {
				forIter.reset();
				clean(forIter.tk.base(forIter, execute, scopes));
			}
		}
		return execute;
	}
	
	@Override
	public boolean startIfStatement(final LexicalAnalyzer lexicalAnalyzer, boolean execute,
	        final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		final CScriptVarLink var = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
		final boolean cond = execute && var.var.getBool();
		clean(var);
		boolean noexecute = false;
		if (cond) {
			execute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, execute, scopes);
		}
		else {
			noexecute = lexicalAnalyzer.tk.statement(lexicalAnalyzer, noexecute, scopes);
		}
		return lexicalAnalyzer.tk.startIfElseStatement(lexicalAnalyzer, execute, noexecute, scopes, cond);
	}
	
	@Override
	public CScriptVarLink startFunctionCall(final LexicalAnalyzer lexicalAnalyzer, boolean execute,
	        final CScriptVarLink function, final CScriptVar parent, final Vector<CScriptVar> scopes) throws Exception {
		lexicalAnalyzer.getNextToken();
		final ScriptFunction functionRoot = new ScriptFunction();
		if (parent != null) {
			functionRoot.addChildNoDup("this", parent);
		}
		CScriptVarLink v = (function.var.firstChild);
		while (v != null) {
			final CScriptVarLink value = lexicalAnalyzer.tk.base(lexicalAnalyzer, execute, scopes);
			if (execute) {
				if (value.var.isBasic()) {
					functionRoot.addChild(v.name, value.var.deepCopy());
				}
				else {
					functionRoot.addChild(v.name, value.var);
				}
			}
			clean(value);
			lexicalAnalyzer.tk.hasNextComma(lexicalAnalyzer);
			v = v.nextSibling;
		}
		lexicalAnalyzer.match(Lexical.LEX_PARENTHESES_RIGHT);
		
		CScriptVarLink returnVar = null;
		final CScriptVarLink returnVarLink = functionRoot.addChild("return", null);
		scopes.add(functionRoot);
		if (function.var.isNative()) {
			function.var.jsCallback.run(functionRoot);
		}
		else {
			Exception exception = null;
			try {
				execute = new LexicalAnalyzer(lexicalAnalyzer.root, function.var.getString()).tk.block(lexicalAnalyzer,
				        execute, scopes);
				execute = true;
			}
			catch (final Exception e) {
				exception = e;
			}
			
			if (exception != null) {
				throw exception;
			}
		}
		scopes.remove(scopes.size() - 1);
		returnVar = new CScriptVarLink(returnVarLink.var, TINYJS_TEMP_NAME);
		functionRoot.removeLink(returnVarLink);
		functionRoot.close();
		return returnVar;
	}
	
}
