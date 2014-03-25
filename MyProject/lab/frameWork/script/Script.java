package frameWork.script;

import static frameWork.script.Util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

import frameWork.script.functions.JsCallback;
import frameWork.script.functions.JsPrint;
import frameWork.script.lexicalAnalyzer.LexicalAnalyzer;
import frameWork.script.scriptVar.CScriptVar;
import frameWork.script.scriptVar.ScriptRoot;

public class Script {
	public static void main(final String[] args) throws Exception {
		final Script script = new Script();
		script.execute("var lets_quit = 0; function quit() { lets_quit = 1; }");
		script.execute("print(\"Interactive mode... Type quit(); to exit, or print(...); to print something.\");");
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (script.evaluate("lets_quit").equals("0")) {
			final String str = br.readLine();
			if (str != null) {
				try {
					script.execute(str);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		script.close();
	}
	
	private final ScriptRoot root;
	
	public Script() throws Exception {
		root = new ScriptRoot();
		addNative("function print(text)", new JsPrint());
	}
	
	public void close() throws Exception {
		root.unref();
	}
	
	public void addNative(final String funcDesc, final JsCallback jsCallback) throws Exception {
		final LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(root, funcDesc);
		lexicalAnalyzer.addNative(jsCallback);
	}
	
	public void execute(final String code) throws Exception {
		final LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(root, code);
		try {
			final Vector<CScriptVar> scopes = new Vector<>();
			scopes.add(root);
			while (!lexicalAnalyzer.tk.isEndOfFile()) {
				lexicalAnalyzer.tk.statement(lexicalAnalyzer, true, scopes);
			}
		}
		catch (final Exception e) {
			throw new Exception("Error " + e.getMessage() + " at " + lexicalAnalyzer.getPosition(), e);
		}
	}
	
	public Object evaluate(final String code) throws Exception {
		final LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(root, code);
		final Vector<CScriptVar> scopes = new Vector<>();
		scopes.add(root);
		CScriptVarLink v = lexicalAnalyzer.tk.base(lexicalAnalyzer, true, scopes);
		try {
			if (!lexicalAnalyzer.tk.isEndOfFile()) {
				lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
			}
			while (!lexicalAnalyzer.tk.isEndOfFile()) {
				clean(v);
				v = lexicalAnalyzer.tk.base(lexicalAnalyzer, true, scopes);
				if (!lexicalAnalyzer.tk.isEndOfFile()) {
					lexicalAnalyzer.tk.assertSemicolon(lexicalAnalyzer);
				}
			}
		}
		catch (final Exception e) {
			throw new Exception("Error " + e.getMessage() + " at " + lexicalAnalyzer.getPosition(), e);
		}
		
		if (v != null) {
			clean(v);
			return v.var.getString();
		}
		return "undefined";
	}
	
}
