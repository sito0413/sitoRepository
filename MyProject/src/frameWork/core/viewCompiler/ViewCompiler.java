package frameWork.core.viewCompiler;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import frameWork.ThrowableUtil;
import frameWork.core.fileSystem.FileSystem;
import frameWork.core.state.AttributeMap;
import frameWork.core.state.Response;
import frameWork.core.state.State;
import frameWork.core.viewCompiler.script.bytecode.ObjectScript;
import frameWork.core.viewCompiler.script.syntax.SyntaxScript;

public class ViewCompiler {
	public static void main(final String[] args) throws Exception {
		//		if (state.getSession().getAttribute("dateId") == null) {
		//			state.getSession().setAttribute("dateId", list.get(0).get(0).get(0));
		//		}
		//state.getRequest().setAttribute("data", list);
		final Scope scope = new Scope();
		final ViewerWriter out = new ViewerWriter();
		scope.startScope();
		scope.put("out", new ObjectScript(ViewerWriter.class, out));
		scope.put("session", new ObjectScript(AttributeMap.class, new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				System.out.println("s@" + name);
				return "";
			}
		}));
		scope.put("application", new ObjectScript(AttributeMap.class, new AttributeMap() {
			
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
		}));
		scope.put("request", new ObjectScript(AttributeMap.class, new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				System.out.println("r@" + name);
				if (name.equals("data")) {
					return new ArrayList<List<List<String>>>();
				}
				return "";
			}
		}));
		parse(new File("input.jsp"), null, scope);
		out.writeTo(System.out);
	}
	
	public static void compile(final Response response, final State state) {
		try (final OutputStream responseOutputStream = response.getOutputStream()) {
			final File targetFile = new File(FileSystem.Viewer, state.getPage().substring(1));
			if (targetFile.exists()) {
				final ViewerWriter out = new ViewerWriter();
				//				final File scriptFile = File.createTempFile("view", ".js", FileSystem.Temp);
				response.setContentType("text/html;charset=" + FileSystem.Config.getString("ViewChareet", "UTF-8"));
				final Scope scope = new Scope();
				scope.put("out", new ObjectScript(ViewerWriter.class, out));
				scope.put("session", new ObjectScript(AttributeMap.class, state.getSession()));
				scope.put("application", new ObjectScript(AttributeMap.class, state.getContext()));
				scope.put("request", new ObjectScript(AttributeMap.class, state.getRequest()));
				parse(targetFile, response, scope);
				response.setContentLength(out.size());
				out.writeTo(responseOutputStream);
			}
		}
		catch (final Exception e) {
			response.setContentLength(0);
			ThrowableUtil.throwable(e);
		}
	}
	
	private static void parse(final File targetFile, final Response response, final Scope scope) throws Exception {
		JOptionPane.showMessageDialog(null, "");
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
			final ScriptsBuffer scriptsBuffer = new ScriptsBuffer(
			        new ParserBuffer(CharBuffer.wrap(writer.toString())).toTextlets(targetFile, scope, response));
			while (scriptsBuffer.hasRemaining()) {
				scriptsBuffer.skip();
				final SyntaxScript subScript = scriptsBuffer.getSyntaxToken();
				switch ( subScript.create(scriptsBuffer) ) {
					case ':' :
					case ',' :
					case ')' :
						throw new Exception("Error " + scriptsBuffer.getChar() + " at " + scriptsBuffer.getPosition());
					case ';' :
					case '}' :
						scriptsBuffer.gotoNextChar();
						break;
					default :
						scriptsBuffer.skip();
						break;
				}
				//				subScript.execute(scope);
			}
		}
		JOptionPane.showMessageDialog(null, "");
	}
}