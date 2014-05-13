package frameWork.architect;

import java.util.Date;

public class SrcUtil {
	public static String getComment(final String fileName) {
		return "/*\r\n * このソースは " + fileName + " から自動生成されました。\r\n * 原則このソースは修正しないでください\r\n * 作成日時 " + new Date()
		        + "\r\n */";
	}
}
