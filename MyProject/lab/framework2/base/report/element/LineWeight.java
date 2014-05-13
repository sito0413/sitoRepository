package framework2.base.report.element;

/**
 * 線の太さ<br>
 * 
 * @最終更新者 若井<br>
 * @最終更新日 2009/10/19
 */
public enum LineWeight {

	/** 極細 */
	HAIRLINE(0),

	/** 細 */
	THIN(1),

	/** 中太 */
	MEDIUM(2),

	/** 太 */
	THICK(3);

	/**
	 * コンストラクタ<br>
	 * 
	 * @param weight
	 */
	private LineWeight(final int weight) {
		this.setWeight(weight);
	}

	/** 線の太さ */
	private int weight;

	/**
	 * 線の太さを取得します<br>
	 * 
	 * @return
	 */
	private int getWeight() {
		return this.weight;
	}

	/**
	 * 線の太さを設定します<br>
	 * 
	 * @param weight
	 */
	private void setWeight(final int weight) {
		this.weight = weight;
	}

	/**
	 * int の値を取得します<br>
	 * 
	 * @return
	 */
	public int toInteger() {
		return this.getWeight();
	}

	public float toFloat() {
		if (this.equals(HAIRLINE)) {
			return 0.5f;
		}
		return this.getWeight();
	}
}
