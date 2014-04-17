package frameWork.core.viewCompiler.script;

import java.util.Map;

import javax.swing.JOptionPane;

import frameWork.core.viewCompiler.Script;
import frameWork.core.viewCompiler.ScriptsBuffer;

public class RootScript extends Script {
	@Override
	public char syntax(final ScriptsBuffer scriptsBuffer) throws Exception {
		JOptionPane.showMessageDialog(null, "");
		while (scriptsBuffer.hasRemaining()) {
			scriptsBuffer.skip();
			switch ( super.syntax(scriptsBuffer) ) {
				case ':' :
				case ',' :
				case ';' :
				case ')' :
				case '}' :
					scriptsBuffer.gotoNextChar();
					break;
				default :
					scriptsBuffer.skip();
					break;
			}
		}
		JOptionPane.showMessageDialog(null, "");
		return 0;
	}
	
	@Override
	public char create(final ScriptsBuffer scriptsBuffer) throws Exception {
		return 0;
	}
	
	@Override
	public String execute(final Map<String, Class<?>> classMap, final Map<String, Object> objectMap) throws Exception {
		for (final Script script : block) {
			script.execute(classMap, objectMap);
		}
		return "";
	}
}
