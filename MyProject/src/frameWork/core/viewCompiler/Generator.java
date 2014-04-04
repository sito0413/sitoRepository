package frameWork.core.viewCompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Generator {
	static void generate(final File srcFile, final ParserResult result,
	        final Map<String, ParserResult> parserResultMap, final String charsetName)
	        throws UnsupportedEncodingException, FileNotFoundException {
		final Set<String> imports = new HashSet<>();
		for (final ParserResult parserResult : parserResultMap.values()) {
			for (final String importText : parserResult.imports) {
				imports.add("importClass(" + importText + ");");
			}
		}
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(srcFile), charsetName))) {
			out.println("importPackage(java.lang);");
			for (final String importText : imports) {
				out.println(importText);
			}
			out.println("var out;");
			out.println("var session;");
			out.println("var application;");
			out.println("var request;");
			for (final ParserResult parserResult : parserResultMap.values()) {
				out.println("function " + parserResult.className + "() {");
				for (int i = 0; i < parserResult.textlets.size(); i++) {
					final Textlet textlet = parserResult.textlets.get(i);
					switch ( textlet.textletType ) {
						case Expression :
							out.println("out.write(" + textlet.text + ");");
							break;
						case Scriptlet :
							out.println(textlet.text);
							break;
						case IncludeAction :
							out.println(parserResultMap.get(textlet.text).className + "();");
							break;
						case Text :
							final StringBuilder builder = new StringBuilder(textlet.text);
							while (((i + 1) < parserResult.textlets.size())
							        && (parserResult.textlets.get(i + 1).textletType == TextletType.Text)) {
								builder.append(parserResult.textlets.get(++i).text);
							}
							final String t = builder.toString();
							boolean isAllSpace = true;
							final StringBuilder sb = new StringBuilder("out.write(\"");
							final int initLength = sb.length();
							final int limit = ((5 * 1024) + initLength);
							for (int j = 0; j < t.length(); j++) {
								char ch = t.charAt(j);
								isAllSpace &= Character.isWhitespace(ch);
								if (ch == '\t') {
									ch = ' ';
								}
								switch ( ch ) {
									case '"' :
										sb.append("\\\"");
										break;
									case '\\' :
										sb.append("\\\\");
										break;
									case '\r' :
										sb.append("\\r");
										break;
									case '\n' :
										sb.append("\\n");
										break;
									default :
										sb.append(ch);
										break;
								}
								if (!isAllSpace && (sb.length() >= limit)) {
									out.println(sb.append("\");").toString());
									sb.setLength(initLength);
								}
							}
							if (!isAllSpace && (sb.length() > initLength)) {
								out.println(sb.append("\");").toString());
							}
							break;
					}
				}
				out.println("}");
			}
		}
	}
}
