package frameWork.core.viewCompiler;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import frameWork.ThrowableUtil;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.Response;
import frameWork.core.state.State;

public class ViewCompiler {
	public static void main(final String[] args) throws Exception {
		//		if (state.getSession().getAttribute("dateId") == null) {
		//			state.getSession().setAttribute("dateId", list.get(0).get(0).get(0));
		//		}
		//state.getRequest().setAttribute("data", list);
		parse(null, new File("input.jsp"), null);
	}
	
	public static void compile(final Response respons, final State state) {
		try (final OutputStream responseOutputStream = respons.getOutputStream()) {
			final File targetFile = new File(FileSystem.Viewer, state.getPage().substring(1));
			if (targetFile.exists()) {
				final File scriptFile = File.createTempFile("view", ".js", FileSystem.Temp);
				parse(scriptFile, targetFile, respons);
				
				final ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
				try (FileReader reader = new FileReader(scriptFile)) {
					final ViewerWriter out = new ViewerWriter();
					engine.put("out", out);
					engine.put("session", state.getSession());
					engine.put("application", state.getContext());
					engine.put("request", state.getRequest());
					engine.eval(reader);
					// 関数の呼び出し
					((Invocable) engine).invokeFunction("invokeFunction");
					scriptFile.delete();
					scriptFile.getParentFile().delete();
					respons.setContentLength(out.size());
					out.writeTo(responseOutputStream);
				}
				
				scriptFile.delete();
			}
		}
		catch (final Exception e) {
			respons.setContentLength(0);
			ThrowableUtil.throwable(e);
		}
	}
	
	public static void parse(final File scriptFile, final File targetFile, final Response response) throws Exception {
		if (response != null) {
			response.setContentType("text/html;charset=" + FileSystem.Config.getString("ViewChareet", "UTF-8"));
		}
		final ParserBuffer parserBuffer = ParserBuffer.create(targetFile);
		final Map<String, Class<?>> classMap = new HashMap<>();
		final List<String> imports = new CopyOnWriteArrayList<>();
		final ScriptsBuffer scriptsBuffer = new ScriptsBuffer(parserBuffer.toTextlets(targetFile, classMap, imports,
		        response));
		final frameWork.core.viewCompiler.script.Script s = new frameWork.core.viewCompiler.script.Script();
		s.execute(scriptsBuffer.toString());
	}
}
