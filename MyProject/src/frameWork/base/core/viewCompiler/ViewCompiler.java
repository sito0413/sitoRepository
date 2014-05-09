package frameWork.base.core.viewCompiler;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;

import frameWork.base.core.fileSystem.FileSystem;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;
import frameWork.base.core.viewCompiler.parser.ParserBuffer;
import frameWork.base.util.ThrowableUtil;

public class ViewCompiler {
	public static void compile(final Response response, final State state) {
		try (final OutputStream responseOutputStream = response.getOutputStream()) {
			final File targetFile = new File(FileSystem.Viewer, state.getPage().substring(1));
			if (targetFile.exists()) {
				final ViewerWriter out = new ViewerWriter();
				response.setContentType("text/html;charset=" + FileSystem.Config.getString("ViewChareet", "UTF-8"));
				final Scope scope = new Scope();
				scope.put("out", out);
				scope.put("session", state.getSession());
				scope.put("application", state.getContext());
				scope.put("request", state.getRequest());
				parse(targetFile, response, scope);
				response.setContentLength(out.size());
				out.writeTo(responseOutputStream);
			}
		}
		catch (final Throwable e) {
			response.setContentLength(0);
			ThrowableUtil.throwable(e);
		}
	}
	
	protected static void parse(final File targetFile, final Response response, final Scope scope) throws Throwable {
		try (final CharArrayWriter writer = new CharArrayWriter()) {
			final ScriptsBuffer scriptsBuffer = new ScriptsBuffer(
			        new ParserBuffer(createCharBuffer(targetFile, writer)).toTextlets(scope, response));
			scriptsBuffer.execute(scope);
		}
	}
	
	private static CharBuffer createCharBuffer(final File targetFile, final CharArrayWriter writer)
	        throws FileNotFoundException, IOException {
		try {
			writerWrite(targetFile, writer, FileSystem.Config.getString("ViewChareet", "UTF-8"));
		}
		catch (final UnsupportedEncodingException e) {
			writerWrite(targetFile, writer, "UTF-8");
		}
		return CharBuffer.wrap(writer.toString());
	}
	
	private static void writerWrite(final File targetFile, final CharArrayWriter writer, final String viewChareet)
	        throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(targetFile), viewChareet)) {
			final char buf[] = new char[FileSystem.Config.getInteger("ViewSrcReadBuffer", 5120)];
			for (int i = 0; (i = reader.read(buf)) != -1;) {
				writer.write(buf, 0, i);
			}
		}
	}
}