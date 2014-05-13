package framework2.base.report.util;

public enum ExceptionType {
	NULL, NULL_ARGUMENT, NOT_HAS_PAGES;

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		switch (this) {
			case NULL_ARGUMENT:
				return "内部値に不整合が発生しました（NULL）";
			case NOT_HAS_PAGES:
				return "ワークシートが読み込めませんでした. 有効なシートがセットされていません。";
			default:
				return "帳票の出力に失敗しました";
		}
	}
}
