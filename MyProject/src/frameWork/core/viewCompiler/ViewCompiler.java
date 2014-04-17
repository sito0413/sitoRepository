package frameWork.core.viewCompiler;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import frameWork.ThrowableUtil;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.AttributeMap;
import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.core.viewCompiler.script.RootScript;

public class ViewCompiler {
	public static void main(final String[] args) throws Exception {
		//		if (state.getSession().getAttribute("dateId") == null) {
		//			state.getSession().setAttribute("dateId", list.get(0).get(0).get(0));
		//		}
		//state.getRequest().setAttribute("data", list);
		final Map<String, Class<?>> classMap = new HashMap<>();
		final Map<String, Object> objectMap = new HashMap<>();
		final ViewerWriter out = new ViewerWriter();
		objectMap.put("out", out);
		objectMap.put("session", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				System.out.println(name);
				return "";
			}
		});
		objectMap.put("application", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		});
		objectMap.put("request", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				System.out.println(name);
				return "";
			}
		});
		parse(new File("input.jsp"), null, classMap, objectMap);
		out.writeTo(System.out);
	}
	
	public static void compile(final Response response, final State state) {
		try (final OutputStream responseOutputStream = response.getOutputStream()) {
			final File targetFile = new File(FileSystem.Viewer, state.getPage().substring(1));
			if (targetFile.exists()) {
				final ViewerWriter out = new ViewerWriter();
				//				final File scriptFile = File.createTempFile("view", ".js", FileSystem.Temp);
				response.setContentType("text/html;charset=" + FileSystem.Config.getString("ViewChareet", "UTF-8"));
				final Map<String, Class<?>> classMap = new HashMap<>();
				final Map<String, Object> objectMap = new HashMap<>();
				objectMap.put("out", out);
				objectMap.put("session", state.getSession());
				objectMap.put("application", state.getContext());
				objectMap.put("request", state.getRequest());
				parse(targetFile, response, classMap, objectMap);
				//
				//				final ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
				//				try (FileReader reader = new FileReader(scriptFile)) {
				//					engine.put("out", out);
				//					engine.put("session", state.getSession());
				//					engine.put("application", state.getContext());
				//					engine.put("request", state.getRequest());
				//					engine.eval(reader);
				//					// 関数の呼び出し
				//					((Invocable) engine).invokeFunction("invokeFunction");
				//					scriptFile.delete();
				//					scriptFile.getParentFile().delete();
				response.setContentLength(out.size());
				out.writeTo(responseOutputStream);
				//				}
				//				scriptFile.delete();
			}
		}
		catch (final Exception e) {
			response.setContentLength(0);
			ThrowableUtil.throwable(e);
		}
	}
	
	private static void parse(final File targetFile, final Response response, final Map<String, Class<?>> classMap,
	        final Map<String, Object> objectMap) throws Exception {
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			try (InputStreamReader reader = new InputStreamReader(new FileInputStream(targetFile),
			        FileSystem.Config.getString("ViewChareet", "UTF-8"))) {
				final char buf[] = new char[5120];
				for (int i = 0; (i = reader.read(buf)) != -1;) {
					writer.write(buf, 0, i);
				}
			}
			catch (final UnsupportedEncodingException e) {
				try (InputStreamReader reader = new InputStreamReader(new FileInputStream(targetFile), "UTF-8")) {
					final char buf[] = new char[5120];
					for (int i = 0; (i = reader.read(buf)) != -1;) {
						writer.write(buf, 0, i);
					}
				}
			}
			final RootScript rootScript = new RootScript();
			rootScript.syntax(new ScriptsBuffer(new ParserBuffer(CharBuffer.wrap(writer.toString())).toTextlets(
			        targetFile, classMap, response)));
			rootScript.execute(classMap, objectMap);
		}
	}
}