package framework2.base.report.book.xml;

enum LineWeight {

	HAIRLINE(1), THIN(2), MEDIUM(3), THICK(4);

	/** 線の太さ */
	private int weight;

	private LineWeight(final int weight) {
		this.weight = weight;
	}

	public int toInteger() {
		return weight;
	}

}
