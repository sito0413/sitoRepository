package frameWork.architect;

import java.io.File;

import frameWork.base.core.authority.Role;

public interface Literal {
	public static final String database = "database";
	public static final String info_xml = "info.xml";
	public static final String Root = new File("").getAbsolutePath();
	public static final String Temp = "Temp";
	public static final String SystemID = "SystemID";
	public static final String Path = "Path";
	public static final String config_xml = "config.xml";
	public static final String authority = "authority";
	public static final String src = "src";
	public static final String routing_xml = "routing.xml";
	public static final String packageName = "controller";
	public static final String config = "config";
	public static final String viewer = "viewer";
	public static final String system = "system";
	public static final String Developer = "Developer";
	public static final String routing = "routing";
	public static final Role DefaultRole = Role.ANONYMOUS;
	public static final String lock = "lock";
}
