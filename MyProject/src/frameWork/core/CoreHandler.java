package frameWork.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import frameWork.databaseConnector.DatabaseConnector;
import frameWork.databaseConnector.pool.ConnectorPool;
import frameWork.utility.Response;
import frameWork.utility.ThrowableUtil;
import frameWork.utility.state.State;

public class CoreHandler {
	public static final String PACKAGE = "viewer.controller";
	private final File baseResource;
	private final File tempDir;
	private final File viewerDir;
	private final ConnectorPool connectorPool;
	
	public CoreHandler(final File baseResource, final ConnectorPool connectorPool) {
		super();
		this.baseResource = baseResource;
		this.tempDir = new File(baseResource, "temp");
		this.viewerDir = new File(baseResource, "viewer");
		this.tempDir.mkdirs();
		this.viewerDir.mkdirs();
		this.connectorPool = connectorPool;
	}
	
	public void handle(final String target, final Response respons, final String charsetName, final String method,
	        final State state, final OutputStream outputStream) throws Exception {
		final String[] uri = (target.startsWith("/") ? target.substring(1) : target).split("/");
		final StringBuilder className = new StringBuilder().append(PACKAGE).append(".");
		final String methodName;
		if (uri.length == 0) {
			className.append("Index");
			methodName = "index";
		}
		else if (uri.length == 1) {
			if (uri[0].isEmpty()) {
				className.append("Index");
				methodName = "index";
			}
			else {
				className.append(Character.toUpperCase(uri[0].charAt(0))).append(uri[0].substring(1));
				methodName = "index";
			}
		}
		else {
			final int index = uri.length - 2;
			for (int j = 0; j < index; j++) {
				className.append(uri[j]).append('.');
			}
			className.append(Character.toUpperCase(uri[index].charAt(0))).append(uri[index].substring(1));
			if (uri[index + 1].contains(".")) {
				methodName = uri[index + 1].split("\\.")[0];
			}
			else {
				methodName = uri[index + 1];
			}
		}
		
		try {
			final Class<?> c = Class.forName(className.toString());
			Method m = null;
			try {
				m = c.getMethod(methodName + "_" + method.toLowerCase(), State.class);
			}
			catch (final NoSuchMethodException t) {
				m = c.getMethod(methodName, State.class);
			}
			try (DatabaseConnector connector = connectorPool.getConnector()) {
				if (check(c, m, state.auth())) {
					state.setConnector(connector);
					state.setPage(c.getCanonicalName().substring(PACKAGE.length()).replace(".", "/") + "/" + methodName
					        + ".jsp");
					m.invoke(bind(c.newInstance(), state), state);
					compile(respons, state, charsetName, outputStream);
				}
			}
		}
		catch (final ClassNotFoundException | NoSuchMethodException e) {
			//NOOP
		}
	}
	
	private boolean check(final Class<?> c, final Method method, final String[] authStrings) {
		return checkAuthority(c.getAnnotation(Authority.class), authStrings)
		        && checkAuthority(method.getAnnotation(Authority.class), authStrings);
	}
	
	private boolean checkAuthority(final Authority annotation, final String[] authStrings) {
		if (annotation != null) {
			final String[] allowRoll = annotation.allowRoll();
			for (final String roll : allowRoll) {
				for (final String auth : authStrings) {
					if (auth.equalsIgnoreCase(roll)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private <T> T bind(final T t, final State state) {
		try {
			for (final Field field : t.getClass().getFields()) {
				Object value = null;
				final String strValue = state.getParameter(field.getName());
				if (strValue != null) {
					try {
						if (field.getType().equals(String.class)) {
							value = strValue;
						}
						else if (field.getType().equals(int.class)) {
							value = Integer.parseInt(strValue);
						}
						else if (field.getType().equals(double.class)) {
							value = Double.parseDouble(strValue);
						}
						else if (field.getType().equals(Boolean.class)) {
							value = Boolean.parseBoolean(strValue);
						}
					}
					catch (final Exception e) {
						ThrowableUtil.throwable(e);
					}
					if (value != null) {
						field.setAccessible(true);
						field.set(t, value);
					}
				}
				
			}
		}
		catch (final Exception e) {
			ThrowableUtil.throwable(e);
		}
		return t;
	}
	
	public void compile(final Response respons, final State state, final String charsetName,
	        final OutputStream responseOutputStream) throws IOException {
		try {
			final Map<String, ParserResult> parserResultMap = Parser.parse(state.getPage(), viewerDir, charsetName);
			final ParserResult result = parserResultMap.get(state.getPage());
			final File srcFile = File.createTempFile("view", ".js", this.tempDir);
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
	
	public File getBaseResource() {
		return baseResource;
	}
}