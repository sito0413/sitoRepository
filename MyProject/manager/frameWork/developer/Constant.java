package frameWork.developer;

import java.io.File;

import frameWork.architect.Literal;

public final class Constant {
	public static final String InfoFile = "info/" + Literal.info_xml;
	public static String TestInfoFile = Literal.src + "/" + Literal.info_xml;
	public static final String LOCK_SHEET_NAME = "ロック";
	public static final String AUTHORITY_SHEET_NAME = "権限";
	public static final String DATABASE_SHEET_NAME = "データベース";
	public static final String TABLE_SHEET_NAME = "テーブル";
	public static final String FIELD_SHEET_NAME = "フィールド";
	public static final String ROUTING_SHEET_NAME = "経路情報";
	
	private Constant() {
	}
	
	public static File getLockFile() {
		return new File(Literal.lock + "/ロック.xls");
	}
	
	public static File getAuthorityFile() {
		return new File(Literal.authority + "/権限.xls");
	}
	
	public static File getDatabaseFile() {
		return new File(Literal.database + "/データベース.xls");
	}
	
	public static File getTableFile(final String database) {
		return new File(Literal.database + "/" + database + "/" + database + ".xls");
	}
	
	public static File getFieldFile(final String database, final String table) {
		return new File(Literal.database + "/" + database + "/" + table + "/" + table + ".xls");
	}
	
	public static File getRoutingFile() {
		return new File(Literal.routing + "/経路情報.xls");
	}
}
