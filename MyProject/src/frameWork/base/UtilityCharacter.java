package frameWork.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtilityCharacter {
	static final HashMap<String, String> hzCharTbl = new HashMap<>();
	static final HashMap<String, String> zhCharTbl = new HashMap<>();
	static {
		final List<String[]> charList = new ArrayList<>();
		charList.add(new String[] {
			"0', '０"
		});
		charList.add(new String[] {
		        "1", "１"
		});
		charList.add(new String[] {
		        "2", "２"
		});
		charList.add(new String[] {
		        "3", "３"
		});
		charList.add(new String[] {
		        "4", "４"
		});
		charList.add(new String[] {
		        "5", "５"
		});
		charList.add(new String[] {
		        "6", "６"
		});
		charList.add(new String[] {
		        "7", "７"
		});
		charList.add(new String[] {
		        "8", "８"
		});
		charList.add(new String[] {
		        "9", "９"
		});
		charList.add(new String[] {
		        "a", "ａ"
		});
		charList.add(new String[] {
		        "b", "ｂ"
		});
		charList.add(new String[] {
		        "c", "ｃ"
		});
		charList.add(new String[] {
		        "d", "ｄ"
		});
		charList.add(new String[] {
		        "e", "ｅ"
		});
		charList.add(new String[] {
		        "f", "ｆ"
		});
		charList.add(new String[] {
		        "g", "ｇ"
		});
		charList.add(new String[] {
		        "h", "ｈ"
		});
		charList.add(new String[] {
		        "i", "ｉ"
		});
		charList.add(new String[] {
		        "j", "ｊ"
		});
		charList.add(new String[] {
		        "k", "ｋ"
		});
		charList.add(new String[] {
		        "l", "ｌ"
		});
		charList.add(new String[] {
		        "m", "ｍ"
		});
		charList.add(new String[] {
		        "n", "ｎ"
		});
		charList.add(new String[] {
		        "o", "ｏ"
		});
		charList.add(new String[] {
		        "p", "ｐ"
		});
		charList.add(new String[] {
		        "q", "ｑ"
		});
		charList.add(new String[] {
		        "r", "ｒ"
		});
		charList.add(new String[] {
		        "s", "ｓ"
		});
		charList.add(new String[] {
		        "t", "ｔ"
		});
		charList.add(new String[] {
		        "u", "ｕ"
		});
		charList.add(new String[] {
		        "v", "ｖ"
		});
		charList.add(new String[] {
		        "w", "ｗ"
		});
		charList.add(new String[] {
		        "x", "ｘ"
		});
		charList.add(new String[] {
		        "y", "ｙ"
		});
		charList.add(new String[] {
		        "z", "ｚ"
		});
		charList.add(new String[] {
		        "A", "Ａ"
		});
		charList.add(new String[] {
		        "B", "Ｂ"
		});
		charList.add(new String[] {
		        "C", "Ｃ"
		});
		charList.add(new String[] {
		        "D", "Ｄ"
		});
		charList.add(new String[] {
		        "E", "Ｅ"
		});
		charList.add(new String[] {
		        "F", "Ｆ"
		});
		charList.add(new String[] {
		        "G", "Ｇ"
		});
		charList.add(new String[] {
		        "H", "Ｈ"
		});
		charList.add(new String[] {
		        "I", "Ｉ"
		});
		charList.add(new String[] {
		        "J", "Ｊ"
		});
		charList.add(new String[] {
		        "K", "Ｋ"
		});
		charList.add(new String[] {
		        "L", "Ｌ"
		});
		charList.add(new String[] {
		        "M", "Ｍ"
		});
		charList.add(new String[] {
		        "N", "Ｎ"
		});
		charList.add(new String[] {
		        "O", "Ｏ"
		});
		charList.add(new String[] {
		        "P", "Ｐ"
		});
		charList.add(new String[] {
		        "Q", "Ｑ"
		});
		charList.add(new String[] {
		        "R", "Ｒ"
		});
		charList.add(new String[] {
		        "S", "Ｓ"
		});
		charList.add(new String[] {
		        "T", "Ｔ"
		});
		charList.add(new String[] {
		        "U", "Ｕ"
		});
		charList.add(new String[] {
		        "V", "Ｖ"
		});
		charList.add(new String[] {
		        "W", "Ｗ"
		});
		charList.add(new String[] {
		        "X", "Ｘ"
		});
		charList.add(new String[] {
		        "Y", "Ｙ"
		});
		charList.add(new String[] {
		        "Z", "Ｚ"
		});
		charList.add(new String[] {
		        "ｱ", "ア"
		});
		charList.add(new String[] {
		        "ｲ", "イ"
		});
		charList.add(new String[] {
		        "ｳ", "ウ"
		});
		charList.add(new String[] {
		        "ｴ", "エ"
		});
		charList.add(new String[] {
		        "ｵ", "オ"
		});
		charList.add(new String[] {
		        "ｶ", "カ"
		});
		charList.add(new String[] {
		        "ｷ", "キ"
		});
		charList.add(new String[] {
		        "ｸ", "ク"
		});
		charList.add(new String[] {
		        "ｹ", "ケ"
		});
		charList.add(new String[] {
		        "ｺ", "コ"
		});
		charList.add(new String[] {
		        "ｻ", "サ"
		});
		charList.add(new String[] {
		        "ｼ", "シ"
		});
		charList.add(new String[] {
		        "ｽ", "ス"
		});
		charList.add(new String[] {
		        "ｾ", "セ"
		});
		charList.add(new String[] {
		        "ｿ", "ソ"
		});
		charList.add(new String[] {
		        "ﾀ", "タ"
		});
		charList.add(new String[] {
		        "ﾁ", "チ"
		});
		charList.add(new String[] {
		        "ﾂ", "ツ"
		});
		charList.add(new String[] {
		        "ﾃ", "テ"
		});
		charList.add(new String[] {
		        "ﾄ", "ト"
		});
		charList.add(new String[] {
		        "ﾅ", "ナ"
		});
		charList.add(new String[] {
		        "ﾆ", "ニ"
		});
		charList.add(new String[] {
		        "ﾇ", "ヌ"
		});
		charList.add(new String[] {
		        "ﾈ", "ネ"
		});
		charList.add(new String[] {
		        "ﾉ", "ノ"
		});
		charList.add(new String[] {
		        "ﾊ", "ハ"
		});
		charList.add(new String[] {
		        "ﾋ", "ヒ"
		});
		charList.add(new String[] {
		        "ﾌ", "フ"
		});
		charList.add(new String[] {
		        "ﾍ", "ヘ"
		});
		charList.add(new String[] {
		        "ﾎ", "ホ"
		});
		charList.add(new String[] {
		        "ﾏ", "マ"
		});
		charList.add(new String[] {
		        "ﾐ", "ミ"
		});
		charList.add(new String[] {
		        "ﾑ", "ム"
		});
		charList.add(new String[] {
		        "ﾒ", "メ"
		});
		charList.add(new String[] {
		        "ﾓ", "モ"
		});
		charList.add(new String[] {
		        "ﾔ", "ヤ"
		});
		charList.add(new String[] {
		        "ﾕ", "ユ"
		});
		charList.add(new String[] {
		        "ﾖ", "ヨ"
		});
		charList.add(new String[] {
		        "ﾗ", "ラ"
		});
		charList.add(new String[] {
		        "ﾘ", "リ"
		});
		charList.add(new String[] {
		        "ﾙ", "ル"
		});
		charList.add(new String[] {
		        "ﾚ", "レ"
		});
		charList.add(new String[] {
		        "ﾛ", "ロ"
		});
		charList.add(new String[] {
		        "ﾜ", "ワ"
		});
		charList.add(new String[] {
		        "ｦ", "ヲ"
		});
		charList.add(new String[] {
		        "ﾝ", "ン"
		});
		charList.add(new String[] {
		        "ｧ", "ァ"
		});
		charList.add(new String[] {
		        "ｨ", "ィ"
		});
		charList.add(new String[] {
		        "ｩ", "ゥ"
		});
		charList.add(new String[] {
		        "ｪ", "ェ"
		});
		charList.add(new String[] {
		        "ｫ", "ォ"
		});
		charList.add(new String[] {
		        "ｯ", "ッ"
		});
		charList.add(new String[] {
		        "ｬ", "ャ"
		});
		charList.add(new String[] {
		        "ｭ", "ュ"
		});
		charList.add(new String[] {
		        "ｮ", "ョ"
		});
		charList.add(new String[] {
		        "ｰ", "ー"
		});
		charList.add(new String[] {
		        "｡", "。"
		});
		charList.add(new String[] {
		        "､", "、"
		});
		charList.add(new String[] {
		        "･", "・"
		});
		charList.add(new String[] {
		        "｢", "「"
		});
		charList.add(new String[] {
		        "｣", "」"
		});
		charList.add(new String[] {
		        "ﾞ", "゛"
		});
		charList.add(new String[] {
		        "ﾟ", "゜"
		});
		charList.add(new String[] {
		        "ｳﾞ", "ヴ"
		});
		charList.add(new String[] {
		        "ｶﾞ", "ガ"
		});
		charList.add(new String[] {
		        "ｷﾞ", "ギ"
		});
		charList.add(new String[] {
		        "ｸﾞ", "グ"
		});
		charList.add(new String[] {
		        "ｹﾞ", "ゲ"
		});
		charList.add(new String[] {
		        "ｺﾞ", "ゴ"
		});
		charList.add(new String[] {
		        "ｻﾞ", "ザ"
		});
		charList.add(new String[] {
		        "ｼﾞ", "ジ"
		});
		charList.add(new String[] {
		        "ｽﾞ", "ズ"
		});
		charList.add(new String[] {
		        "ｾﾞ", "ゼ"
		});
		charList.add(new String[] {
		        "ｿﾞ", "ゾ"
		});
		charList.add(new String[] {
		        "ﾀﾞ", "ダ"
		});
		charList.add(new String[] {
		        "ﾁﾞ", "ヂ"
		});
		charList.add(new String[] {
		        "ﾂﾞ", "ヅ"
		});
		charList.add(new String[] {
		        "ﾃﾞ", "デ"
		});
		charList.add(new String[] {
		        "ﾄﾞ", "ド"
		});
		charList.add(new String[] {
		        "ﾊﾞ", "バ"
		});
		charList.add(new String[] {
		        "ﾋﾞ", "ビ"
		});
		charList.add(new String[] {
		        "ﾌﾞ", "ブ"
		});
		charList.add(new String[] {
		        "ﾍﾞ", "ベ"
		});
		charList.add(new String[] {
		        "ﾎﾞ", "ボ"
		});
		charList.add(new String[] {
		        "ﾊﾟ", "パ"
		});
		charList.add(new String[] {
		        "ﾋﾟ", "ピ"
		});
		charList.add(new String[] {
		        "ﾌﾟ", "プ"
		});
		charList.add(new String[] {
		        "ﾍﾟ", "ペ"
		});
		charList.add(new String[] {
		        "ﾎﾟ", "ポ"
		});
		for (final String[] strings : charList) {
			hzCharTbl.put(strings[0], strings[1]);
			zhCharTbl.put(strings[1], strings[0]);
			
		}
	}
	
	public static String toHankaku(final String s) {
		final StringBuffer sbBuf = new StringBuffer();
		String strKey = new String(); // HashMapのKEY
		String strChar1 = ""; // １文字目
		char c1 = ' '; // １文字目
		int i;
		for (i = 0; i < s.length(); i++) {
			c1 = s.charAt(i);
			strChar1 = "";
			strKey = String.valueOf(c1);
			strChar1 = zhCharTbl.get(strKey);
			if (strChar1 == null) {
				sbBuf.append(c1);
			}
			else {
				sbBuf.append(strChar1);
			}
		}
		return sbBuf.toString();
	}
	
	public static String toZenkaku(final String s) {
		final StringBuffer sbBuf = new StringBuffer();
		
		String strKey = new String(); // HashMapのKEY
		String strChar1 = ""; // １文字目
		char c1 = ' '; // １文字目
		char c2 = ' '; // ２文字目
		int i;
		for (i = 0; i < s.length(); i++) {
			c1 = ' ';
			c2 = ' ';
			c1 = s.charAt(i);
			if ((i + 1) < s.length()) {
				c2 = s.charAt(i + 1);
			}
			strChar1 = "";
			if ((c2 == 'ﾞ') || (c2 == 'ﾟ')) {
				strKey = String.valueOf(c1) + String.valueOf(c2);
				strChar1 = hzCharTbl.get(strKey);
				if (strChar1 == null) {
					strKey = String.valueOf(c1);
					strChar1 = hzCharTbl.get(strKey);
					if (strChar1 == null) {
						sbBuf.append(c1);
					}
					else {
						sbBuf.append(strChar1);
					}
				}
				else {
					sbBuf.append(strChar1);
					i++;
				}
			}
			else {
				strKey = String.valueOf(c1);
				strChar1 = hzCharTbl.get(strKey);
				if (strChar1 == null) {
					sbBuf.append(c1);
				}
				else {
					sbBuf.append(strChar1);
				}
			}
		}
		return sbBuf.toString();
	}
	
	public static boolean isWhitespace(final char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r');
	}
	
	public static boolean isAlpha(final char ch) {
		return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_');
	}
	
	public static boolean isNumeric(final char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	
	public static boolean isOperator(final char ch) {
		return (ch == '!') || (ch == '^') || (ch == '~') || (ch == '=') || (ch == '+') || (ch == '-') || (ch == '*')
		        || (ch == '/') || (ch == '%') || (ch == '&') || (ch == '|') || (ch == '<') || (ch == '>');
	}
}
