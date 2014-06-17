package frameWork.base.core.viewCompiler.script.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ScriptException;
import frameWork.base.core.viewCompiler.ScriptsBuffer;
import frameWork.base.core.viewCompiler.script.Script;
import frameWork.base.core.viewCompiler.script.bytecode.Bytecode;

@SuppressWarnings("rawtypes")
public class SwitchScript extends Script<Bytecode> {
	private final Map<Script, List<Script>> caseMap;
	private final Map<Object, List<Script>> caseBytecodeMap;
	private final List<Script> defaultList;
	private final ArrayList<Script> caseOrderList;
	
	public SwitchScript(final String label) {
		super(label);
		caseMap = new HashMap<>();
		defaultList = new ArrayList<>();
		caseOrderList = new ArrayList<>();
		caseBytecodeMap = new HashMap<>();
	}
	
	@Override
	protected final char block(final ScriptsBuffer scriptsBuffer) throws ScriptException {
		if (scriptsBuffer.getChar() == '{') {
			scriptsBuffer.gotoNextChar();
			if (scriptsBuffer.getChar() == '}') {
				return scriptsBuffer.gotoNextChar();
			}
			
			List<Script> caseBlock = defaultList;
			while (scriptsBuffer.hasRemaining()) {
				while (scriptsBuffer.startToken("case")) {
					scriptsBuffer.skip();
					caseBlock = new ArrayList<>();
					final Script subScript = scriptsBuffer.getStatementToken();
					scriptsBuffer.skip();
					subScript.create(scriptsBuffer);
					caseOrderList.add(subScript);
					caseMap.put(subScript, caseBlock);
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
					scriptsBuffer.gotoNextChar();
				}
				if (scriptsBuffer.startToken("default")) {
					scriptsBuffer.skip();
					caseBlock = defaultList;
					if (scriptsBuffer.getChar() != ':') {
						throw scriptsBuffer.illegalCharacterError();
					}
					scriptsBuffer.gotoNextChar();
				}
				switch ( scriptsBuffer.getChar() ) {
					case '}' :
						return scriptsBuffer.gotoNextChar();
					default :
						if (scriptsBuffer.hasRemaining()) {
							final Script subScript = scriptsBuffer.getSyntaxToken();
							caseBlock.add(subScript);
							switch ( subScript.create(scriptsBuffer) ) {
								case ';' :
									scriptsBuffer.gotoNextChar();
									if (scriptsBuffer.getChar() == '}') {
										return scriptsBuffer.gotoNextChar();
									}
									break;
								default :
									break;
							}
						}
				}
			}
		}
		throw scriptsBuffer.illegalCharacterError();
	}
	
	@Override
	public Bytecode execute(final Scope scope) throws ScriptException {
		final Bytecode selectLabel = statement.get(0).execute(scope);
		scope.startScope();
		caseBytecodeMap.clear();
		int index = caseOrderList.size();
		for (int i = 0; i < caseOrderList.size(); i++) {
			final Script caseSyntaxScript = caseOrderList.get(i);
			final Object object = caseSyntaxScript.execute(scope).get();
			for (final Script script : caseMap.get(caseSyntaxScript)) {
				script.callDefine(scope);
			}
			if (object.equals(selectLabel.get())) {
				index = i;
			}
		}
		Bytecode bytecode = null;
		LOOP:
		{
			while (index < caseOrderList.size()) {
				final List<Script> caseBlock = caseMap.get(caseOrderList.get(index));
				for (final Script script : caseBlock) {
					bytecode = script.execute(scope);
					if (bytecode != null) {
						if (bytecode.isBreak()) {
							if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
								bytecode = null;
							}
							break LOOP;
						}
						if (bytecode.isContinue()) {
							if (!label.isEmpty() && bytecode.get().equals(label)) {
								bytecode = null;
							}
							break LOOP;
						}
					}
				}
				index++;
			}
			for (final Script script : defaultList) {
				bytecode = script.execute(scope);
				if (bytecode != null) {
					if (bytecode.isBreak()) {
						if (bytecode.get().toString().isEmpty() || bytecode.get().equals(label)) {
							bytecode = null;
						}
						break;
					}
					if (bytecode.isContinue()) {
						if (!label.isEmpty() && bytecode.get().equals(label)) {
							bytecode = null;
						}
						break;
					}
				}
			}
		}
		scope.endScope();
		return bytecode;
	}
}
