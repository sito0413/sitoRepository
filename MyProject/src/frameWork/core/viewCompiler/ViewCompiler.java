package frameWork.core.viewCompiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import frameWork.core.fileSystem.FileSystem;
import frameWork.utility.Response;
import frameWork.utility.ThrowableUtil;
import frameWork.utility.state.State;

public class ViewCompiler {
	
	public static void compile(final Response respons, final State state, final String charsetName,
	        final OutputStream responseOutputStream) throws IOException {
		try {
			final Map<String, ParserResult> parserResultMap = Parser.parse(state.getPage(), FileSystem.Viewer,
			        charsetName);
			final ParserResult result = parserResultMap.get(state.getPage());
			final File srcFile = File.createTempFile("view", ".js", FileSystem.Temp);
			Generator.generate(srcFile, result, parserResultMap, charsetName);
			respons.setContentType(result.contentType);
			final ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
			try (FileReader reader = new FileReader(srcFile)) {
				final ViewerWriter out = new ViewerWriter();
				engine.put("out", out);
				engine.put("session", state.getSession());
				engine.put("application", state.getContext());
				engine.put("request", state.getRequest());
				engine.eval(reader);
				// 関数の呼び出し
				((Invocable) engine).invokeFunction(result.className);
				srcFile.delete();
				srcFile.getParentFile().delete();
				respons.setContentLength(out.size());
				out.writeTo(responseOutputStream);
			}
			srcFile.delete();
		}
		catch (final Exception e) {
			respons.setContentLength(0);
			ThrowableUtil.throwable(e);
		}
		finally {
			responseOutputStream.close();
		}
	}
	
}
