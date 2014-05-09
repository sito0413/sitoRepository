package frameWork.base.database;

import frameWork.base.database.scheme.Database;

/*
 * このソースは database配下のファイル群 から自動生成されました。
 * 原則このソースは修正しないでください
 * 作成日時 Fri May 09 14:25:41 JST 2014
 */
public final class DatabaseManager {
	public static final Database[] databases = new Database[] {};
	
	public final void create() {
		for (final Database database : databases) {
			database.create();
		}
	}
}
