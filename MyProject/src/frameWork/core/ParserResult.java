package frameWork.core;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class ParserResult {
	final List<Textlet> textlets;
	final List<String> imports;
	final Deque<String> preCompilationContextStack;
	String contentType;
	String className;
	
	ParserResult(final String jspFile, final Deque<String> preCompilationContextStack) {
		this.textlets = new CopyOnWriteArrayList<>();
		this.imports = new CopyOnWriteArrayList<>();
		this.contentType = "text/html;charset=UTF-8";
		this.preCompilationContextStack = preCompilationContextStack;
		this.className = jspFile.replace("/", "_").replace(".", "_");
	}
}