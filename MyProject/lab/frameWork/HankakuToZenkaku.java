package frameWork;

import java.util.HashMap;

@SuppressWarnings("nls")
public class HankakuToZenkaku {
	
	public static String Han2Zen(final String s) {
		
		final HashMap<String, String> hmCharTbl = new HashMap<>();
		
		hmCharTbl.put("0", "０");
		hmCharTbl.put("1", "１");
		hmCharTbl.put("2", "２");
		hmCharTbl.put("3", "３");
		hmCharTbl.put("4", "４");
		hmCharTbl.put("5", "５");
		hmCharTbl.put("6", "６");
		hmCharTbl.put("7", "７");
		hmCharTbl.put("8", "８");
		hmCharTbl.put("9", "９");
		hmCharTbl.put("a", "ａ");
		hmCharTbl.put("b", "ｂ");
		hmCharTbl.put("c", "ｃ");
		hmCharTbl.put("d", "ｄ");
		hmCharTbl.put("e", "ｅ");
		hmCharTbl.put("f", "ｆ");
		hmCharTbl.put("g", "ｇ");
		hmCharTbl.put("h", "ｈ");
		hmCharTbl.put("i", "ｉ");
		hmCharTbl.put("j", "ｊ");
		hmCharTbl.put("k", "ｋ");
		hmCharTbl.put("l", "ｌ");
		hmCharTbl.put("m", "ｍ");
		hmCharTbl.put("n", "ｎ");
		hmCharTbl.put("o", "ｏ");
		hmCharTbl.put("p", "ｐ");
		hmCharTbl.put("q", "ｑ");
		hmCharTbl.put("r", "ｒ");
		hmCharTbl.put("s", "ｓ");
		hmCharTbl.put("t", "ｔ");
		hmCharTbl.put("u", "ｕ");
		hmCharTbl.put("v", "ｖ");
		hmCharTbl.put("w", "ｗ");
		hmCharTbl.put("x", "ｘ");
		hmCharTbl.put("y", "ｙ");
		hmCharTbl.put("z", "ｚ");
		hmCharTbl.put("A", "Ａ");
		hmCharTbl.put("B", "Ｂ");
		hmCharTbl.put("C", "Ｃ");
		hmCharTbl.put("D", "Ｄ");
		hmCharTbl.put("E", "Ｅ");
		hmCharTbl.put("F", "Ｆ");
		hmCharTbl.put("G", "Ｇ");
		hmCharTbl.put("H", "Ｈ");
		hmCharTbl.put("I", "Ｉ");
		hmCharTbl.put("J", "Ｊ");
		hmCharTbl.put("K", "Ｋ");
		hmCharTbl.put("L", "Ｌ");
		hmCharTbl.put("M", "Ｍ");
		hmCharTbl.put("N", "Ｎ");
		hmCharTbl.put("O", "Ｏ");
		hmCharTbl.put("P", "Ｐ");
		hmCharTbl.put("Q", "Ｑ");
		hmCharTbl.put("R", "Ｒ");
		hmCharTbl.put("S", "Ｓ");
		hmCharTbl.put("T", "Ｔ");
		hmCharTbl.put("U", "Ｕ");
		hmCharTbl.put("V", "Ｖ");
		hmCharTbl.put("W", "Ｗ");
		hmCharTbl.put("X", "Ｘ");
		hmCharTbl.put("Y", "Ｙ");
		hmCharTbl.put("Z", "Ｚ");
		hmCharTbl.put("ｱ", "ア");
		hmCharTbl.put("ｲ", "イ");
		hmCharTbl.put("ｳ", "ウ");
		hmCharTbl.put("ｴ", "エ");
		hmCharTbl.put("ｵ", "オ");
		hmCharTbl.put("ｶ", "カ");
		hmCharTbl.put("ｷ", "キ");
		hmCharTbl.put("ｸ", "ク");
		hmCharTbl.put("ｹ", "ケ");
		hmCharTbl.put("ｺ", "コ");
		hmCharTbl.put("ｻ", "サ");
		hmCharTbl.put("ｼ", "シ");
		hmCharTbl.put("ｽ", "ス");
		hmCharTbl.put("ｾ", "セ");
		hmCharTbl.put("ｿ", "ソ");
		hmCharTbl.put("ﾀ", "タ");
		hmCharTbl.put("ﾁ", "チ");
		hmCharTbl.put("ﾂ", "ツ");
		hmCharTbl.put("ﾃ", "テ");
		hmCharTbl.put("ﾄ", "ト");
		hmCharTbl.put("ﾅ", "ナ");
		hmCharTbl.put("ﾆ", "ニ");
		hmCharTbl.put("ﾇ", "ヌ");
		hmCharTbl.put("ﾈ", "ネ");
		hmCharTbl.put("ﾉ", "ノ");
		hmCharTbl.put("ﾊ", "ハ");
		hmCharTbl.put("ﾋ", "ヒ");
		hmCharTbl.put("ﾌ", "フ");
		hmCharTbl.put("ﾍ", "ヘ");
		hmCharTbl.put("ﾎ", "ホ");
		hmCharTbl.put("ﾏ", "マ");
		hmCharTbl.put("ﾐ", "ミ");
		hmCharTbl.put("ﾑ", "ム");
		hmCharTbl.put("ﾒ", "メ");
		hmCharTbl.put("ﾓ", "モ");
		hmCharTbl.put("ﾔ", "ヤ");
		hmCharTbl.put("ﾕ", "ユ");
		hmCharTbl.put("ﾖ", "ヨ");
		hmCharTbl.put("ﾗ", "ラ");
		hmCharTbl.put("ﾘ", "リ");
		hmCharTbl.put("ﾙ", "ル");
		hmCharTbl.put("ﾚ", "レ");
		hmCharTbl.put("ﾛ", "ロ");
		hmCharTbl.put("ﾜ", "ワ");
		hmCharTbl.put("ｦ", "ヲ");
		hmCharTbl.put("ﾝ", "ン");
		hmCharTbl.put("ｧ", "ァ");
		hmCharTbl.put("ｨ", "ィ");
		hmCharTbl.put("ｩ", "ゥ");
		hmCharTbl.put("ｪ", "ェ");
		hmCharTbl.put("ｫ", "ォ");
		hmCharTbl.put("ｯ", "ッ");
		hmCharTbl.put("ｬ", "ャ");
		hmCharTbl.put("ｭ", "ュ");
		hmCharTbl.put("ｮ", "ョ");
		hmCharTbl.put("ｰ", "ー");
		hmCharTbl.put("｡", "。");
		hmCharTbl.put("､", "、");
		hmCharTbl.put("･", "・");
		hmCharTbl.put("｢", "「");
		hmCharTbl.put("｣", "」");
		hmCharTbl.put("ﾞ", "゛");
		hmCharTbl.put("ﾟ", "゜");
		hmCharTbl.put("ｳﾞ", "ヴ");
		hmCharTbl.put("ｶﾞ", "ガ");
		hmCharTbl.put("ｷﾞ", "ギ");
		hmCharTbl.put("ｸﾞ", "グ");
		hmCharTbl.put("ｹﾞ", "ゲ");
		hmCharTbl.put("ｺﾞ", "ゴ");
		hmCharTbl.put("ｻﾞ", "ザ");
		hmCharTbl.put("ｼﾞ", "ジ");
		hmCharTbl.put("ｽﾞ", "ズ");
		hmCharTbl.put("ｾﾞ", "ゼ");
		hmCharTbl.put("ｿﾞ", "ゾ");
		hmCharTbl.put("ﾀﾞ", "ダ");
		hmCharTbl.put("ﾁﾞ", "ヂ");
		hmCharTbl.put("ﾂﾞ", "ヅ");
		hmCharTbl.put("ﾃﾞ", "デ");
		hmCharTbl.put("ﾄﾞ", "ド");
		hmCharTbl.put("ﾊﾞ", "バ");
		hmCharTbl.put("ﾋﾞ", "ビ");
		hmCharTbl.put("ﾌﾞ", "ブ");
		hmCharTbl.put("ﾍﾞ", "ベ");
		hmCharTbl.put("ﾎﾞ", "ボ");
		hmCharTbl.put("ﾊﾟ", "パ");
		hmCharTbl.put("ﾋﾟ", "ピ");
		hmCharTbl.put("ﾌﾟ", "プ");
		hmCharTbl.put("ﾍﾟ", "ペ");
		hmCharTbl.put("ﾎﾟ", "ポ");
		
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
				strChar1 = hmCharTbl.get(strKey);
				if (strChar1 == null) {
					strKey = String.valueOf(c1);
					strChar1 = hmCharTbl.get(strKey);
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
				strChar1 = hmCharTbl.get(strKey);
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
}
