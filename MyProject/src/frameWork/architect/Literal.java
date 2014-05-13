package frameWork.architect;

import java.io.File;

import frameWork.base.core.authority.Role;

public class Literal {
	
	public static void main(final String[] args) {
		System.out.println(Role.class.getPackage().getName().replace(".", "/"));
	}
	
	public static final String database = "database";
	public static final String info_xml = "info.xml";
	public static final String Root = new File("").getAbsolutePath();
	public static final String Temp = "Temp";
	public static final String SystemID = "SystemID";
	public static final String Path = "Path";
	public static final String config_xml = "config.xml";
	public static final String authority = "authority";
	public static final String src = "src";
}
