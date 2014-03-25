package frameWork.script.scriptVar;

public class ScriptRoot extends ScriptObject {
	public final ScriptObject stringClass;
	public final ScriptObject objectClass;
	public final ScriptObject arrayClass;
	
	public ScriptRoot() {
		super();
		stringClass = new ScriptObject();
		arrayClass = new ScriptObject();
		objectClass = new ScriptObject();
		addChild("Object", objectClass);
		addChild("Array", arrayClass);
		addChild("String", stringClass);
	}
}
