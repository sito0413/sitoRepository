package frameWork.core.viewCompiler.script.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import frameWork.core.viewCompiler.script.Bytecode;
import frameWork.core.viewCompiler.script.Scope;
import frameWork.core.viewCompiler.script.Script;
import frameWork.core.viewCompiler.script.ScriptException;
import frameWork.core.viewCompiler.script.ScriptsBuffer;

@SuppressWarnings("rawtypes")
public class SwitchScript extends SyntaxScript<Bytecode> {
	private final Map<SyntaxScript, List<SyntaxScript>> caseMap;
	private Map<Object, List<SyntaxScript>> caseBytecodeMap;
	private final List<SyntaxScript> defaultList;
	
	public SwitchScript(final String label) {
		super(label);
		caseMap = new HashMap<>();
		defaultList = new ArrayList<>();
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		statement(scriptsBuffer);
		switch ( scriptsBuffer.getChar() ) {
			case '{' :
				return block(scriptsBuffer);
			default :
				throw scriptsBuffer.illegalCharacterError();
		}
	}
	
	@Override
	protected final char block(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			List<SyntaxScript> caseBlock = defaultList;
			while (scriptsBuffer.hasRemaining()) {
				if (scriptsBuffer.startToken("case")) {
					scriptsBuffer.skip();
					caseBlock = new ArrayList<>();
					final SyntaxScript subScript = scriptsBuffer.getStatementToken();
					scriptsBuffer.skip();
					subScript.create(scriptsBuffer);
					caseMap.put(subScript, caseBlock);
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
				}
				else if (scriptsBuffer.startToken("default")) {
					scriptsBuffer.skip();
					caseBlock = defaultList;
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
				}
				scriptsBuffer.skip();
				
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				caseBlock.add(subScript);
				switch ( subScript.create(scriptsBuffer) ) {
					case '}' :
						return scriptsBuffer.gotoNextChar();
					case ';' :
						scriptsBuffer.gotoNextChar();
						break;
					default :
						break;
				}
			}
		}
		throw scriptsBuffer.illegalCharacterError();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		final Bytecode selectLabel = statement.get(0).execute(scope);
		scope.startScope();
		if (caseBytecodeMap == null) {
			caseBytecodeMap = new HashMap<>();
			for (final Entry<SyntaxScript, List<SyntaxScript>> e : caseMap.entrySet()) {
				caseBytecodeMap.put(e.getKey().execute(scope).get(), e.getValue());
			}
		}
		List<SyntaxScript> caseBlock = caseBytecodeMap.get(selectLabel.get());
		if (caseBlock == null) {
			caseBlock = defaultList;
		}
		Bytecode bytecode = null;
		loop:
		for (final Script script : caseBlock) {
			bytecode = script.execute(scope);
			if (bytecode.isBreak()) {
				if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
					bytecode = null;
				}
				break loop;
			}
			if (bytecode.isContinue()) {
				if (!label.isEmpty() && bytecode.get().equals(label)) {
					bytecode = null;
				}
				break loop;
			}
		}
		scope.endScope();
		return bytecode;
	}
}
